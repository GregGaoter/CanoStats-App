package ch.epicerielacanopee.statistics.service;

import ch.epicerielacanopee.statistics.domain.*; // for static metamodels
import ch.epicerielacanopee.statistics.domain.MouvementsStock;
import ch.epicerielacanopee.statistics.repository.MouvementsStockRepository;
import ch.epicerielacanopee.statistics.service.criteria.MouvementsStockCriteria;
import ch.epicerielacanopee.statistics.service.dto.MouvementsStockDTO;
import ch.epicerielacanopee.statistics.service.mapper.MouvementsStockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link MouvementsStock} entities in the database.
 * The main input is a {@link MouvementsStockCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link MouvementsStockDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MouvementsStockQueryService extends QueryService<MouvementsStock> {

    private static final Logger LOG = LoggerFactory.getLogger(MouvementsStockQueryService.class);

    private final MouvementsStockRepository mouvementsStockRepository;

    private final MouvementsStockMapper mouvementsStockMapper;

    public MouvementsStockQueryService(MouvementsStockRepository mouvementsStockRepository, MouvementsStockMapper mouvementsStockMapper) {
        this.mouvementsStockRepository = mouvementsStockRepository;
        this.mouvementsStockMapper = mouvementsStockMapper;
    }

    /**
     * Return a {@link Page} of {@link MouvementsStockDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MouvementsStockDTO> findByCriteria(MouvementsStockCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MouvementsStock> specification = createSpecification(criteria);
        return mouvementsStockRepository.findAll(specification, page).map(mouvementsStockMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MouvementsStockCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<MouvementsStock> specification = createSpecification(criteria);
        return mouvementsStockRepository.count(specification);
    }

    /**
     * Function to convert {@link MouvementsStockCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MouvementsStock> createSpecification(MouvementsStockCriteria criteria) {
        Specification<MouvementsStock> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), MouvementsStock_.id));
            }
            if (criteria.getEpicerioId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEpicerioId(), MouvementsStock_.epicerioId));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), MouvementsStock_.createdDate));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), MouvementsStock_.lastUpdatedDate));
            }
            if (criteria.getImportedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getImportedDate(), MouvementsStock_.importedDate));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), MouvementsStock_.date));
            }
            if (criteria.getUtilisateur() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUtilisateur(), MouvementsStock_.utilisateur));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), MouvementsStock_.type));
            }
            if (criteria.getEpicerioMouvement() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getEpicerioMouvement(), MouvementsStock_.epicerioMouvement)
                );
            }
            if (criteria.getMouvement() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMouvement(), MouvementsStock_.mouvement));
            }
            if (criteria.getSolde() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSolde(), MouvementsStock_.solde));
            }
            if (criteria.getVente() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVente(), MouvementsStock_.vente));
            }
            if (criteria.getCodeProduit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodeProduit(), MouvementsStock_.codeProduit));
            }
            if (criteria.getProduit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProduit(), MouvementsStock_.produit));
            }
            if (criteria.getResponsableProduit() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getResponsableProduit(), MouvementsStock_.responsableProduit)
                );
            }
            if (criteria.getFournisseurProduit() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getFournisseurProduit(), MouvementsStock_.fournisseurProduit)
                );
            }
            if (criteria.getCodeFournisseur() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getCodeFournisseur(), MouvementsStock_.codeFournisseur)
                );
            }
            if (criteria.getReduction() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReduction(), MouvementsStock_.reduction));
            }
            if (criteria.getPonderation() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPonderation(), MouvementsStock_.ponderation));
            }
            if (criteria.getVenteChf() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVenteChf(), MouvementsStock_.venteChf));
            }
            if (criteria.getValeurChf() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValeurChf(), MouvementsStock_.valeurChf));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MouvementsStock_.active));
            }
        }
        return specification;
    }
}
