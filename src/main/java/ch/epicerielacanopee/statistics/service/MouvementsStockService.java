package ch.epicerielacanopee.statistics.service;

import ch.epicerielacanopee.statistics.domain.MouvementsStock;
import ch.epicerielacanopee.statistics.repository.MouvementsStockRepository;
import ch.epicerielacanopee.statistics.service.dto.EpicerioMouvementsStockDTO;
import ch.epicerielacanopee.statistics.service.dto.MouvementsStockDTO;
import ch.epicerielacanopee.statistics.service.dto.TopSellingProductResult;
import ch.epicerielacanopee.statistics.service.mapper.MouvementsStockMapper;
import ch.epicerielacanopee.statistics.service.util.YearWeek;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Implementation for managing
 * {@link ch.epicerielacanopee.statistics.domain.MouvementsStock}.
 */
@Service
@Transactional
public class MouvementsStockService {

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

    public List<MouvementsStockDTO> findByDateBetween(Instant startDate, Instant endDate) {
        return mouvementsStockRepository
            .findByDateBetween(startDate, endDate)
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

    /**
     * Computes the top 5 selling products per week based on stock movements.
     * <p>
     * For each week present in the provided list of stock movements, this method calculates,
     * for each product, the percentage and quantity of stock sold relative to the available stock
     * (initial stock plus deliveries) during that week. It then selects the top 5 products with the
     * highest percentage sold for each week.
     * </p>
     *
     * <p>
     * The calculation for each product in a week is as follows:
     * <ul>
     *   <li>Initial stock is determined as the last known balance before the week, or the first balance observed during the week if none exists before.</li>
     *   <li>Deliveries are summed from all "Livraison" movements during the week.</li>
     *   <li>Available stock is the sum of initial stock and deliveries.</li>
     *   <li>Final stock is the last known balance during the week, or the initial stock if there were no movements.</li>
     *   <li>Sold quantity is the difference between available stock and final stock (never negative).</li>
     *   <li>Sold percentage is the sold quantity divided by available stock, expressed as a percentage.</li>
     * </ul>
     * </p>
     *
     * <p>
     * Only products with sufficient data (known initial and final stock, and available stock &gt; 0)
     * are considered. For each week, the top 5 products are selected by descending sold percentage,
     * then by sold quantity.
     * </p>
     *
     * @param movements the list of stock movement DTOs to analyze
     * @return a map where each key is a {@link YearWeek} and the value is a list of up to 5 {@link TopSellingProductResult}
     *         objects representing the top selling products for that week, sorted by descending sold percentage
     */
    public Map<YearWeek, List<TopSellingProductResult>> findTop5SellingProductsPerWeek(List<MouvementsStockDTO> movements) {
        // Index by product, sorted by date
        Map<String, List<MouvementsStockDTO>> movementsByProduct = movements
            .stream()
            .filter(m -> m.getCodeProduit() != null)
            .collect(
                Collectors.groupingBy(
                    MouvementsStockDTO::getCodeProduit,
                    Collectors.collectingAndThen(Collectors.toList(), list ->
                        list.stream().sorted(Comparator.comparing(MouvementsStockDTO::getDate)).collect(Collectors.toList())
                    )
                )
            );

        // Build the set of present weeks
        WeekFields weekFields = WeekFields.ISO; // ISO weeks (Monday as the first day)
        ZoneId zone = ZoneId.of("Europe/Zurich");
        Set<YearWeek> weeks = movements
            .stream()
            .map(m -> YearWeek.from(m.getDate().atZone(zone), weekFields))
            .collect(Collectors.toCollection(TreeSet::new));

        Map<YearWeek, List<TopSellingProductResult>> top5ByWeek = new TreeMap<>();

        for (YearWeek yw : weeks) {
            List<TopSellingProductResult> results = new ArrayList<>();

            // Start/end of week as Instant for filtering
            LocalDate firstDay = LocalDate.of(yw.getYear(), 1, 4) // ISO week 1 contains Jan 4
                .with(weekFields.weekOfWeekBasedYear(), yw.getWeek())
                .with(weekFields.dayOfWeek(), 1);
            LocalDate lastDay = firstDay.plusDays(6);

            Instant weekStart = firstDay.atStartOfDay(zone).toInstant();
            Instant weekEnd = lastDay.plusDays(1).atStartOfDay(zone).toInstant(); // exclusive

            for (Map.Entry<String, List<MouvementsStockDTO>> entry : movementsByProduct.entrySet()) {
                String product = entry.getKey();
                List<MouvementsStockDTO> productMovements = entry.getValue();

                // Movement before the week (for initial stock)
                Optional<MouvementsStockDTO> lastBeforeWeek = lastBefore(productMovements, weekStart);
                Float initialStock = lastBeforeWeek.map(MouvementsStockDTO::getSolde).orElse(null);

                // Movements during the week
                List<MouvementsStockDTO> weekMovements = productMovements
                    .stream()
                    .filter(m -> !m.getDate().isBefore(weekStart) && m.getDate().isBefore(weekEnd))
                    .collect(Collectors.toList());

                if (weekMovements.isEmpty() && initialStock == null) continue; // no usable information

                if (initialStock == null && !weekMovements.isEmpty()) {
                    // fallback: take the first balance observed during the week as the initial stock
                    initialStock = weekMovements.get(0).getSolde();
                }

                // Deliveries of the week
                float deliveries = (float) weekMovements
                    .stream()
                    .filter(m -> m.getType().equals("Livraison"))
                    .map(m -> m.getMouvement() == null ? 0f : m.getMouvement())
                    .reduce(0f, Float::sum);

                // Final stock: last known balance during the week, otherwise reuse the last before if there was no movement
                Float finalStock = null;
                if (!weekMovements.isEmpty()) {
                    finalStock = weekMovements.get(weekMovements.size() - 1).getSolde();
                } else {
                    finalStock = initialStock; // no movement → stock unchanged
                }

                if (initialStock == null || finalStock == null) continue; // do not produce incomplete results

                float availableStock = initialStock + deliveries;
                if (availableStock <= 0f) continue; // avoid division by zero or irrelevant cases

                float soldQuantity = availableStock - finalStock;
                if (soldQuantity < 0f) soldQuantity = 0f; // protection against corrections/inventories that increase the stock

                float soldPercentage = (soldQuantity / availableStock) * 100f;

                results.add(new TopSellingProductResult(product, soldPercentage, soldQuantity, availableStock));
            }

            // Top 5 by descending % sold
            List<TopSellingProductResult> top5 = results
                .stream()
                .sorted(
                    Comparator.comparing(TopSellingProductResult::getSoldPercentage)
                        .reversed()
                        .thenComparing(TopSellingProductResult::getSoldQuantity)
                        .reversed()
                )
                .limit(5)
                .collect(Collectors.toList());

            if (!top5.isEmpty()) {
                top5ByWeek.put(yw, top5);
            }
        }

        return top5ByWeek;
    }

    /**
     * Returns the last {@link MouvementsStockDTO} from the given list whose date is before the specified instant.
     * <p>
     * The method iterates through the provided list of movements and keeps track of the last movement
     * whose date is strictly before the given {@code instant}. The search stops as soon as a movement
     * with a date not before the instant is encountered, assuming the list is sorted in ascending order by date.
     *
     * @param movements the list of {@link MouvementsStockDTO} to search, assumed to be sorted by date in ascending order
     * @param instant the {@link Instant} to compare movement dates against
     * @return an {@link Optional} containing the last movement before the specified instant, or empty if none found
     */
    private static Optional<MouvementsStockDTO> lastBefore(List<MouvementsStockDTO> movements, Instant instant) {
        MouvementsStockDTO last = null;
        for (MouvementsStockDTO m : movements) {
            if (m.getDate().isBefore(instant)) {
                last = m;
            } else {
                break;
            }
        }
        return Optional.ofNullable(last);
    }
}
