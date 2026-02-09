package ch.epicerielacanopee.statistics.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.epicerielacanopee.statistics.config.Constants;
import ch.epicerielacanopee.statistics.domain.MouvementsStock;
import ch.epicerielacanopee.statistics.repository.MouvementsStockRepository;
import ch.epicerielacanopee.statistics.repository.projection.MouvementsStockDateRangeProjection;
import ch.epicerielacanopee.statistics.repository.projection.MouvementsStockProjection;
import ch.epicerielacanopee.statistics.service.dto.EpicerioMouvementsStockDTO;
import ch.epicerielacanopee.statistics.service.dto.MouvementsStockDTO;
import ch.epicerielacanopee.statistics.service.mapper.MouvementsStockMapper;
import ch.epicerielacanopee.statistics.service.util.AnalysisValues;
import ch.epicerielacanopee.statistics.service.util.LowestSalesResult;
import ch.epicerielacanopee.statistics.service.util.MonthlyAnalysisResult;
import ch.epicerielacanopee.statistics.service.util.MonthlyAnalysisStats;
import ch.epicerielacanopee.statistics.service.util.MouvementsStockDateRange;
import ch.epicerielacanopee.statistics.service.util.ProductGroupingKey;
import ch.epicerielacanopee.statistics.service.util.StatisticalQuantities;
import ch.epicerielacanopee.statistics.service.util.TopLossesResult;
import ch.epicerielacanopee.statistics.service.util.YearMonth;

/**
 * Service Implementation for managing
 * {@link ch.epicerielacanopee.statistics.domain.MouvementsStock}.
 */
@Service
@Transactional
public class MouvementsStockService {

