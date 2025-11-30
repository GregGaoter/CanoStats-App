package ch.epicerielacanopee.statistics.service.mapper;

import ch.epicerielacanopee.statistics.domain.Produit;
import ch.epicerielacanopee.statistics.service.dto.ProduitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Produit} and its DTO {@link ProduitDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProduitMapper extends EntityMapper<ProduitDTO, Produit> {}
