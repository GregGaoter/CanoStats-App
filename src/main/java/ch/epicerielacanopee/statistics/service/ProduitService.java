package ch.epicerielacanopee.statistics.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.epicerielacanopee.statistics.domain.Produit;
import ch.epicerielacanopee.statistics.repository.ProduitRepository;
import ch.epicerielacanopee.statistics.repository.projection.ProduitCodeProjection;
import ch.epicerielacanopee.statistics.service.dto.EpicerioProduitDTO;
import ch.epicerielacanopee.statistics.service.dto.ProduitDTO;
import ch.epicerielacanopee.statistics.service.mapper.ProduitMapper;

/**
 * Service Implementation for managing
 * {@link ch.epicerielacanopee.statistics.domain.Produit}.
 */
@Service
@Transactional
public class ProduitService {

    private static final Logger LOG = LoggerFactory.getLogger(ProduitService.class);

    private final ObjectMapper objectMapper;

    private final ProduitRepository produitRepository;

    private final ProduitMapper produitMapper;

    public ProduitService(ProduitRepository produitRepository, ProduitMapper produitMapper, ObjectMapper objectMapper) {
        this.produitRepository = produitRepository;
        this.produitMapper = produitMapper;
        this.objectMapper = objectMapper;
    }

    /**
     * Save a produit.
     *
     * @param produitDTO the entity to save.
     * @return the persisted entity.
     */
    public ProduitDTO save(ProduitDTO produitDTO) {
        LOG.debug("Request to save Produit : {}", produitDTO);
        Produit produit = produitMapper.toEntity(produitDTO);
        produit = produitRepository.save(produit);
        return produitMapper.toDto(produit);
    }

    /**
     * Update a produit.
     *
     * @param produitDTO the entity to save.
     * @return the persisted entity.
     */
    public ProduitDTO update(ProduitDTO produitDTO) {
        LOG.debug("Request to update Produit : {}", produitDTO);
        Produit produit = produitMapper.toEntity(produitDTO);
        produit = produitRepository.save(produit);
        return produitMapper.toDto(produit);
    }

    /**
     * Partially update a produit.
     *
     * @param produitDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProduitDTO> partialUpdate(ProduitDTO produitDTO) {
        LOG.debug("Request to partially update Produit : {}", produitDTO);

        return produitRepository
                .findById(produitDTO.getId())
                .map(existingProduit -> {
                    produitMapper.partialUpdate(existingProduit, produitDTO);

                    return existingProduit;
                })
                .map(produitRepository::save)
                .map(produitMapper::toDto);
    }

    /**
     * Get all the produits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProduitDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Produits");
        return produitRepository.findAll(pageable).map(produitMapper::toDto);
    }

    /**
     * Get one produit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProduitDTO> findOne(UUID id) {
        LOG.debug("Request to get Produit : {}", id);
        return produitRepository.findById(id).map(produitMapper::toDto);
    }

    /**
     * Delete the produit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        LOG.debug("Request to delete Produit : {}", id);
        produitRepository.deleteById(id);
    }

    public Produit create(EpicerioProduitDTO epicerioProduit) {
        Instant now = Instant.now();
        Produit produit = new Produit();
        produit.setEpicerioId(epicerioProduit.getId());
        produit.setCreatedDate(now);
        produit.setLastUpdatedDate(now);
        produit.setImportedDate(now);
        produit.setNom(epicerioProduit.getNom());
        produit.setCode(epicerioProduit.getCode());
        produit.setDisponible(epicerioProduit.getDisponible());
        produit.setPrixFournisseur(epicerioProduit.getPrixFournisseur());
        produit.setHtTtc(epicerioProduit.getHtTtc());
        produit.setTauxTva(epicerioProduit.getTauxTva());
        produit.setMargeProfit(epicerioProduit.getMargeProfit());
        produit.setPrixVente(epicerioProduit.getPrixVente());
        produit.setVendu(epicerioProduit.getVendu());
        produit.setQuantiteParPiece(epicerioProduit.getQuantiteParPiece());
        produit.setUnite(epicerioProduit.getUnite());
        produit.setPrixParUnite(epicerioProduit.getPrixParUnite());
        produit.setDescription(epicerioProduit.getDescription());
        produit.setRemarquesInternes(epicerioProduit.getRemarquesInternes());
        produit.setFournisseur(epicerioProduit.getFournisseur());
        produit.setRefFournisseur(epicerioProduit.getRefFournisseur());
        produit.setStock(epicerioProduit.getStock());
        produit.setCommandesClients(epicerioProduit.getCommandesClients());
        produit.setDerniereVerificationDate(epicerioProduit.getDerniereVerificationDate());
        produit.setDerniereLivraisonDate(epicerioProduit.getDerniereLivraisonDate());
        produit.setAchatFournisseur(epicerioProduit.getAchatFournisseur());
        produit.setDernierAchatDate(epicerioProduit.getDernierAchatDate());
        produit.setDernierAchatQuantite(epicerioProduit.getDernierAchatQuantite());
        produit.setStatsLivraison(epicerioProduit.getStatsLivraison());
        produit.setStatsPerte(epicerioProduit.getStatsPerte());
        produit.setStatsVente(epicerioProduit.getStatsVente());
        produit.setStatsVenteSpeciale(epicerioProduit.getStatsVenteSpeciale());
        produit.setTags(epicerioProduit.getTags());
        return produit;
    }

    public String importFile(MultipartFile file) throws Exception {
        String content = new String(file.getBytes());
        List<EpicerioProduitDTO> epicerioProduitDTOs = objectMapper.readValue(
                content,
                new TypeReference<List<EpicerioProduitDTO>>() {
                });
        epicerioProduitDTOs.sort(Comparator.comparing(epicerioProduit -> epicerioProduit.getId()));
        int produitsTotal = epicerioProduitDTOs.size();
        List<Produit> produits = new ArrayList<>(produitsTotal);
        for (EpicerioProduitDTO epicerioProduit : epicerioProduitDTOs) {
            Produit produit = create(epicerioProduit);
            produits.add(produit);
        }
        produitRepository.deleteAllInBatch();
        produitRepository.saveAll(produits);
        return String.format(" %d Produits successfuly imported!", produitsTotal);
    }

    public List<String> getProductTypesByCode() {
        List<ProduitCodeProjection> produits = produitRepository.findDistinctCodeByCodeIsNotNull();
        return produits.stream().map(ProduitCodeProjection::getCode).map(String::trim)
                .map(s -> s.replaceAll("\\d+", "")).filter(s -> s.matches("[A-Za-z]+")).distinct().sorted().toList();
    }
}