    private static final Map<Month, List<String>> seasonalProducts = new HashMap<>();
    static {
        seasonalProducts.put(Month.JANUARY,
                Arrays.asList("fru01", "leg75", "fru03", "fru22", "leg01", "leg06", "leg08", "leg09", "leg11", "leg15",
                        "leg18", "leg21", "leg24", "leg25", "leg26", "leg27", "leg28", "leg29", "leg30", "leg31",
                        "leg33", "leg34", "leg37", "leg39", "leg46", "leg47", "leg49", "leg50", "leg51", "leg55",
                        "leg58", "leg60", "leg80", "fru33", "fru34", "fru35", "fru36", "fru37", "fru38", "leg87",
                        "fru42", "leg90", "fru44", "fru45", "fru51", "leg93", "alc15", "fru23", "leg98", "fru13",
                        "leg102", "leg19", "leg63", "leg77", "leg79", "fru42", "fru23", "leg94", "leg38", "leg100",
                        "leg101", "leg102", "leg42", "leg45", "leg86", "leg89", "leg95", "leg103"));
        seasonalProducts.put(Month.FEBRUARY,
                Arrays.asList("fru01", "leg75", "fru03", "fru22", "leg01", "leg05", "leg06", "leg08", "leg09", "leg11",
                        "leg15", "leg18", "leg21", "leg25", "leg26", "leg27", "leg28", "leg29", "leg30", "leg31",
                        "leg33", "leg37", "leg47", "leg49", "leg50", "leg51", "leg55", "leg58", "leg60", "leg80",
                        "boi10", "fru33", "fru35", "fru36", "fru38", "leg87", "leg90", "fru44", "fru45", "fru51",
                        "leg93", "fru23", "leg98", "leg104", "fru13", "leg102", "leg19", "leg63", "leg77", "leg79",
                        "leg38", "leg100", "leg101", "leg102", "leg42", "leg45", "leg86", "leg89", "leg103"));
        seasonalProducts.put(Month.MARCH,
                Arrays.asList("fru01", "leg75", "fru03", "fru22", "leg01", "leg05", "leg06", "leg08", "leg09", "leg15",
                        "leg18", "leg21", "leg25", "leg26", "leg27", "leg28", "leg29", "leg30", "leg31", "leg32",
                        "leg33", "leg47", "leg48", "leg49", "leg51", "leg55", "leg58", "leg60", "leg80", "fru35",
                        "fru36", "fru38", "leg90", "fru44", "fru45", "fru51", "leg93", "fru23", "fru13", "leg102",
                        "leg19", "leg63", "leg77", "leg79", "leg38", "leg100", "leg101", "leg102", "leg42", "leg45",
                        "leg86", "leg89"));
        seasonalProducts.put(Month.APRIL,
                Arrays.asList("fru01", "leg75", "fru03", "fru22", "fru30", "leg69", "leg01", "leg03", "leg05", "leg06",
                        "leg08", "leg09", "leg10", "leg16", "leg17", "leg21", "leg25", "leg28", "leg31", "leg33",
                        "leg34", "leg47", "leg48", "leg49", "leg51", "leg55", "leg58", "leg60", "leg76", "leg80",
                        "fru38", "leg90", "fru44", "fru45", "fru51", "leg104", "fru13", "leg102", "leg19", "leg23",
                        "leg36", "leg63", "leg77", "leg79", "leg96", "leg38", "leg101", "leg102", "leg45", "leg86",
                        "leg89", "leg95"));
        seasonalProducts.put(Month.MAY,
                Arrays.asList("leg75", "fru03", "fru22", "fru30", "leg69", "leg01", "leg03", "leg05", "leg06", "leg07",
                        "leg08", "leg09", "leg10", "leg16", "leg17", "leg21", "leg22", "leg25", "leg28", "leg33",
                        "leg34", "leg35", "leg40", "leg48", "leg49", "leg51", "leg59", "leg60", "leg62", "leg74",
                        "leg76", "leg80", "fru38", "leg90", "fru44", "fru45", "fru02", "fru13", "leg102", "leg19",
                        "leg23", "leg36", "leg63", "leg77", "leg79", "leg96", "leg38", "leg45", "leg81", "leg82",
                        "leg85 ", "leg86", "leg89", "leg95"));
        seasonalProducts.put(Month.JUNE,
                Arrays.asList("leg75", "fru03", "fru04", "fru08", "fru10", "fru14", "fru16", "fru17", "fru22", "fru30",
                        "fru31", "leg69", "leg01", "leg03", "leg05", "leg06", "leg07", "leg08", "leg10", "leg14",
                        "leg22", "leg24", "leg34", "leg35", "leg40", "leg43", "leg48", "leg51", "leg54", "leg59",
                        "leg62", "leg74", "leg78", "leg76", "leg90", "fru44", "fru45", "fru02", "fru13", "fru29",
                        "leg23", "leg36", "leg77", "leg79", "leg83", "leg84", "leg44", "fru05", "leg96", "leg38",
                        "leg04", "leg45", "leg52", "leg81", "leg82", "leg85 ", "leg86", "leg89", "leg95"));
        seasonalProducts.put(Month.JULY,
                Arrays.asList("leg75", "fru03", "fru04", "fru07", "fru08", "fru10", "fru14", "fru15", "fru16", "fru17",
                        "fru20", "fru31", "leg69", "leg02", "leg03", "leg05", "leg06", "leg07", "leg08", "leg10",
                        "leg13", "leg14", "leg22", "leg24", "leg34", "leg35", "leg40", "leg48", "leg54", "leg57",
                        "leg59", "leg62", "leg74", "leg78", "leg76", "fru38", "fru43", "leg90", "fru44", "fru45",
                        "fru02", "fru19", "fru29", "leg23", "leg36", "leg77", "leg79", "leg83", "leg84", "leg92",
                        "fru46", "fru47", "fru48", "leg44", "leg68", "fru05", "leg96", "leg38", "leg04", "leg42",
                        "leg52", "leg66", "leg81", "leg82", "leg85 ", "leg86", "leg95"));
        seasonalProducts.put(Month.AUGUST,
                Arrays.asList("leg75", "fru03", "fru04", "fru07", "fru08", "fru14", "fru15", "fru16", "fru17", "fru20",
                        "fru31", "leg69", "leg03", "leg05", "leg06", "leg07", "leg08", "leg10", "leg13", "leg14",
                        "leg22", "leg24", "leg25", "leg34", "leg35", "leg40", "leg41", "leg43", "leg48", "leg54",
                        "leg57", "leg59", "leg62", "leg74", "leg78", "leg76", "fru37", "fru38", "fru42", "fru43",
                        "leg90", "fru44", "fru45", "fru49", "leg97", "fru19", "fru13", "fru29", "leg23", "leg36",
                        "leg63", "leg77", "leg79", "leg83", "leg84", "leg92", "fru42", "fru46", "fru47", "fru48",
                        "fru49", "leg44", "leg68", "fru05", "leg96", "leg38", "leg04", "leg42", "leg52", "leg66",
                        "leg81", "leg82", "leg85 ", "leg86", "leg95"));
        seasonalProducts.put(Month.SEPTEMBER,
                Arrays.asList("leg75", "fru03", "fru04", "fru11", "fru15", "fru16", "fru17", "fru20", "fru21", "fru22",
                        "leg69", "leg01", "leg03", "leg05", "leg06", "leg07", "leg08", "leg10", "leg13", "leg14",
                        "leg15", "leg22", "leg24", "leg25", "leg34", "leg35", "leg39", "leg40", "leg41", "leg43",
                        "leg46", "leg49", "leg50", "leg51", "leg55", "leg56", "leg57", "leg59", "leg60", "leg62",
                        "leg64", "leg74", "leg78", "leg76", "leg80", "fru37", "fru38", "fru43", "leg90", "fru44",
                        "fru45", "fru49", "leg97", "fru19", "fru13", "leg23", "leg36", "leg63", "leg77", "leg79",
                        "fru32", "leg83", "leg84", "leg92", "fru46", "fru47", "fru48", "fru49", "leg44", "leg68",
                        "fru05", "leg96", "leg94", "leg38", "leg91", "leg101", "leg04", "leg42", "leg45", "leg52",
                        "leg66", "leg81", "leg82", "leg85 ", "leg86", "leg89", "leg95", "leg103"));
        seasonalProducts.put(Month.OCTOBER,
                Arrays.asList("leg75", "fru03", "fru11", "fru21", "fru22", "leg69", "leg01", "leg03", "leg05", "leg06",
                        "leg07", "leg08", "leg15", "leg18", "leg21", "leg22", "leg24", "leg25", "leg27", "leg28",
                        "leg29", "leg30", "leg31", "leg32", "leg33", "leg34", "leg35", "leg39", "leg40", "leg41",
                        "leg43", "leg46", "leg47", "leg49", "leg50", "leg51", "leg55", "leg56", "leg57", "leg59",
                        "leg60", "leg62", "leg64", "leg74", "leg78", "leg76", "leg80", "fru35", "fru37", "fru38",
                        "leg90", "fru44", "fru45", "fru49", "fru51", "fru13", "leg102", "leg36", "leg63", "leg77",
                        "leg79", "fru32", "leg83", "fru48", "fru49", "leg44", "leg68", "leg96", "fru23", "leg94",
                        "leg38", "leg91", "leg100", "leg101", "leg102", "leg04", "leg42", "leg45", "leg52", "leg66",
                        "leg81", "leg82", "leg85 ", "leg86", "leg89", "leg95", "leg103"));
        seasonalProducts.put(Month.NOVEMBER,
                Arrays.asList("fru01", "leg75", "fru03", "fru11", "fru22", "leg01", "leg03", "leg05", "leg06", "leg08",
                        "leg09", "leg11", "leg15", "leg18", "leg21", "leg22", "leg24", "leg25", "leg26", "leg27",
                        "leg28", "leg29", "leg30", "leg31", "leg32", "leg33", "leg34", "leg35", "leg39", "leg43",
                        "leg46", "leg47", "leg49", "leg50", "leg51", "leg55", "leg56", "leg57", "leg58", "leg60",
                        "leg80", "fru33", "fru34", "fru35", "fru37", "fru38", "leg90", "fru44", "fru45", "fru51",
                        "leg93", "fru23", "leg98", "fru13", "leg102", "leg19", "leg36", "leg63", "leg77", "leg79",
                        "leg68", "fru23", "leg94", "leg38", "leg91", "leg100", "leg101", "leg102", "leg04", "leg42",
                        "leg45", "leg82", "leg86", "leg89", "leg95", "leg103"));
        seasonalProducts.put(Month.DECEMBER,
                Arrays.asList("fru01", "leg75", "fru03", "fru22", "leg01", "leg05", "leg06", "leg09", "leg11", "leg15",
                        "leg18", "leg21", "leg22", "leg24", "leg25", "leg26", "leg27", "leg28", "leg29", "leg30",
                        "leg31", "leg33", "leg34", "leg37", "leg39", "leg46", "leg47", "leg49", "leg50", "leg51",
                        "leg55", "leg58", "leg60", "leg80", "fru33", "fru34", "fru35", "fru36", "fru37", "fru38",
                        "leg87", "fru42", "leg90", "fru44", "fru45", "fru51", "leg93", "alc15", "fru23", "leg98",
                        "fru13", "leg102", "leg19", "leg63", "leg77", "fru42", "fru23", "leg94", "leg38", "leg91",
                        "leg100", "leg101", "leg102", "leg04", "leg42", "leg45", "leg82", "leg86", "leg89", "leg95",
                        "leg103"));
    }

