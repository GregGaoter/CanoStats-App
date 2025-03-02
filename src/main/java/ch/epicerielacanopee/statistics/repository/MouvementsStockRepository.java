package ch.epicerielacanopee.statistics.repository;

import ch.epicerielacanopee.statistics.domain.MouvementsStock;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MouvementsStock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MouvementsStockRepository extends JpaRepository<MouvementsStock, UUID>, JpaSpecificationExecutor<MouvementsStock> {
    public List<MouvementsStock> findByVenteAndDateBetween(String vente, Instant startDate, Instant endDate);
}
