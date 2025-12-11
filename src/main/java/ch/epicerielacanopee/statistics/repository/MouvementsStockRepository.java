package ch.epicerielacanopee.statistics.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ch.epicerielacanopee.statistics.domain.MouvementsStock;
import ch.epicerielacanopee.statistics.repository.projection.MouvementsStockDateRangeProjection;
import ch.epicerielacanopee.statistics.repository.projection.MouvementsStockProjection;

/**
 * Spring Data JPA repository for the MouvementsStock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MouvementsStockRepository
        extends JpaRepository<MouvementsStock, UUID>, JpaSpecificationExecutor<MouvementsStock> {
    public List<MouvementsStock> findByVenteAndDateBetween(String vente, Instant startDate, Instant endDate);

    public List<MouvementsStockProjection> findByCodeProduitStartingWithAndDateBetween(String codeProduitPrefix,
            Instant startDate, Instant endDate);

    @Query(value = "SELECT * FROM mouvements_stock m WHERE (:prefixes IS NULL OR m.code_produit LIKE ANY (ARRAY(SELECT CONCAT(p, '%') FROM unnest(:prefixes) AS p))) AND m.date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<MouvementsStockProjection> findByCodeProduitStartingWithAnyAndDateBetween(
            @Param("prefixes") List<String> prefixes, @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate);

    public Optional<MouvementsStock> findFirstByCodeProduitAndVenteAndEpicerioIdLessThanOrderByEpicerioIdDesc(
            String codeProduit,
            String vente,
            Integer epicerioId);

    Optional<MouvementsStock> findFirstByCodeProduitAndDateBeforeOrderByDateDesc(String codeProduit, Instant instant);

    @Query("SELECT MIN(date) AS minDate, MAX(date) AS maxDate FROM MouvementsStock")
    MouvementsStockDateRangeProjection findDateRange();

    @Query("SELECT MAX(date) FROM MouvementsStock")
    Instant findMaxDate();
}