    private static final Logger LOG = LoggerFactory.getLogger(MouvementsStockService.class);

    private final ObjectMapper objectMapper;

    private final MouvementsStockRepository mouvementsStockRepository;

    private final MouvementsStockMapper mouvementsStockMapper;

    private final AnalysisProgressService progressService;

    public MouvementsStockService(MouvementsStockRepository mouvementsStockRepository,
            MouvementsStockMapper mouvementsStockMapper, ObjectMapper objectMapper, AnalysisProgressService progressService) {
        this.mouvementsStockRepository = mouvementsStockRepository;
        this.mouvementsStockMapper = mouvementsStockMapper;
        this.progressService = progressService;
        this.objectMapper = objectMapper;
    }

    /**
     * Save a mouvementsStock.
     *
     * @param mouvementsStockDTO the entity to save.
     * @return the persisted entity.
     */
    public MouvementsStockDTO save(MouvementsStockDTO mouvementsStockDTO) {
        LOG.debug("Request to save MouvementsStock : {}", mouvementsStockDTO);
        MouvementsStock mouvementsStock = mouvementsStockMapper.toEntity(mouvementsStockDTO);
        mouvementsStock = mouvementsStockRepository.save(mouvementsStock);
        return mouvementsStockMapper.toDto(mouvementsStock);
    }

    /**
     * Update a mouvementsStock.
     *
     * @param mouvementsStockDTO the entity to save.
     * @return the persisted entity.
     */
    public MouvementsStockDTO update(MouvementsStockDTO mouvementsStockDTO) {
        LOG.debug("Request to update MouvementsStock : {}", mouvementsStockDTO);
        MouvementsStock mouvementsStock = mouvementsStockMapper.toEntity(mouvementsStockDTO);
        mouvementsStock = mouvementsStockRepository.save(mouvementsStock);
        return mouvementsStockMapper.toDto(mouvementsStock);
    }

