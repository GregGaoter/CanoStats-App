package ch.epicerielacanopee.statistics.service;

import ch.epicerielacanopee.statistics.domain.*; // for static metamodels
import ch.epicerielacanopee.statistics.domain.Produit;
import ch.epicerielacanopee.statistics.repository.ProduitRepository;
import ch.epicerielacanopee.statistics.service.criteria.ProduitCriteria;
import ch.epicerielacanopee.statistics.service.dto.ProduitDTO;
import ch.epicerielacanopee.statistics.service.mapper.ProduitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Produit} entities in the database.
 * The main input is a {@link ProduitCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link ProduitDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProduitQueryService extends QueryService<Produit> {

    private static final Logger LOG = LoggerFactory.getLogger(ProduitQueryService.class);

    private final ProduitRepository produitRepository;

    private final ProduitMapper produitMapper;

    public ProduitQueryService(ProduitRepository produitRepository, ProduitMapper produitMapper) {
        this.produitRepository = produitRepository;
        this.produitMapper = produitMapper;
    }

    /**
     * Return a {@link Page} of {@link ProduitDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProduitDTO> findByCriteria(ProduitCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Produit> specification = createSpecification(criteria);
        return produitRepository.findAll(specification, page).map(produitMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProduitCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Produit> specification = createSpecification(criteria);
        return produitRepository.count(specification);
    }

    /**
     * Function to convert {@link ProduitCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Produit> createSpecification(ProduitCriteria criteria) {
        Specification<Produit> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Produit_.id));
            }
            if (criteria.getEpicerioId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEpicerioId(), Produit_.epicerioId));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Produit_.createdDate));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), Produit_.lastUpdatedDate));
            }
            if (criteria.getImportedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getImportedDate(), Produit_.importedDate));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Produit_.nom));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Produit_.code));
            }
            if (criteria.getDisponible() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDisponible(), Produit_.disponible));
            }
            if (criteria.getPrixFournisseur() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrixFournisseur(), Produit_.prixFournisseur));
            }
            if (criteria.getHtTtc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHtTtc(), Produit_.htTtc));
            }
            if (criteria.getTauxTva() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTauxTva(), Produit_.tauxTva));
            }
            if (criteria.getMargeProfit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMargeProfit(), Produit_.margeProfit));
            }
            if (criteria.getPrixVente() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrixVente(), Produit_.prixVente));
            }
            if (criteria.getVendu() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVendu(), Produit_.vendu));
            }
            if (criteria.getQuantiteParPiece() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantiteParPiece(), Produit_.quantiteParPiece));
            }
            if (criteria.getUnite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUnite(), Produit_.unite));
            }
            if (criteria.getPrixParUnite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrixParUnite(), Produit_.prixParUnite));
            }
            if (criteria.getFournisseur() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFournisseur(), Produit_.fournisseur));
            }
            if (criteria.getRefFournisseur() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRefFournisseur(), Produit_.refFournisseur));
            }
            if (criteria.getStock() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStock(), Produit_.stock));
            }
            if (criteria.getCommandesClients() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCommandesClients(), Produit_.commandesClients));
            }
            if (criteria.getDerniereVerificationDate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getDerniereVerificationDate(), Produit_.derniereVerificationDate)
                );
            }
            if (criteria.getDerniereLivraisonDate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getDerniereLivraisonDate(), Produit_.derniereLivraisonDate)
                );
            }
            if (criteria.getAchatFournisseur() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAchatFournisseur(), Produit_.achatFournisseur));
            }
            if (criteria.getDernierAchatDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDernierAchatDate(), Produit_.dernierAchatDate));
            }
            if (criteria.getDernierAchatQuantite() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getDernierAchatQuantite(), Produit_.dernierAchatQuantite)
                );
            }
            if (criteria.getStatsLivraison() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatsLivraison(), Produit_.statsLivraison));
            }
            if (criteria.getStatsPerte() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatsPerte(), Produit_.statsPerte));
            }
            if (criteria.getStatsVente() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatsVente(), Produit_.statsVente));
            }
            if (criteria.getStatsVenteSpeciale() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatsVenteSpeciale(), Produit_.statsVenteSpeciale));
            }
        }
        return specification;
    }
}
