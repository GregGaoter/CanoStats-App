package ch.epicerielacanopee.statistics.web.rest;

import ch.epicerielacanopee.statistics.repository.MouvementsStockRepository;
import ch.epicerielacanopee.statistics.service.MouvementsStockService;
import ch.epicerielacanopee.statistics.service.dto.MouvementsStockDTO;
import ch.epicerielacanopee.statistics.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ch.epicerielacanopee.statistics.domain.MouvementsStock}.
 */
@RestController
@RequestMapping("/api/mouvements-stocks")
public class MouvementsStockResource {

    private static final Logger LOG = LoggerFactory.getLogger(MouvementsStockResource.class);

    private static final String ENTITY_NAME = "mouvementsStock";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MouvementsStockService mouvementsStockService;

    private final MouvementsStockRepository mouvementsStockRepository;

    public MouvementsStockResource(MouvementsStockService mouvementsStockService, MouvementsStockRepository mouvementsStockRepository) {
        this.mouvementsStockService = mouvementsStockService;
        this.mouvementsStockRepository = mouvementsStockRepository;
    }

    /**
     * {@code POST  /mouvements-stocks} : Create a new mouvementsStock.
     *
     * @param mouvementsStockDTO the mouvementsStockDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mouvementsStockDTO, or with status {@code 400 (Bad Request)} if the mouvementsStock has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MouvementsStockDTO> createMouvementsStock(@Valid @RequestBody MouvementsStockDTO mouvementsStockDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save MouvementsStock : {}", mouvementsStockDTO);
        if (mouvementsStockDTO.getId() != null) {
            throw new BadRequestAlertException("A new mouvementsStock cannot already have an ID", ENTITY_NAME, "idexists");
        }
        mouvementsStockDTO = mouvementsStockService.save(mouvementsStockDTO);
        return ResponseEntity.created(new URI("/api/mouvements-stocks/" + mouvementsStockDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, mouvementsStockDTO.getId().toString()))
            .body(mouvementsStockDTO);
    }

    /**
     * {@code PUT  /mouvements-stocks/:id} : Updates an existing mouvementsStock.
     *
     * @param id the id of the mouvementsStockDTO to save.
     * @param mouvementsStockDTO the mouvementsStockDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mouvementsStockDTO,
     * or with status {@code 400 (Bad Request)} if the mouvementsStockDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mouvementsStockDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MouvementsStockDTO> updateMouvementsStock(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody MouvementsStockDTO mouvementsStockDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update MouvementsStock : {}, {}", id, mouvementsStockDTO);
        if (mouvementsStockDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mouvementsStockDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mouvementsStockRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        mouvementsStockDTO = mouvementsStockService.update(mouvementsStockDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mouvementsStockDTO.getId().toString()))
            .body(mouvementsStockDTO);
    }

    /**
     * {@code PATCH  /mouvements-stocks/:id} : Partial updates given fields of an existing mouvementsStock, field will ignore if it is null
     *
     * @param id the id of the mouvementsStockDTO to save.
     * @param mouvementsStockDTO the mouvementsStockDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mouvementsStockDTO,
     * or with status {@code 400 (Bad Request)} if the mouvementsStockDTO is not valid,
     * or with status {@code 404 (Not Found)} if the mouvementsStockDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the mouvementsStockDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MouvementsStockDTO> partialUpdateMouvementsStock(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody MouvementsStockDTO mouvementsStockDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update MouvementsStock partially : {}, {}", id, mouvementsStockDTO);
        if (mouvementsStockDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mouvementsStockDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mouvementsStockRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MouvementsStockDTO> result = mouvementsStockService.partialUpdate(mouvementsStockDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mouvementsStockDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /mouvements-stocks} : get all the mouvementsStocks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mouvementsStocks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MouvementsStockDTO>> getAllMouvementsStocks(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of MouvementsStocks");
        Page<MouvementsStockDTO> page = mouvementsStockService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mouvements-stocks/:id} : get the "id" mouvementsStock.
     *
     * @param id the id of the mouvementsStockDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mouvementsStockDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MouvementsStockDTO> getMouvementsStock(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get MouvementsStock : {}", id);
        Optional<MouvementsStockDTO> mouvementsStockDTO = mouvementsStockService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mouvementsStockDTO);
    }

    /**
     * {@code DELETE  /mouvements-stocks/:id} : delete the "id" mouvementsStock.
     *
     * @param id the id of the mouvementsStockDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMouvementsStock(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete MouvementsStock : {}", id);
        mouvementsStockService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * Handles the import of a file containing stock movements.
     *
     * @param mouvementsStocksFile the file to be imported
     * @return a ResponseEntity with a success message if the import is successful,
     *         or an error message if there is an issue processing the file.
     */
    @PostMapping("/import")
    public ResponseEntity<String> importFile(@RequestParam MultipartFile mouvementsStocksFile) {
        try {
            mouvementsStockService.importFile(mouvementsStocksFile);
            return ResponseEntity.ok("MouvementsStocks imported successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file!");
        }
    }
}