    /**
     * Partially update a mouvementsStock.
     *
     * @param mouvementsStockDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MouvementsStockDTO> partialUpdate(MouvementsStockDTO mouvementsStockDTO) {
        LOG.debug("Request to partially update MouvementsStock : {}", mouvementsStockDTO);

        return mouvementsStockRepository
                .findById(mouvementsStockDTO.getId())
                .map(existingMouvementsStock -> {
                    mouvementsStockMapper.partialUpdate(existingMouvementsStock, mouvementsStockDTO);

                    return existingMouvementsStock;
                })
                .map(mouvementsStockRepository::save)
                .map(mouvementsStockMapper::toDto);
    }

    /**
     * Get all the mouvementsStocks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MouvementsStockDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all MouvementsStocks");
        return mouvementsStockRepository.findAll(pageable).map(mouvementsStockMapper::toDto);
    }

    /**
     * Get one mouvementsStock by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MouvementsStockDTO> findOne(UUID id) {
        LOG.debug("Request to get MouvementsStock : {}", id);
        return mouvementsStockRepository.findById(id).map(mouvementsStockMapper::toDto);
    }

    /**
     * Delete the mouvementsStock by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        LOG.debug("Request to delete MouvementsStock : {}", id);
        mouvementsStockRepository.deleteById(id);
    }

    /**
     * Imports a file containing a list of {@link EpicerioMouvementsStockDTO}
     * objects, processes the data, and saves the corresponding
     * {@link MouvementsStock} entities to the repository.
     *
     * @param file the {@link MultipartFile} to be imported
     * @return a message indicating the number of successfully imported
     *         {@link MouvementsStock} entities
     * @throws Exception if an error occurs during file processing or data
     *                   conversion
     */
    public String importFile(MultipartFile file) throws Exception {
        String content = new String(file.getBytes());
        List<EpicerioMouvementsStockDTO> epicerioMouvementsStocks = objectMapper.readValue(
                content,
                new TypeReference<List<EpicerioMouvementsStockDTO>>() {
                });
        epicerioMouvementsStocks.sort(Comparator.comparing(epicerioMouvementsStock -> epicerioMouvementsStock.getId()));
        int mouvementsStocksTotal = epicerioMouvementsStocks.size();
        List<MouvementsStock> mouvementsStocks = new ArrayList<>(mouvementsStocksTotal);
        Instant now = Instant.now();
        for (EpicerioMouvementsStockDTO epicerioMouvementsStock : epicerioMouvementsStocks) {
            MouvementsStock mouvementsStock = create(epicerioMouvementsStock);
            mouvementsStock.setImportedDate(now);
            mouvementsStocks.add(mouvementsStock);
        }
        mouvementsStockRepository.saveAll(mouvementsStocks);
        return String.format(" %d MouvementsStocks successfuly imported!", mouvementsStocksTotal);
    }

    /**
     * Creates a new MouvementsStock object from the given
     * EpicerioMouvementsStockDTO.
     *
     * @param epicerioMouvementsStocks the DTO containing the data for the new
     *                                 MouvementsStock
     * @return the newly created MouvementsStock object
     */
    public MouvementsStock create(EpicerioMouvementsStockDTO epicerioMouvementsStocks) {
        Instant now = Instant.now();
        MouvementsStock mouvementsStock = new MouvementsStock();
        mouvementsStock.setEpicerioId(epicerioMouvementsStocks.getId());
        mouvementsStock.setCreatedDate(now);
        mouvementsStock.setLastUpdatedDate(now);
        mouvementsStock.setImportedDate(now);
        mouvementsStock.setDate(epicerioMouvementsStocks.getDate());
        mouvementsStock.setUtilisateur(epicerioMouvementsStocks.getUtilisateur());
        mouvementsStock.setType(epicerioMouvementsStocks.getType());
        mouvementsStock.setEpicerioMouvement(epicerioMouvementsStocks.getMouvement());
        mouvementsStock.setMouvement(epicerioMouvementsStocks.getMouvement());
        mouvementsStock.setSolde(epicerioMouvementsStocks.getSolde());
        mouvementsStock.setVente(epicerioMouvementsStocks.getVente());
        mouvementsStock.setCodeProduit(epicerioMouvementsStocks.getCodeProduit());
        mouvementsStock.setProduit(epicerioMouvementsStocks.getProduit());
        mouvementsStock.setResponsableProduit(epicerioMouvementsStocks.getResponsableProduit());
        mouvementsStock.setFournisseurProduit(epicerioMouvementsStocks.getFournisseurProduit());
        mouvementsStock.setCodeFournisseur(epicerioMouvementsStocks.getCodeFournisseur());
        mouvementsStock.setReduction(epicerioMouvementsStocks.getReduction());
        mouvementsStock.setPonderation(epicerioMouvementsStocks.getPonderation());
        mouvementsStock.setVenteChf(epicerioMouvementsStocks.getVenteChf());
        mouvementsStock.setValeurChf(epicerioMouvementsStocks.getValeurChf());
        mouvementsStock.setRemarques(epicerioMouvementsStocks.getRemarques());
        mouvementsStock.setActive(true);
        return mouvementsStock;
    }

    public List<MouvementsStockProjection> findByDateBetween(Instant startDate, Instant endDate) {
        return mouvementsStockRepository
                .findByDateBetween(startDate, endDate)
                .stream()
                .toList();
    }

    public List<MouvementsStockDTO> findByVenteAndDateBetween(String vente, Instant startDate, Instant endDate) {
        return mouvementsStockRepository
                .findByVenteAndDateBetween(vente, startDate, endDate)
                .stream()
                .map(mouvementsStockMapper::toDto)
                .toList();
    }

    public List<MouvementsStockProjection> findByCodeProduitStartingWithAnyAndDateBetween(
            List<String> codeProduitPrefixes, Instant startDate, Instant endDate) {
        return mouvementsStockRepository
                .findByCodeProduitStartingWithAnyAndDateBetween(codeProduitPrefixes, startDate, endDate)
                .stream()
                .toList();
    }

