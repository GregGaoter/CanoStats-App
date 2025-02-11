package ch.epicerielacanopee.statistics.service;

import ch.epicerielacanopee.statistics.domain.MouvementsStock;
import ch.epicerielacanopee.statistics.repository.MouvementsStockRepository;
import ch.epicerielacanopee.statistics.service.dto.EpicerioMouvementsStockDTO;
import ch.epicerielacanopee.statistics.service.dto.MouvementsStockDTO;
import ch.epicerielacanopee.statistics.service.mapper.MouvementsStockMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
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

/**
 * Service Implementation for managing {@link ch.epicerielacanopee.statistics.domain.MouvementsStock}.
 */
@Service
@Transactional
public class MouvementsStockService {

    private static final Logger LOG = LoggerFactory.getLogger(MouvementsStockService.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final MouvementsStockRepository mouvementsStockRepository;

    private final MouvementsStockMapper mouvementsStockMapper;

    public MouvementsStockService(MouvementsStockRepository mouvementsStockRepository, MouvementsStockMapper mouvementsStockMapper) {
        this.mouvementsStockRepository = mouvementsStockRepository;
        this.mouvementsStockMapper = mouvementsStockMapper;
        objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Save a mouvementsStock.
     *
     * @param mouvementsStockDTO the entity to save.
     * @return the persisted entity.
     */
    public MouvementsStockDTO save(MouvementsStockDTO mouvementsStockDTO) {
        LOG.debug("Request to save MouvementsStock : {}", mouvementsStockDTO);
        MouvementsStock mouvementsStock = mouvementsStockMapper.toEntity(mouvementsStockDTO);
        mouvementsStock = mouvementsStockRepository.save(mouvementsStock);
        return mouvementsStockMapper.toDto(mouvementsStock);
    }

    /**
     * Update a mouvementsStock.
     *
     * @param mouvementsStockDTO the entity to save.
     * @return the persisted entity.
     */
    public MouvementsStockDTO update(MouvementsStockDTO mouvementsStockDTO) {
        LOG.debug("Request to update MouvementsStock : {}", mouvementsStockDTO);
        MouvementsStock mouvementsStock = mouvementsStockMapper.toEntity(mouvementsStockDTO);
        mouvementsStock = mouvementsStockRepository.save(mouvementsStock);
        return mouvementsStockMapper.toDto(mouvementsStock);
    }

    /**
     * Partially update a mouvementsStock.
     *
     * @param mouvementsStockDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MouvementsStockDTO> partialUpdate(MouvementsStockDTO mouvementsStockDTO) {
        LOG.debug("Request to partially update MouvementsStock : {}", mouvementsStockDTO);

        return mouvementsStockRepository
            .findById(mouvementsStockDTO.getId())
            .map(existingMouvementsStock -> {
                mouvementsStockMapper.partialUpdate(existingMouvementsStock, mouvementsStockDTO);

                return existingMouvementsStock;
            })
            .map(mouvementsStockRepository::save)
            .map(mouvementsStockMapper::toDto);
    }

    /**
     * Get all the mouvementsStocks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MouvementsStockDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all MouvementsStocks");
        return mouvementsStockRepository.findAll(pageable).map(mouvementsStockMapper::toDto);
    }

    /**
     * Get one mouvementsStock by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MouvementsStockDTO> findOne(UUID id) {
        LOG.debug("Request to get MouvementsStock : {}", id);
        return mouvementsStockRepository.findById(id).map(mouvementsStockMapper::toDto);
    }

    /**
     * Delete the mouvementsStock by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        LOG.debug("Request to delete MouvementsStock : {}", id);
        mouvementsStockRepository.deleteById(id);
    }

    /**
     * Imports a file containing a list of {@link EpicerioMouvementsStockDTO} objects.
     * The method reads the content of the file, converts it to a list of {@link EpicerioMouvementsStockDTO},
     * and then processes each item to create and save {@link MouvementsStock} entities.
     *
     * @param file the {@link MultipartFile} to be imported
     * @throws IOException if an I/O error occurs while reading the file
     */
    public void importFile(MultipartFile file) throws IOException {
        String content = new String(file.getBytes());
        List<EpicerioMouvementsStockDTO> epicerioMouvementsStocks = objectMapper.readValue(
            content,
            new TypeReference<List<EpicerioMouvementsStockDTO>>() {}
        );
        int mouvementsStocksTotal = epicerioMouvementsStocks.size();
        List<MouvementsStock> mouvementsStocks = new ArrayList<>(mouvementsStocksTotal);
        int i = 1;
        Instant now = Instant.now();
        for (EpicerioMouvementsStockDTO epicerioMouvementsStock : epicerioMouvementsStocks) {
            MouvementsStock mouvementsStock = create(epicerioMouvementsStock);
            mouvementsStock.setImportedDate(now);
            mouvementsStocks.add(mouvementsStock);
            LOG.info("{} / {}", i++, mouvementsStocksTotal);
        }
        mouvementsStockRepository.saveAll(mouvementsStocks);
    }

    /**
     * Creates a new MouvementsStock object from the given EpicerioMouvementsStockDTO.
     *
     * @param epicerioMouvementsStocks the DTO containing the data for the new MouvementsStock
     * @return the newly created MouvementsStock object
     */
    public MouvementsStock create(EpicerioMouvementsStockDTO epicerioMouvementsStocks) {
        Instant now = Instant.now();
        MouvementsStock mouvementsStock = new MouvementsStock();
        mouvementsStock.setEpicerioId(epicerioMouvementsStocks.getId());
        mouvementsStock.setCreatedDate(now);
        mouvementsStock.setLastUpdatedDate(now);
        mouvementsStock.setImportedDate(now);
        mouvementsStock.setDate(epicerioMouvementsStocks.getDate());
        mouvementsStock.setUtilisateur(epicerioMouvementsStocks.getUtilisateur());
        mouvementsStock.setType(epicerioMouvementsStocks.getType());
        mouvementsStock.setEpicerioMouvement(epicerioMouvementsStocks.getMouvement());
        mouvementsStock.setMouvement(epicerioMouvementsStocks.getMouvement());
        mouvementsStock.setSolde(epicerioMouvementsStocks.getSolde());
        mouvementsStock.setVente(epicerioMouvementsStocks.getVente());
        mouvementsStock.setCodeProduit(epicerioMouvementsStocks.getCodeProduit());
        mouvementsStock.setProduit(epicerioMouvementsStocks.getProduit());
        mouvementsStock.setResponsableProduit(epicerioMouvementsStocks.getResponsableProduit());
        mouvementsStock.setFournisseurProduit(epicerioMouvementsStocks.getFournisseurProduit());
        mouvementsStock.setCodeFournisseur(epicerioMouvementsStocks.getCodeFournisseur());
        mouvementsStock.setReduction(epicerioMouvementsStocks.getReduction());
        mouvementsStock.setPonderation(epicerioMouvementsStocks.getPonderation());
        mouvementsStock.setVenteChf(epicerioMouvementsStocks.getVenteChf());
        mouvementsStock.setValeurChf(epicerioMouvementsStocks.getValeurChf());
        mouvementsStock.setRemarques(epicerioMouvementsStocks.getRemarques());
        mouvementsStock.setActive(true);
        return mouvementsStock;
    }
}
