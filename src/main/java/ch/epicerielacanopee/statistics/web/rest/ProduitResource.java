package ch.epicerielacanopee.statistics.web.rest;

import ch.epicerielacanopee.statistics.repository.ProduitRepository;
import ch.epicerielacanopee.statistics.service.ProduitService;
import ch.epicerielacanopee.statistics.service.dto.ProduitDTO;
import ch.epicerielacanopee.statistics.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
 * REST controller for managing {@link ch.epicerielacanopee.statistics.domain.Produit}.
 */
@RestController
@RequestMapping("/api/produits")
public class ProduitResource {

    private static final Logger LOG = LoggerFactory.getLogger(ProduitResource.class);

    private static final String ENTITY_NAME = "produit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProduitService produitService;

    private final ProduitRepository produitRepository;

    public ProduitResource(ProduitService produitService, ProduitRepository produitRepository) {
        this.produitService = produitService;
        this.produitRepository = produitRepository;
    }

    /**
     * {@code POST  /produits} : Create a new produit.
     *
     * @param produitDTO the produitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new produitDTO, or with status {@code 400 (Bad Request)} if the produit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ProduitDTO> createProduit(@Valid @RequestBody ProduitDTO produitDTO) throws URISyntaxException {
        LOG.debug("REST request to save Produit : {}", produitDTO);
        if (produitDTO.getId() != null) {
            throw new BadRequestAlertException("A new produit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        produitDTO = produitService.save(produitDTO);
        return ResponseEntity.created(new URI("/api/produits/" + produitDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, produitDTO.getId().toString()))
            .body(produitDTO);
    }

    /**
     * {@code PUT  /produits/:id} : Updates an existing produit.
     *
     * @param id the id of the produitDTO to save.
     * @param produitDTO the produitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated produitDTO,
     * or with status {@code 400 (Bad Request)} if the produitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the produitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProduitDTO> updateProduit(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody ProduitDTO produitDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Produit : {}, {}", id, produitDTO);
        if (produitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, produitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!produitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        produitDTO = produitService.update(produitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, produitDTO.getId().toString()))
            .body(produitDTO);
    }

    /**
     * {@code PATCH  /produits/:id} : Partial updates given fields of an existing produit, field will ignore if it is null
     *
     * @param id the id of the produitDTO to save.
     * @param produitDTO the produitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated produitDTO,
     * or with status {@code 400 (Bad Request)} if the produitDTO is not valid,
     * or with status {@code 404 (Not Found)} if the produitDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the produitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProduitDTO> partialUpdateProduit(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody ProduitDTO produitDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Produit partially : {}, {}", id, produitDTO);
        if (produitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, produitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!produitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProduitDTO> result = produitService.partialUpdate(produitDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, produitDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /produits} : get all the produits.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of produits in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ProduitDTO>> getAllProduits(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Produits");
        Page<ProduitDTO> page = produitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /produits/:id} : get the "id" produit.
     *
     * @param id the id of the produitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the produitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProduitDTO> getProduit(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get Produit : {}", id);
        Optional<ProduitDTO> produitDTO = produitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(produitDTO);
    }

    /**
     * {@code DELETE  /produits/:id} : delete the "id" produit.
     *
     * @param id the id of the produitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete Produit : {}", id);
        produitService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * Handles the import of a file containing products.
     *
     * @param productsFile the file to be imported
     * @return a ResponseEntity with a success message if the import is successful,
     *         or an error message if there is an issue processing the file.
     */
    @PostMapping("/import")
    public ResponseEntity<String> importFile(@RequestParam MultipartFile productsFile) {
        try {
            String message = produitService.importFile(productsFile);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
