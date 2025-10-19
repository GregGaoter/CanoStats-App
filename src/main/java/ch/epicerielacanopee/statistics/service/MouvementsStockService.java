package ch.epicerielacanopee.statistics.service;

import ch.epicerielacanopee.statistics.domain.MouvementsStock;
import ch.epicerielacanopee.statistics.repository.MouvementsStockRepository;
import ch.epicerielacanopee.statistics.service.dto.EpicerioMouvementsStockDTO;
import ch.epicerielacanopee.statistics.service.dto.MouvementsStockDTO;
import ch.epicerielacanopee.statistics.service.dto.TopSellingProductResult;
import ch.epicerielacanopee.statistics.service.mapper.MouvementsStockMapper;
import ch.epicerielacanopee.statistics.service.util.ProductGroupingKey;
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
import java.util.HashMap;
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

    /**
     * Compute the top-5 selling products for each ISO week number, aggregated across years.
     *
     * <p>Algorithm overview:
     * <ul>
     *   <li>Group input movements by product (combination of codeProduit and produit) and sort each group's movements by date ascending.</li>
     *   <li>Determine the set of YearWeek values present in the input (ISO weeks, Monday-first) using the Europe/Zurich time zone.</li>
     *   <li>For each YearWeek present:
     *     <ul>
     *       <li>Compute the week start (inclusive) and end (exclusive) Instants in Europe/Zurich.</li>
     *       <li>For each product:
     *         <ul>
     *           <li>Find the last movement strictly before the week start to determine the initial stock (solde).</li>
     *           <li>If no prior movement exists but at least one movement occurs during the week, use the first movement's solde during the week as a fallback initial stock.</li>
     *           <li>Collect deliveries during the week (movements with type.equals("Livraison")) and sum their mouvement values (null treated as 0).</li>
     *           <li>Determine the final stock as the last solde observed during the week, or reuse the initial stock if there were no movements in the week.</li>
     *           <li>If initial or final stock is missing, or if availableStock (initialStock + deliveries) &le; 0, skip the product for that week.</li>
     *           <li>Compute soldQuantity = max(availableStock - finalStock, 0) and soldPercentage = (soldQuantity / availableStock) * 100.</li>
     *         </ul>
     *       </li>
     *       <li>Collect TopSellingProductResult instances for all products with valid metrics and select the top 5 by:
     *         <ol>
     *           <li>descending soldPercentage</li>
     *           <li>then descending soldQuantity</li>
     *         </ol>
     *       </li>
     *     </ul>
     *   </li>
     *   <li>After processing all YearWeek instances, group the per-week (YearWeek) top-5 results by ISO week number (1..53), then for each product compute the average soldPercentage across the different years in which it appeared in that week's top-5.</li>
     *   <li>For each week number return the top 5 products ordered by descending average soldPercentage. The returned TopSellingProductResult objects contain averaged soldPercentage; soldQuantity and availableStock are set to 0 for these aggregated results.</li>
     * </ul>
     *
     * <p>Notes, assumptions and important details:
     * <ul>
     *   <li>Input movements must provide:
     *     <ul>
     *       <li>non-null codeProduit and produit to be considered for grouping;</li>
     *       <li>a date accessible as an Instant (the method calls m.getDate().atZone(zone));</li>
     *       <li>solde (Float), mouvement (Float) and type (String) fields used for calculations.</li>
     *     </ul>
     *   </li>
     *   <li>Week calculations use ISO week definitions (WeekFields.ISO) and the Europe/Zurich time zone.</li>
     *   <li>If a product has no usable information for a particular YearWeek (no initial or final stock and no movements), it will be skipped for that week.</li>
     *   <li>Negative sold quantities (resulting from stock increases due to corrections or inventories) are clamped to zero.</li>
     *   <li>Deliveries are identified strictly by type.equals("Livraison"). Movements with null mouvement values are treated as zero quantity.</li>
     *   <li>Final aggregation returns a Map keyed by ISO week number (Integer) mapping to a list of up to 5 TopSellingProductResult entries representing the highest average soldPercentage across all years present in the data.</li>
     *   <li>The method does not modify the input list and does not throw checked exceptions. It is not synchronized; callers should handle concurrency if the same input collection is shared concurrently.</li>
     * </ul>
     *
     * @param movements a list of MouvementsStockDTO instances (may be empty). Each DTO's date is interpreted in the Europe/Zurich time zone and weeks use ISO week rules.
     * @return a map from ISO week number (1..53) to a list (up to 5 items) of TopSellingProductResult representing the products with the highest average sold percentage for that week number across all years present in the input. If no products qualify for a given week number, that week number will not be present in the map.
     */
    public Map<Integer, List<TopSellingProductResult>> findTop5SellingProductsPerWeek(List<MouvementsStockDTO> movements) {
        // Index by product, sorted by date
        Map<ProductGroupingKey, List<MouvementsStockDTO>> movementsByProduct = movements
            .stream()
            .filter(m -> m.getCodeProduit() != null && m.getProduit() != null)
            .collect(
                Collectors.groupingBy(
                    m -> new ProductGroupingKey(m.getCodeProduit(), m.getProduit()),
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

            for (Map.Entry<ProductGroupingKey, List<MouvementsStockDTO>> entry : movementsByProduct.entrySet()) {
                ProductGroupingKey key = entry.getKey();
                String productCode = key.getCodeProduit();
                String product = key.getProduit();
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

                results.add(new TopSellingProductResult(productCode, product, soldPercentage, soldQuantity, availableStock));
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

        // For each week number, identify the products that had the highest average sales rates across all available years.

        // For each week, group the results by product
        Map<Integer, Map<ProductGroupingKey, List<Float>>> soldPercentagesByWeekAndProduct = new HashMap<>();

        for (Map.Entry<YearWeek, List<TopSellingProductResult>> entry : top5ByWeek.entrySet()) {
            int weekNum = entry.getKey().getWeek();
            for (TopSellingProductResult pr : entry.getValue()) {
                soldPercentagesByWeekAndProduct
                    .computeIfAbsent(weekNum, k -> new HashMap<>())
                    .computeIfAbsent(new ProductGroupingKey(pr.getProductCode(), pr.getProduct()), k -> new ArrayList<>())
                    .add(pr.getSoldPercentage());
            }
        }

        // Calculate the average per product and per week
        Map<Integer, List<TopSellingProductResult>> topProductsByWeekNumber = new HashMap<>();

        for (Map.Entry<Integer, Map<ProductGroupingKey, List<Float>>> entry : soldPercentagesByWeekAndProduct.entrySet()) {
            int weekNum = entry.getKey();
            List<TopSellingProductResult> aggregated = new ArrayList<>();

            for (Map.Entry<ProductGroupingKey, List<Float>> productEntry : entry.getValue().entrySet()) {
                String productCode = productEntry.getKey().getCodeProduit();
                String product = productEntry.getKey().getProduit();
                List<Float> percentages = productEntry.getValue();
                float avg = (float) percentages.stream().mapToDouble(Float::doubleValue).average().orElse(0);
                aggregated.add(new TopSellingProductResult(productCode, product, avg, 0f, 0f)); // qty and stock not needed here
            }

            List<TopSellingProductResult> top5 = aggregated
                .stream()
                .sorted(Comparator.comparing(TopSellingProductResult::getSoldPercentage).reversed())
                .limit(5)
                .collect(Collectors.toList());

            topProductsByWeekNumber.put(weekNum, top5);
        }

        return topProductsByWeekNumber;
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
