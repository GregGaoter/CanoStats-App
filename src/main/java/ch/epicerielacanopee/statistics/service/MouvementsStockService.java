package ch.epicerielacanopee.statistics.service;

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
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ch.epicerielacanopee.statistics.domain.MouvementsStock;
import ch.epicerielacanopee.statistics.repository.MouvementsStockRepository;
import ch.epicerielacanopee.statistics.service.dto.EpicerioMouvementsStockDTO;
import ch.epicerielacanopee.statistics.service.dto.MouvementsStockDTO;
import ch.epicerielacanopee.statistics.service.dto.TopSellingProductResult;
import ch.epicerielacanopee.statistics.service.mapper.MouvementsStockMapper;
import ch.epicerielacanopee.statistics.service.util.ProductGroupingKey;
import ch.epicerielacanopee.statistics.service.util.SoldValues;
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
                        "fru42", "leg90", "fru44", "fru45", "fru51", "leg93", "alc15", "fru23", "leg98"));
        seasonalProducts.put(Month.FEBRUARY,
                Arrays.asList("fru01", "leg75", "fru03", "fru22", "leg01", "leg05", "leg06", "leg08", "leg09", "leg11",
                        "leg15", "leg18", "leg21", "leg25", "leg26", "leg27", "leg28", "leg29", "leg30", "leg31",
                        "leg33", "leg37", "leg47", "leg49", "leg50", "leg51", "leg55", "leg58", "leg60", "leg80",
                        "boi10", "fru33", "fru35", "fru36", "fru38", "leg87", "leg90", "fru44", "fru45", "fru51",
                        "leg93", "fru23", "leg98", "leg104"));
        seasonalProducts.put(Month.MARCH,
                Arrays.asList("fru01", "leg75", "fru03", "fru22", "leg01", "leg05", "leg06", "leg08", "leg09", "leg15",
                        "leg18", "leg21", "leg25", "leg26", "leg27", "leg28", "leg29", "leg30", "leg31", "leg32",
                        "leg33", "leg47", "leg48", "leg49", "leg51", "leg55", "leg58", "leg60", "leg80", "fru35",
                        "fru36", "fru38", "leg90", "fru44", "fru45", "fru51", "leg93", "fru23"));
        seasonalProducts.put(Month.APRIL,
                Arrays.asList("fru01", "leg75", "fru03", "fru22", "fru30", "leg69", "leg01", "leg03", "leg05", "leg06",
                        "leg08", "leg09", "leg10", "leg16", "leg17", "leg21", "leg25", "leg28", "leg31", "leg33",
                        "leg34", "leg47", "leg48", "leg49", "leg51", "leg55", "leg58", "leg60", "leg76", "leg80",
                        "fru38", "leg90", "fru44", "fru45", "fru51", "leg104"));
        seasonalProducts.put(Month.MAY,
                Arrays.asList("leg75", "fru03", "fru22", "fru30", "leg69", "leg01", "leg03", "leg05", "leg06", "leg07",
                        "leg08", "leg09", "leg10", "leg16", "leg17", "leg21", "leg22", "leg25", "leg28", "leg33",
                        "leg34", "leg35", "leg40", "leg48", "leg49", "leg51", "leg59", "leg60", "leg62", "leg74",
                        "leg76", "leg80", "fru38", "leg90", "fru44", "fru45", "fru02"));
        seasonalProducts.put(Month.JUNE,
                Arrays.asList("leg75", "fru03", "fru04", "fru08", "fru10", "fru14", "fru16", "fru17", "fru22", "fru30",
                        "fru31", "leg69", "leg01", "leg03", "leg05", "leg06", "leg07", "leg08", "leg10", "leg14",
                        "leg22", "leg24", "leg34", "leg35", "leg40", "leg43", "leg48", "leg51", "leg54", "leg59",
                        "leg62", "leg74", "leg78", "leg76", "leg90", "fru44", "fru45", "fru02"));
        seasonalProducts.put(Month.JULY,
                Arrays.asList("leg75", "fru03", "fru04", "fru07", "fru08", "fru10", "fru14", "fru15", "fru16", "fru17",
                        "fru20", "fru31", "leg69", "leg02", "leg03", "leg05", "leg06", "leg07", "leg08", "leg10",
                        "leg13", "leg14", "leg22", "leg24", "leg34", "leg35", "leg40", "leg48", "leg54", "leg57",
                        "leg59", "leg62", "leg74", "leg78", "leg76", "fru38", "fru43", "leg90", "fru44", "fru45",
                        "fru02", "fru19"));
        seasonalProducts.put(Month.AUGUST,
                Arrays.asList("leg75", "fru03", "fru04", "fru07", "fru08", "fru14", "fru15", "fru16", "fru17", "fru20",
                        "fru31", "leg69", "leg03", "leg05", "leg06", "leg07", "leg08", "leg10", "leg13", "leg14",
                        "leg22", "leg24", "leg25", "leg34", "leg35", "leg40", "leg41", "leg43", "leg48", "leg54",
                        "leg57", "leg59", "leg62", "leg74", "leg78", "leg76", "fru37", "fru38", "fru42", "fru43",
                        "leg90", "fru44", "fru45", "fru49", "leg97", "fru19"));
        seasonalProducts.put(Month.SEPTEMBER,
                Arrays.asList("leg75", "fru03", "fru04", "fru11", "fru15", "fru16", "fru17", "fru20", "fru21", "fru22",
                        "leg69", "leg01", "leg03", "leg05", "leg06", "leg07", "leg08", "leg10", "leg13", "leg14",
                        "leg15", "leg22", "leg24", "leg25", "leg34", "leg35", "leg39", "leg40", "leg41", "leg43",
                        "leg46", "leg49", "leg50", "leg51", "leg55", "leg56", "leg57", "leg59", "leg60", "leg62",
                        "leg64", "leg74", "leg78", "leg76", "leg80", "fru37", "fru38", "fru43", "leg90", "fru44",
                        "fru45", "fru49", "leg97", "fru19"));
        seasonalProducts.put(Month.OCTOBER,
                Arrays.asList("leg75", "fru03", "fru11", "fru21", "fru22", "leg69", "leg01", "leg03", "leg05", "leg06",
                        "leg07", "leg08", "leg15", "leg18", "leg21", "leg22", "leg24", "leg25", "leg27", "leg28",
                        "leg29", "leg30", "leg31", "leg32", "leg33", "leg34", "leg35", "leg39", "leg40", "leg41",
                        "leg43", "leg46", "leg47", "leg49", "leg50", "leg51", "leg55", "leg56", "leg57", "leg59",
                        "leg60", "leg62", "leg64", "leg74", "leg78", "leg76", "leg80", "fru35", "fru37", "fru38",
                        "leg90", "fru44", "fru45", "fru49", "fru51"));
        seasonalProducts.put(Month.NOVEMBER,
                Arrays.asList("fru01", "leg75", "fru03", "fru11", "fru22", "leg01", "leg03", "leg05", "leg06", "leg08",
                        "leg09", "leg11", "leg15", "leg18", "leg21", "leg22", "leg24", "leg25", "leg26", "leg27",
                        "leg28", "leg29", "leg30", "leg31", "leg32", "leg33", "leg34", "leg35", "leg39", "leg43",
                        "leg46", "leg47", "leg49", "leg50", "leg51", "leg55", "leg56", "leg57", "leg58", "leg60",
                        "leg80", "fru33", "fru34", "fru35", "fru37", "fru38", "leg90", "fru44", "fru45", "fru51",
                        "leg93", "fru23", "leg98"));
        seasonalProducts.put(Month.DECEMBER,
                Arrays.asList("fru01", "leg75", "fru03", "fru22", "leg01", "leg05", "leg06", "leg09", "leg11", "leg15",
                        "leg18", "leg21", "leg22", "leg24", "leg25", "leg26", "leg27", "leg28", "leg29", "leg30",
                        "leg31", "leg33", "leg34", "leg37", "leg39", "leg46", "leg47", "leg49", "leg50", "leg51",
                        "leg55", "leg58", "leg60", "leg80", "fru33", "fru34", "fru35", "fru36", "fru37", "fru38",
                        "leg87", "fru42", "leg90", "fru44", "fru45", "fru51", "leg93", "alc15", "fru23", "leg98"));
    }

    private static final Logger LOG = LoggerFactory.getLogger(MouvementsStockService.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final MouvementsStockRepository mouvementsStockRepository;

    private final MouvementsStockMapper mouvementsStockMapper;

    public MouvementsStockService(MouvementsStockRepository mouvementsStockRepository, MouvementsStockMapper mouvementsStockMapper) {
        this.mouvementsStockRepository = mouvementsStockRepository;
        this.mouvementsStockMapper = mouvementsStockMapper;
        objectMapper.registerModule(new JavaTimeModule());
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
            new TypeReference<List<EpicerioMouvementsStockDTO>>() {}
        );
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

    public List<MouvementsStockDTO> findByVenteAndDateBetween(String vente, Instant startDate, Instant endDate) {
        return mouvementsStockRepository
            .findByVenteAndDateBetween(vente, startDate, endDate)
            .stream()
            .map(mouvementsStockMapper::toDto)
            .toList();
    }

    public List<MouvementsStockDTO> findLegByDateBetween(Instant startDate, Instant endDate) {
        return mouvementsStockRepository
            .findByDateBetweenAndCodeProduitStartingWith(startDate, endDate, "leg")
            .stream()
            .map(mouvementsStockMapper::toDto)
            .toList();
    }

    public Map<String, List<MouvementsStockDTO>> findByInventory(List<MouvementsStockDTO> mouvementsStocks, float mouvement) {
        return mouvementsStocks
            .stream()
            .collect(Collectors.groupingBy(MouvementsStockDTO::getCodeProduit))
            .entrySet()
            .stream()
            .map(entry -> {
                entry.setValue(
                    entry.getValue().stream().sorted(Comparator.comparing(MouvementsStockDTO::getEpicerioId)).collect(Collectors.toList())
                );
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
                                    optionalPreviousSolde = Optional.of(entry.getValue().get(index - 1).getSolde());
                                } else {
                                    Optional<MouvementsStock> optionalPreviousM =
                                        mouvementsStockRepository.findFirstByCodeProduitAndVenteAndEpicerioIdLessThanOrderByEpicerioIdDesc(
                                            m.getCodeProduit(),
                                            m.getVente(),
                                            m.getEpicerioId()
                                        );
                                    optionalPreviousSolde = optionalPreviousM.map(previousM -> previousM.getSolde());
                                }
                                return optionalPreviousSolde.isPresent()
                                    ? Math.abs(m.getSolde() - optionalPreviousSolde.get()) >= mouvement
                                    : false;
                            } else {
                                return false;
                            }
                        })
                        .sorted(Comparator.comparing(MouvementsStockDTO::getMouvement))
                        .collect(Collectors.toList())
                );
                return entry;
            })
            .filter(entry -> !entry.getValue().isEmpty())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<Month, List<String>> getSeasonalProductsOverPeriod(Instant start, Instant end) {
        ZoneId zone = ZoneId.of("Europe/Zurich");
        LocalDate startDate = start.atZone(zone).toLocalDate().withDayOfMonth(1);
        LocalDate endDate = end.atZone(zone).toLocalDate().withDayOfMonth(1);

        List<Integer> months = new ArrayList<>();
        LocalDate current = startDate;
        while (current.isBefore(endDate) || current.isEqual(endDate)) {
            int monthValue = current.getMonthValue();
            if(!months.contains(monthValue)) {
                months.add(monthValue);
            }
            current = current.plusMonths(1);
        }

        Map<Month, List<String>> seasonalProductsOverPeriod = seasonalProducts.entrySet().stream().filter(entry -> months.contains(entry.getKey().getValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        
        return seasonalProductsOverPeriod;
    }

    public Map<Integer, List<TopSellingProductResult>> buildMonthlySeasonalPlan(List<MouvementsStockDTO> movements, Instant startDate, Instant endDate) {
        ZoneId zone = ZoneId.of("Europe/Zurich");

        // 1. Group by product and by year/month
        Map<YearMonth, Map<ProductGroupingKey, List<MouvementsStockDTO>>> byYearMonthAndProduct = movements.stream()
            .collect(Collectors.groupingBy(
                m -> YearMonth.from(m.getDate(), zone),
                Collectors.groupingBy(m -> new ProductGroupingKey(m.getCodeProduit(), m.getProduit(), m.getVente()))
            ));

        // 2. Calculate sales and percentages by YearMonth/product
        Map<YearMonth, List<TopSellingProductResult>> yearMonthResults = new HashMap<>();

        for (Map.Entry<YearMonth, Map<ProductGroupingKey, List<MouvementsStockDTO>>> yearMonthEntry : byYearMonthAndProduct.entrySet()) {
            YearMonth yearMonth = yearMonthEntry.getKey();
            Map<ProductGroupingKey, List<MouvementsStockDTO>> byProduct = yearMonthEntry.getValue();

            List<TopSellingProductResult> productResults = new ArrayList<>();

            for (Map.Entry<ProductGroupingKey, List<MouvementsStockDTO>> productEntry : byProduct.entrySet()) {
                List<MouvementsStockDTO> mvts = productEntry.getValue();
                if (mvts.isEmpty()) continue;
                mvts.sort(Comparator.comparing(MouvementsStockDTO::getDate));

                ProductGroupingKey key = productEntry.getKey();
                String productCode = key.getCodeProduit();

                // Last movement before the month (for initial stock)
                Optional<MouvementsStock> lastBeforeMonth = mouvementsStockRepository.findFirstByCodeProduitAndDateBeforeOrderByDateDesc(productCode, LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1).atStartOfDay(zone).toInstant());
                float initialStock = lastBeforeMonth.isPresent() ? lastBeforeMonth.get().getSolde() : mvts.get(0).getSolde();
                if (initialStock < 0f) continue;

                // Stock final = last balance in month
                float finalStock = mvts.get(mvts.size() - 1).getSolde();
                if (finalStock < 0f) continue;

                // Deliveries during month
                float deliveries = (float) mvts.stream()
                    .filter(m -> m.getType().equals("Livraison"))
                    .map(m -> m.getMouvement() == null ? 0f : m.getMouvement())
                    .reduce(0f, Float::sum);

                float available = initialStock + deliveries;
                if (available <= 0) continue;

                // Sales during month
                float soldQuantity = Math.abs((float) mvts.stream()
                    .filter(m -> m.getType().equals("Vente"))
                    .map(m -> m.getMouvement() == null ? 0f : m.getMouvement())
                    .reduce(0f, Float::sum));

                float soldPercentage = (soldQuantity / available) * 100f;

                productResults.add(new TopSellingProductResult(productCode, key.getProduit(), soldPercentage, 0f, soldQuantity, 0f, mvts.get(0).getVente()));
            }

            yearMonthResults.put(yearMonth, productResults);
        }

        // For each month, group the results by product
        Map<Integer, Map<ProductGroupingKey, List<SoldValues>>> monthlyAverageByProduct = new HashMap<>();

        for (Map.Entry<YearMonth, List<TopSellingProductResult>> entry : yearMonthResults.entrySet()) {
            int monthNum = entry.getKey().getMonth();
            for (TopSellingProductResult pr : entry.getValue()) {
                monthlyAverageByProduct
                    .computeIfAbsent(monthNum, k -> new HashMap<>())
                    .computeIfAbsent(new ProductGroupingKey(pr.getProductCode(), pr.getProduct(), pr.getSaleType()), k -> new ArrayList<>())
                    .add(new SoldValues(pr.getSoldPercentageAverage(), pr.getSoldQuantityAverage()));
            }
        }

        // Calculate the average per product and per month
        Map<Integer, List<TopSellingProductResult>> topProductsByMonthNumber = new HashMap<>();

        for (Map.Entry<Integer, Map<ProductGroupingKey, List<SoldValues>>> entry : monthlyAverageByProduct.entrySet()) {
            int monthNum = entry.getKey();
            List<TopSellingProductResult> aggregated = new ArrayList<>();

            for (Map.Entry<ProductGroupingKey, List<SoldValues>> productEntry : entry.getValue().entrySet()) {
                ProductGroupingKey key = productEntry.getKey();
                List<SoldValues> soldValues = productEntry.getValue();

                DescriptiveStatistics percentageStats = new DescriptiveStatistics();
                soldValues.forEach(v -> percentageStats.addValue(v.getSoldPercentage()));

                DescriptiveStatistics quantityStats = new DescriptiveStatistics();
                soldValues.forEach(v -> quantityStats.addValue(v.getSoldQuantity()));

                aggregated.add(new TopSellingProductResult(key.getCodeProduit(), key.getProduit(), (float) percentageStats.getMean(), (float) percentageStats.getStandardDeviation() * 100f, (float) quantityStats.getMean(), (float) quantityStats.getStandardDeviation() * 100f, key.getSaleType()));
            }
            topProductsByMonthNumber.put(monthNum, aggregated);
        }

        // 3. Aggregate over multiple years: average of percentages and quantities
        Map<Integer, List<TopSellingProductResult>> seasonalPlan = new HashMap<>();

        Map<Month, List<String>> seasonalProductsOverPeriod = getSeasonalProductsOverPeriod(startDate, endDate);

        for (Map.Entry<Month, List<String>> entry : seasonalProductsOverPeriod.entrySet()) {
            int monthNumber = entry.getKey().getValue();
            List<String> seasonal = entry.getValue();

            List<TopSellingProductResult> seasonalProducts = topProductsByMonthNumber.getOrDefault(monthNumber, Collections.emptyList()).stream()
                .filter(pr -> seasonal.contains(pr.getProductCode()))
                .sorted(Comparator.comparing(TopSellingProductResult::getSoldPercentageAverage).reversed())
                .collect(Collectors.toList());

            seasonalPlan.put(monthNumber, seasonalProducts);
        }

        return seasonalPlan;
    }
}