    public Map<String, List<MouvementsStockDTO>> findByInventory(List<MouvementsStockDTO> mouvementsStocks,
            float mouvement) {
        return mouvementsStocks
                .stream()
                .collect(Collectors.groupingBy(MouvementsStockDTO::getCodeProduit))
                .entrySet()
                .stream()
                .map(entry -> {
                    entry.setValue(
                            entry.getValue().stream().sorted(Comparator.comparing(MouvementsStockDTO::getEpicerioId))
                                    .collect(Collectors.toList()));
                    return entry;
                })
                .map(entry -> {
                    entry.setValue(
                            entry
                                    .getValue()
                                    .stream()
                                    .filter(m -> {
                                        if (m.getType().equals("Inventaire") && m.getMouvement() != 0) {
                                            int index = entry.getValue().indexOf(m);
                                            Optional<Float> optionalPreviousSolde = Optional.empty();
                                            if (index > 0) {
                                                optionalPreviousSolde = Optional
                                                        .of(entry.getValue().get(index - 1).getSolde());
                                            } else {
                                                Optional<MouvementsStock> optionalPreviousM = mouvementsStockRepository
                                                        .findFirstByCodeProduitAndVenteAndEpicerioIdLessThanOrderByEpicerioIdDesc(
                                                                m.getCodeProduit(),
                                                                m.getVente(),
                                                                m.getEpicerioId());
                                                optionalPreviousSolde = optionalPreviousM
                                                        .map(previousM -> previousM.getSolde());
                                            }
                                            return optionalPreviousSolde.isPresent()
                                                    ? Math.abs(m.getSolde() - optionalPreviousSolde.get()) >= mouvement
                                                    : false;
                                        } else {
                                            return false;
                                        }
                                    })
                                    .sorted(Comparator.comparing(MouvementsStockDTO::getMouvement))
                                    .collect(Collectors.toList()));
                    return entry;
                })
                .filter(entry -> !entry.getValue().isEmpty())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<Month, List<String>> getSeasonalProductsOverPeriod(Instant start, Instant end) {
        ZoneId zone = ZoneId.of(Constants.TIME_ZONE);
        LocalDate startDate = start.atZone(zone).toLocalDate().withDayOfMonth(1);
        LocalDate endDate = end.atZone(zone).toLocalDate().withDayOfMonth(1);

        List<Integer> months = new ArrayList<>();
        LocalDate current = startDate;
        while (current.isBefore(endDate) || current.isEqual(endDate)) {
            int monthValue = current.getMonthValue();
            if (!months.contains(monthValue)) {
                months.add(monthValue);
            }
            current = current.plusMonths(1);
        }

        return seasonalProducts
                .entrySet()
                .stream()
                .filter(entry -> months.contains(entry.getKey().getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<Integer, List<MonthlyAnalysisStats>> getMonthlyAnalysis(
            List<MouvementsStockProjection> movements,
            String movementsType,
            Instant startDate,
            Instant endDate) {
        ZoneId zone = ZoneId.of(Constants.TIME_ZONE);

        Map<YearMonth, Map<ProductGroupingKey, List<MouvementsStockProjection>>> byYearMonthAndProduct = groupByYearMonthAndProduct(
                movements, zone);

        Map<YearMonth, List<MonthlyAnalysisResult>> yearMonthResults = buildMonthlyAnalysisResults(
                byYearMonthAndProduct, movementsType, zone);

        Map<Integer, Map<ProductGroupingKey, List<AnalysisValues>>> monthlyAverageByProduct = buildMonthlyAverageByProduct(
                yearMonthResults);

        Map<Integer, List<MonthlyAnalysisStats>> productsByMonthNumber = aggregateStatisticsByMonth(
                monthlyAverageByProduct);

        // return filterSeasonalProducts(startDate, endDate, productsByMonthNumber);
        return productsByMonthNumber;
    }

    private Map<ProductGroupingKey, List<MouvementsStockProjection>> groupByProduct(List<MouvementsStockProjection> movements) {
            return movements.stream().collect(Collectors
                            .groupingBy(m -> new ProductGroupingKey(m.getCodeProduit(), m.getProduit(), m.getVente())));
    }

    private Map<String, List<MouvementsStockProjection>> groupByProductCode(List<MouvementsStockProjection> movements) {
            return movements.stream().collect(Collectors.groupingBy(m -> m.getCodeProduit()));
    }

    private Map<YearMonth, Map<ProductGroupingKey, List<MouvementsStockProjection>>> groupByYearMonthAndProduct(
            List<MouvementsStockProjection> movements,
            ZoneId zone) {
        return movements
                .stream()
                .collect(
                        Collectors.groupingBy(
                                m -> YearMonth.from(m.getDate(), zone),
                                Collectors.groupingBy(m -> new ProductGroupingKey(m.getCodeProduit(), m.getProduit(),
                                        m.getVente()))));
    }

    private Map<YearMonth, List<MonthlyAnalysisResult>> buildMonthlyAnalysisResults(
            Map<YearMonth, Map<ProductGroupingKey, List<MouvementsStockProjection>>> byYearMonthAndProduct,
            String movementsType,
            ZoneId zone) {
        Map<YearMonth, List<MonthlyAnalysisResult>> yearMonthResults = new HashMap<>();

        int productKeyTotal = byYearMonthAndProduct.values().stream().mapToInt(byProduct -> byProduct.size()).sum();
        int productKeyCount = 0;
        int productKeyProgress = 0;
        String progressMessage = "Progression de l'analyse mensuelle...";
        for (Map.Entry<YearMonth, Map<ProductGroupingKey, List<MouvementsStockProjection>>> yearMonthEntry : byYearMonthAndProduct
                .entrySet()) {
            YearMonth yearMonth = yearMonthEntry.getKey();
            Map<ProductGroupingKey, List<MouvementsStockProjection>> byProduct = yearMonthEntry.getValue();

            List<MonthlyAnalysisResult> monthlyAnalysisResults = new ArrayList<>();

            for (Map.Entry<ProductGroupingKey, List<MouvementsStockProjection>> productEntry : byProduct.entrySet()) {
                productKeyCount++;
                productKeyProgress = BigDecimal.valueOf(productKeyCount).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(productKeyTotal), 0, RoundingMode.HALF_UP).intValue();
                progressService.emitProgress(productKeyProgress, progressMessage);
                analyzeProductMonth(yearMonth, productEntry.getKey(), productEntry.getValue(), movementsType, zone)
                        .ifPresent(monthlyAnalysisResults::add);
            }

            yearMonthResults.put(yearMonth, monthlyAnalysisResults);
        }

        return yearMonthResults;
    }

    private Optional<MonthlyAnalysisResult> analyzeProductMonth(
            YearMonth yearMonth,
            ProductGroupingKey key,
            List<MouvementsStockProjection> mvts,
            String movementsType,
            ZoneId zone) {
        if (mvts.isEmpty()) {
            return Optional.empty();
        }
        mvts.sort(Comparator.comparing(MouvementsStockProjection::getDate));

        String productCode = key.getCodeProduit();
        Optional<MouvementsStock> lastBeforeMonth = mouvementsStockRepository
                .findFirstByCodeProduitAndDateBeforeOrderByDateDesc(
                        productCode,
                        LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1).atStartOfDay(zone).toInstant());

        float initialStock = lastBeforeMonth.map(MouvementsStock::getSolde).orElse(mvts.get(0).getSolde());
        if (initialStock < 0f) {
            return Optional.empty();
        }

        float closingStock = mvts.get(mvts.size() - 1).getSolde();
        if (closingStock < 0f) {
            return Optional.empty();
        }

        float deliveries = sumMovementsOfType(mvts, "Livraison");
        float available = initialStock + deliveries;
        if (available <= 0f) {
            return Optional.empty();
        }

        float quantity = Math.abs(sumMovementsOfType(mvts, movementsType));
        float percentage = (quantity / available) * 100f;

        return Optional.of(
                new MonthlyAnalysisResult(
                        productCode,
                        key.getProduit(),
                        percentage,
                        quantity,
                        available,
                        countMovementsOfType(mvts, "Livraison"),
                        countMovementsOfType(mvts, "Vente"),
                        countMovementsOfType(mvts, "Perte"),
                        countMovementsOfType(mvts, "Inventaire"),
                        mvts.get(0).getVente()));
    }

    private float sumMovementsOfType(List<MouvementsStockProjection> mvts, String type) {
        return (float) mvts
                .stream()
                .filter(m -> m.getType().equals(type))
                .map(m -> m.getMouvement() == null ? 0f : m.getMouvement())
                .reduce(0f, Float::sum);
    }

    private int countMovementsOfType(List<MouvementsStockProjection> mvts, String type) {
        return mvts
                .stream()
                .filter(m -> m.getType().equals(type))
                .mapToInt(m -> 1)
                .sum();
    }

    private Map<Integer, Map<ProductGroupingKey, List<AnalysisValues>>> buildMonthlyAverageByProduct(
            Map<YearMonth, List<MonthlyAnalysisResult>> yearMonthResults) {
        Map<Integer, Map<ProductGroupingKey, List<AnalysisValues>>> monthlyAverageByProduct = new HashMap<>();

        for (Map.Entry<YearMonth, List<MonthlyAnalysisResult>> entry : yearMonthResults.entrySet()) {
            int monthNum = entry.getKey().getMonth();
            for (MonthlyAnalysisResult pr : entry.getValue()) {
                monthlyAverageByProduct
                        .computeIfAbsent(monthNum, k -> new HashMap<>())
                        .computeIfAbsent(new ProductGroupingKey(pr.getProductCode(), pr.getProduct(), pr.getUnit()),
                                k -> new ArrayList<>())
                        .add(new AnalysisValues(pr.getPercentage(), pr.getQuantity(),
                                pr.getAvailableStock(), pr.getNbDeliveries(), pr.getNbSales(), pr.getNbLosses(),
                                pr.getNbInventories()));
            }
        }

        return monthlyAverageByProduct;
    }

    private Map<Integer, List<MonthlyAnalysisStats>> aggregateStatisticsByMonth(
            Map<Integer, Map<ProductGroupingKey, List<AnalysisValues>>> monthlyAverageByProduct) {
        Map<Integer, List<MonthlyAnalysisStats>> productsByMonthNumber = new HashMap<>();
        Comparator<MonthlyAnalysisStats> byPercentageMean = Comparator
                .comparing(monthlyStats -> monthlyStats.getPercentageStats().getMean());

        for (Map.Entry<Integer, Map<ProductGroupingKey, List<AnalysisValues>>> entry : monthlyAverageByProduct
                .entrySet()) {
            int monthNum = entry.getKey();
            List<MonthlyAnalysisStats> aggregated = new ArrayList<>();

            for (Map.Entry<ProductGroupingKey, List<AnalysisValues>> productEntry : entry.getValue().entrySet()) {
                ProductGroupingKey key = productEntry.getKey();
                List<AnalysisValues> analysisValues = productEntry.getValue();

                DescriptiveStatistics percentageStats = new DescriptiveStatistics();
                DescriptiveStatistics quantityStats = new DescriptiveStatistics();
                DescriptiveStatistics availableStockStats = new DescriptiveStatistics();
                DescriptiveStatistics nbDeliveriesStats = new DescriptiveStatistics();
                DescriptiveStatistics nbSalesStats = new DescriptiveStatistics();
                DescriptiveStatistics nbLossesStats = new DescriptiveStatistics();
                DescriptiveStatistics nbInventoriesStats = new DescriptiveStatistics();

                analysisValues.forEach(v -> {
                    percentageStats.addValue(v.getPercentage());
                    quantityStats.addValue(v.getQuantity());
                    availableStockStats.addValue(v.getAvailableStock());
                    nbDeliveriesStats.addValue(v.getNbDeliveries());
                    nbSalesStats.addValue(v.getNbSales());
                    nbLossesStats.addValue(v.getNbLosses());
                    nbInventoriesStats.addValue(v.getNbInventories());
                });

                aggregated.add(
                        new MonthlyAnalysisStats(
                                key.getCodeProduit(),
                                key.getProduit(),
                                new StatisticalQuantities((float) percentageStats.getMean(),
                                        (float) percentageStats.getStandardDeviation()),
                                new StatisticalQuantities((float) quantityStats.getMean(),
                                        (float) quantityStats.getStandardDeviation()),
                                new StatisticalQuantities((float) availableStockStats.getMean(),
                                        (float) availableStockStats.getStandardDeviation()),
                                new StatisticalQuantities((float) nbDeliveriesStats.getMean(),
                                        (float) nbDeliveriesStats.getStandardDeviation()),
                                new StatisticalQuantities((float) nbSalesStats.getMean(),
                                        (float) nbSalesStats.getStandardDeviation()),
                                new StatisticalQuantities((float) nbLossesStats.getMean(),
                                        (float) nbLossesStats.getStandardDeviation()),
                                new StatisticalQuantities((float) nbInventoriesStats.getMean(),
                                        (float) nbInventoriesStats.getStandardDeviation()),
                                key.getSaleType()));
            }

            List<MonthlyAnalysisStats> sortedAggregated = aggregated.stream()
                    .sorted(byPercentageMean.reversed())
                    .collect(Collectors.toList());
            productsByMonthNumber.put(monthNum, sortedAggregated);
        }

        return productsByMonthNumber;
    }

    private Map<Integer, List<MonthlyAnalysisStats>> filterSeasonalProducts(
            Instant startDate,
            Instant endDate,
            Map<Integer, List<MonthlyAnalysisStats>> productsByMonthNumber) {
        Map<Integer, List<MonthlyAnalysisStats>> monthlyAnalysis = new HashMap<>();
        Map<Month, List<String>> seasonalProductsOverPeriod = getSeasonalProductsOverPeriod(startDate, endDate);
        Comparator<MonthlyAnalysisStats> byPercentageMean = Comparator
                .comparing(monthlyStats -> monthlyStats.getPercentageStats().getMean());

        for (Map.Entry<Month, List<String>> entry : seasonalProductsOverPeriod.entrySet()) {
            int monthNumber = entry.getKey().getValue();
            List<String> seasonal = entry.getValue();

            List<MonthlyAnalysisStats> seasonalProducts = productsByMonthNumber
                    .getOrDefault(monthNumber, Collections.emptyList())
                    .stream()
                    .filter(pr -> seasonal.contains(pr.getProductCode()))
                    .sorted(byPercentageMean.reversed())
                    .collect(Collectors.toList());

            monthlyAnalysis.put(monthNumber, seasonalProducts);
        }

        return monthlyAnalysis;
    }

    public MouvementsStockDateRange getDateRange() {
            MouvementsStockDateRangeProjection dateRangeProjection = mouvementsStockRepository.findDateRange();
            return new MouvementsStockDateRange(dateRangeProjection.getMinDate().toString(),
                            dateRangeProjection.getMaxDate().toString());
    }

    public Instant getMaxDate() {
        return mouvementsStockRepository.findMaxDate();
    }

    public List<TopLossesResult> getTopLosses(List<MouvementsStockProjection> movements, Instant startDate) {
        ZoneId zone = ZoneId.of(Constants.TIME_ZONE);

        Map<String, List<MouvementsStockProjection>> byProduct = groupByProductCode(movements);

        return buildTopLossesResults(startDate, byProduct, zone);
    }

    private List<TopLossesResult> buildTopLossesResults(
                    Instant startDate,
                    Map<String, List<MouvementsStockProjection>> byProduct,
                    ZoneId zone) {
            int productTotal = byProduct.size();
            int productCount = 0;
            int productProgress = 0;
            String progressMessage = "Progression de l'analyse...";

            List<TopLossesResult> topLossesResult = new ArrayList<>();

            for (Map.Entry<String, List<MouvementsStockProjection>> productEntry : byProduct.entrySet()) {
                    productCount++;
                    productProgress = BigDecimal.valueOf(productCount).multiply(BigDecimal.valueOf(100))
                                    .divide(BigDecimal.valueOf(productTotal), 0, RoundingMode.HALF_UP).intValue();
                    progressService.emitProgress(productProgress, progressMessage);
                    analyzeTopLossesProduct(startDate, productEntry.getKey(), productEntry.getValue(), "Perte", zone)
                                    .ifPresent(topLossesResult::add);
            }

            Comparator<TopLossesResult> byPercentage = Comparator.comparing(topLosses -> topLosses.getPercentage());

            return topLossesResult.stream().sorted(byPercentage.reversed()).limit(50).collect(Collectors.toList());
    }

    private Optional<TopLossesResult> analyzeTopLossesProduct(
                    Instant startDate,
                    String productCode,
                    List<MouvementsStockProjection> mvts,
                    String movementsType,
                    ZoneId zone) {
            if (mvts.isEmpty()) {
                    return Optional.empty();
            }
            mvts.sort(Comparator.comparing(MouvementsStockProjection::getDate));

            Optional<MouvementsStock> lastBeforeMonth = mouvementsStockRepository
                            .findFirstByCodeProduitAndDateBeforeOrderByDateDesc(productCode, startDate);

            float initialStock = lastBeforeMonth.map(MouvementsStock::getSolde).orElse(mvts.get(0).getSolde());
            if (initialStock < 0f) {
                    return Optional.empty();
            }

            float closingStock = mvts.get(mvts.size() - 1).getSolde();
            if (closingStock < 0f) {
                    return Optional.empty();
            }

            float deliveries = sumMovementsOfType(mvts, "Livraison");
            float available = initialStock + deliveries;
            if (available <= 0f) {
                    return Optional.empty();
            }

            float quantity = Math.abs(sumMovementsOfType(mvts, movementsType));
            float percentage = (quantity / available) * 100f;

            return Optional.of(
                            new TopLossesResult(
                                            productCode,
                                            mvts.get(mvts.size() - 1).getProduit(),
                                            percentage,
                                            quantity,
                                            mvts.get(0).getVente()));
    }

    public List<LowestSalesResult> getLowestSales(List<MouvementsStockProjection> movements, Instant startDate) {
        ZoneId zone = ZoneId.of(Constants.TIME_ZONE);

        Map<String, List<MouvementsStockProjection>> byProduct = groupByProductCode(movements);

        return buildLowestSalesResults(startDate, byProduct, zone);
    }

    private List<LowestSalesResult> buildLowestSalesResults(
                    Instant startDate,
                    Map<String, List<MouvementsStockProjection>> byProduct,
                    ZoneId zone) {
            int productTotal = byProduct.size();
            int productCount = 0;
            int productProgress = 0;
            String progressMessage = "Progression de l'analyse...";

            List<LowestSalesResult> lowestSalesResult = new ArrayList<>();

            for (Map.Entry<String, List<MouvementsStockProjection>> productEntry : byProduct.entrySet()) {
                    productCount++;
                    productProgress = BigDecimal.valueOf(productCount).multiply(BigDecimal.valueOf(100))
                                    .divide(BigDecimal.valueOf(productTotal), 0, RoundingMode.HALF_UP).intValue();
                    progressService.emitProgress(productProgress, progressMessage);
                    analyzeLowestSalesProduct(startDate, productEntry.getKey(), productEntry.getValue(), "Vente", zone)
                                    .ifPresent(lowestSalesResult::add);
            }

            Comparator<LowestSalesResult> byPercentage = Comparator.comparing(lowestSales -> lowestSales.getPercentage());

            return lowestSalesResult.stream().sorted(byPercentage).limit(50).collect(Collectors.toList());
    }

    private Optional<LowestSalesResult> analyzeLowestSalesProduct(
                    Instant startDate,
                    String productCode,
                    List<MouvementsStockProjection> mvts,
                    String movementsType,
                    ZoneId zone) {
            if (mvts.isEmpty()) {
                    return Optional.empty();
            }
            mvts.sort(Comparator.comparing(MouvementsStockProjection::getDate));

            Optional<MouvementsStock> lastBeforeMonth = mouvementsStockRepository
                            .findFirstByCodeProduitAndDateBeforeOrderByDateDesc(productCode, startDate);

            float initialStock = lastBeforeMonth.map(MouvementsStock::getSolde).orElse(mvts.get(0).getSolde());
            if (initialStock < 0f) {
                    return Optional.empty();
            }

            float closingStock = mvts.get(mvts.size() - 1).getSolde();
            if (closingStock < 0f) {
                    return Optional.empty();
            }

            float deliveries = sumMovementsOfType(mvts, "Livraison");
            float available = initialStock + deliveries;
            if (available <= 0f) {
                    return Optional.empty();
            }

            float quantity = Math.abs(sumMovementsOfType(mvts, movementsType));
            float percentage = (quantity / available) * 100f;

            return Optional.of(
                            new LowestSalesResult(
                                            productCode,
                                            mvts.get(mvts.size() - 1).getProduit(),
                                            percentage,
                                            quantity,
                                            mvts.get(0).getVente()));
    }
}
