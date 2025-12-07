package ch.epicerielacanopee.statistics.repository;

import ch.epicerielacanopee.statistics.domain.Produit;

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

  List<String> findDistinctCodeByCodeIsNotNull();
}
