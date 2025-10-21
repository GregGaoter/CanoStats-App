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
     * Computes the top-5 selling products for each ISO week number (aggregated across years)
     * based on weekly sales rates derived from stock movements.
     *
     * <p>Processing overview:
     * <ul>
     *   <li>Movements are first grouped by product (combination of product code and product name)
     *       and sorted by movement date.</li>
     *   <li>Weeks are determined using ISO week fields (WeekFields.ISO) and the
     *       "Europe/Zurich" time zone. Each distinct YearWeek present in the input movements
     *       is analyzed independently.</li>
     *   <li>For each product and week:
     *     <ul>
     *       <li>The method attempts to determine the initial stock at the start of the week:
     *         <ul>
     *           <li>Prefer the last known balance strictly before the week's start by querying
     *               the repository: {@code mouvementsStockRepository.findFirstByCodeProduitAndDateBeforeOrderByDateDesc(...)}.</li>
     *           <li>If no prior balance exists but there are movements during the week, the
     *               first observed balance in the week is used as a fallback initial stock.</li>
     *         </ul>
     *       </li>
     *       <li>All movements whose timestamp is within the week's inclusive date range are
     *           considered "week movements".</li>
     *       <li>Deliveries for the week are summed from movements where {@code type.equals("Livraison")}.
     *           Null movement amounts are treated as 0.</li>
     *       <li>The final stock for the week is taken as the last observed balance during the week,
     *           or the initial stock if there were no movements in the week.</li>
     *       <li>Negative balances are clamped to 0 for both initial and final stocks.</li>
     *       <li>Available stock is computed as {@code initialStock + deliveries}. If available stock
     *           is less than or equal to zero, the product/week pair is ignored (to avoid division by zero
     *           and irrelevant cases).</li>
     *       <li>Sold quantity is {@code availableStock - finalStock}, clamped to a minimum of 0
     *           (to protect against corrections or inventory increases), and sold percentage is
     *           {@code (soldQuantity / availableStock) * 100}.</li>
     *       <li>Product/week pairs without sufficient information (no initial stock and no movements)
     *           or where computed values are incomplete are skipped.</li>
     *     </ul>
     *   </li>
     *   <li>After computing sold percentages per product for each YearWeek, results are aggregated
     *       by ISO week number across years. For each product/weekNumber the average sold percentage
     *       across all years (and YearWeeks) present in the input is computed.</li>
     *   <li>For each week number (1–53), the products are sorted by their average sold percentage
     *       in descending order and the top 5 products are returned.</li>
     * </ul>
     *
     * Important notes and assumptions:
     * <ul>
     *   <li>Input {@code movements} are expected to have non-null {@code codeProduit}, {@code produit}
     *       and a date value that can be converted to the "Europe/Zurich" zone via {@code atZone(zone)}.</li>
     *   <li>The method depends on a repository call to obtain the last known balance before a week.
     *       That call is executed per product/week and may be expensive for large datasets.</li>
     *   <li>Returned percentages are in the range [0, 100]. Quantities and stocks are represented as floats.</li>
     *   <li>The map key is the ISO week number (1–53). The associated list is ordered by decreasing
     *       average sold percentage and contains up to 5 TopSellingProductResult entries. In the
     *       returned TopSellingProductResult objects, quantity and stock fields are not meaningful
     *       for the aggregated results (they are set to 0f in aggregation).</li>
     * </ul>
     *
     * @param movements list of stock movement DTOs (MouvementsStockDTO) used to compute weekly sales rates;
     *                  only entries with non-null product code and product name are considered
     * @return a map indexed by ISO week number (Integer). Each value is a list of up to 5
     *         TopSellingProductResult objects representing the products with the highest average
     *         sold percentage for that week number, sorted in descending order of sold percentage.
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

        Map<YearWeek, List<TopSellingProductResult>> weekToSellingProductsResult = new TreeMap<>();

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
                Optional<MouvementsStock> lastBeforeWeek = mouvementsStockRepository.findFirstByCodeProduitAndDateBeforeOrderByDateDesc(
                    productCode,
                    weekStart
                );
                Float initialStock = lastBeforeWeek.isPresent() ? lastBeforeWeek.get().getSolde() : null;

                // Movements during the week
                List<MouvementsStockDTO> weekMovements = productMovements
                    .stream()
                    .filter(m -> m.getDate().isAfter(weekStart) && m.getDate().isBefore(weekEnd))
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

                if (initialStock < 0f) initialStock = 0f;
                if (finalStock < 0f) finalStock = 0f;

                float availableStock = initialStock + deliveries;
                if (availableStock <= 0f) continue; // avoid division by zero or irrelevant cases

                float soldQuantity = availableStock - finalStock;
                if (soldQuantity < 0f) soldQuantity = 0f; // protection against corrections/inventories that increase the stock

                float soldPercentage = (soldQuantity / availableStock) * 100f;

                results.add(new TopSellingProductResult(productCode, product, soldPercentage, soldQuantity, availableStock));
            }

            if (!results.isEmpty()) {
                weekToSellingProductsResult.put(yw, results);
            }
        }

        // For each week number, identify the products that had the highest average sales rates across all available years.

        // For each week, group the results by product
        Map<Integer, Map<ProductGroupingKey, List<Float>>> soldPercentagesByWeekAndProduct = new HashMap<>();

        for (Map.Entry<YearWeek, List<TopSellingProductResult>> entry : weekToSellingProductsResult.entrySet()) {
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
}
