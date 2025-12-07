package ch.epicerielacanopee.statistics.repository;

import ch.epicerielacanopee.statistics.domain.Produit;
import ch.epicerielacanopee.statistics.repository.projection.ProduitCodeProjection;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Produit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProduitRepository extends JpaRepository<Produit, UUID>, JpaSpecificationExecutor<Produit> {

  List<ProduitCodeProjection> findDistinctCodeByCodeIsNotNull();
}
