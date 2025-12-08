package ch.epicerielacanopee.statistics.repository;

import ch.epicerielacanopee.statistics.domain.MouvementsStock;
import ch.epicerielacanopee.statistics.repository.projection.MouvementsStockDateRangeProjection;
import ch.epicerielacanopee.statistics.repository.projection.MouvementsStockProjection;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MouvementsStock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MouvementsStockRepository extends JpaRepository<MouvementsStock, UUID>, JpaSpecificationExecutor<MouvementsStock> {
    public List<MouvementsStock> findByVenteAndDateBetween(String vente, Instant startDate, Instant endDate);

    public List<MouvementsStockProjection> findByCodeProduitStartingWithAndDateBetween(String codeProduitPrefix, Instant startDate, Instant endDate);

    public Optional<MouvementsStock> findFirstByCodeProduitAndVenteAndEpicerioIdLessThanOrderByEpicerioIdDesc(
        String codeProduit,
        String vente,
        Integer epicerioId
    );

    Optional<MouvementsStock> findFirstByCodeProduitAndDateBeforeOrderByDateDesc(String codeProduit, Instant instant);

    @Query("SELECT MIN(date) AS minDate, MAX(date) AS maxDate FROM mouvements_stock")
    MouvementsStockDateRangeProjection findDateRange();
}
