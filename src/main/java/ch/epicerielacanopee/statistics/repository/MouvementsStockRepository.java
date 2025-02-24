package ch.epicerielacanopee.statistics.repository;

import ch.epicerielacanopee.statistics.domain.MouvementsStock;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MouvementsStock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MouvementsStockRepository extends JpaRepository<MouvementsStock, UUID>, JpaSpecificationExecutor<MouvementsStock> {}
