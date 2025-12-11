package ch.epicerielacanopee.statistics.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ch.epicerielacanopee.statistics.domain.MouvementsStock;
import ch.epicerielacanopee.statistics.repository.projection.MouvementsStockDateRangeProjection;
import ch.epicerielacanopee.statistics.repository.projection.MouvementsStockProjection;
import ch.epicerielacanopee.statistics.repository.specification.MouvementsStockSpecifications;

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

        default List<MouvementsStockProjection> findByCodeProduitStartingWithAnyAndDateBetween(List<String> prefixes,
                        Instant startDate, Instant endDate) {
                return findBy(MouvementsStockSpecifications.codeProduitStartsWithAnyAndDateBetween(prefixes, startDate,
                                endDate), q -> q.as(MouvementsStockProjection.class).all());
        }

        public Optional<MouvementsStock> findFirstByCodeProduitAndVenteAndEpicerioIdLessThanOrderByEpicerioIdDesc(
                        String codeProduit, String vente, Integer epicerioId);

        Optional<MouvementsStock> findFirstByCodeProduitAndDateBeforeOrderByDateDesc(String codeProduit,
                        Instant instant);

        @Query("SELECT MIN(date) AS minDate, MAX(date) AS maxDate FROM MouvementsStock")
        MouvementsStockDateRangeProjection findDateRange();

        @Query("SELECT MAX(date) FROM MouvementsStock")
        Instant findMaxDate();
}
