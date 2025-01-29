package ch.epicerielacanopee.statistics.web.rest;

import static ch.epicerielacanopee.statistics.domain.MouvementsStockAsserts.*;
import static ch.epicerielacanopee.statistics.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.epicerielacanopee.statistics.IntegrationTest;
import ch.epicerielacanopee.statistics.domain.MouvementsStock;
import ch.epicerielacanopee.statistics.repository.MouvementsStockRepository;
import ch.epicerielacanopee.statistics.service.dto.MouvementsStockDTO;
import ch.epicerielacanopee.statistics.service.mapper.MouvementsStockMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MouvementsStockResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MouvementsStockResourceIT {

    private static final Integer DEFAULT_EPICERIO_ID = 1;
    private static final Integer UPDATED_EPICERIO_ID = 2;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_IMPORTED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_IMPORTED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UTILISATEUR = "AAAAAAAAAA";
    private static final String UPDATED_UTILISATEUR = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Float DEFAULT_EPICERIO_MOUVEMENT = 1F;
    private static final Float UPDATED_EPICERIO_MOUVEMENT = 2F;

    private static final Float DEFAULT_MOUVEMENT = 1F;
    private static final Float UPDATED_MOUVEMENT = 2F;

    private static final Float DEFAULT_SOLDE = 1F;
    private static final Float UPDATED_SOLDE = 2F;

    private static final String DEFAULT_VENTE = "AAAAAAAAAA";
    private static final String UPDATED_VENTE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_PRODUIT = "AAAAAAAAAA";
    private static final String UPDATED_CODE_PRODUIT = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUIT = "AAAAAAAAAA";
    private static final String UPDATED_PRODUIT = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSABLE_PRODUIT = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSABLE_PRODUIT = "BBBBBBBBBB";

    private static final String DEFAULT_FOURNISSEUR_PRODUIT = "AAAAAAAAAA";
    private static final String UPDATED_FOURNISSEUR_PRODUIT = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_FOURNISSEUR = "AAAAAAAAAA";
    private static final String UPDATED_CODE_FOURNISSEUR = "BBBBBBBBBB";

    private static final Float DEFAULT_REDUCTION = 1F;
    private static final Float UPDATED_REDUCTION = 2F;

    private static final Float DEFAULT_PONDERATION = 1F;
    private static final Float UPDATED_PONDERATION = 2F;

    private static final Float DEFAULT_VENTE_CHF = 1F;
    private static final Float UPDATED_VENTE_CHF = 2F;

    private static final Float DEFAULT_VALEUR_CHF = 1F;
    private static final Float UPDATED_VALEUR_CHF = 2F;

    private static final String DEFAULT_REMARQUES = "AAAAAAAAAA";
    private static final String UPDATED_REMARQUES = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/mouvements-stocks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MouvementsStockRepository mouvementsStockRepository;

    @Autowired
    private MouvementsStockMapper mouvementsStockMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMouvementsStockMockMvc;

    private MouvementsStock mouvementsStock;

    private MouvementsStock insertedMouvementsStock;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MouvementsStock createEntity() {
        return new MouvementsStock()
            .epicerioId(DEFAULT_EPICERIO_ID)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .importedDate(DEFAULT_IMPORTED_DATE)
            .date(DEFAULT_DATE)
            .utilisateur(DEFAULT_UTILISATEUR)
            .type(DEFAULT_TYPE)
            .epicerioMouvement(DEFAULT_EPICERIO_MOUVEMENT)
            .mouvement(DEFAULT_MOUVEMENT)
            .solde(DEFAULT_SOLDE)
            .vente(DEFAULT_VENTE)
            .codeProduit(DEFAULT_CODE_PRODUIT)
            .produit(DEFAULT_PRODUIT)
            .responsableProduit(DEFAULT_RESPONSABLE_PRODUIT)
            .fournisseurProduit(DEFAULT_FOURNISSEUR_PRODUIT)
            .codeFournisseur(DEFAULT_CODE_FOURNISSEUR)
            .reduction(DEFAULT_REDUCTION)
            .ponderation(DEFAULT_PONDERATION)
            .venteChf(DEFAULT_VENTE_CHF)
            .valeurChf(DEFAULT_VALEUR_CHF)
            .remarques(DEFAULT_REMARQUES)
            .active(DEFAULT_ACTIVE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MouvementsStock createUpdatedEntity() {
        return new MouvementsStock()
            .epicerioId(UPDATED_EPICERIO_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .importedDate(UPDATED_IMPORTED_DATE)
            .date(UPDATED_DATE)
            .utilisateur(UPDATED_UTILISATEUR)
            .type(UPDATED_TYPE)
            .epicerioMouvement(UPDATED_EPICERIO_MOUVEMENT)
            .mouvement(UPDATED_MOUVEMENT)
            .solde(UPDATED_SOLDE)
            .vente(UPDATED_VENTE)
            .codeProduit(UPDATED_CODE_PRODUIT)
            .produit(UPDATED_PRODUIT)
            .responsableProduit(UPDATED_RESPONSABLE_PRODUIT)
            .fournisseurProduit(UPDATED_FOURNISSEUR_PRODUIT)
            .codeFournisseur(UPDATED_CODE_FOURNISSEUR)
            .reduction(UPDATED_REDUCTION)
            .ponderation(UPDATED_PONDERATION)
            .venteChf(UPDATED_VENTE_CHF)
            .valeurChf(UPDATED_VALEUR_CHF)
            .remarques(UPDATED_REMARQUES)
            .active(UPDATED_ACTIVE);
    }

    @BeforeEach
    public void initTest() {
        mouvementsStock = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedMouvementsStock != null) {
            mouvementsStockRepository.delete(insertedMouvementsStock);
            insertedMouvementsStock = null;
        }
    }

    @Test
    @Transactional
    void createMouvementsStock() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MouvementsStock
        MouvementsStockDTO mouvementsStockDTO = mouvementsStockMapper.toDto(mouvementsStock);
        var returnedMouvementsStockDTO = om.readValue(
            restMouvementsStockMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mouvementsStockDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MouvementsStockDTO.class
        );

        // Validate the MouvementsStock in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMouvementsStock = mouvementsStockMapper.toEntity(returnedMouvementsStockDTO);
        assertMouvementsStockUpdatableFieldsEquals(returnedMouvementsStock, getPersistedMouvementsStock(returnedMouvementsStock));

        insertedMouvementsStock = returnedMouvementsStock;
    }

    @Test
    @Transactional
    void createMouvementsStockWithExistingId() throws Exception {
        // Create the MouvementsStock with an existing ID
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);
        MouvementsStockDTO mouvementsStockDTO = mouvementsStockMapper.toDto(mouvementsStock);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMouvementsStockMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mouvementsStockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MouvementsStock in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        mouvementsStock.setCreatedDate(null);

        // Create the MouvementsStock, which fails.
        MouvementsStockDTO mouvementsStockDTO = mouvementsStockMapper.toDto(mouvementsStock);

        restMouvementsStockMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mouvementsStockDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastUpdatedDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        mouvementsStock.setLastUpdatedDate(null);

        // Create the MouvementsStock, which fails.
        MouvementsStockDTO mouvementsStockDTO = mouvementsStockMapper.toDto(mouvementsStock);

        restMouvementsStockMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mouvementsStockDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkImportedDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        mouvementsStock.setImportedDate(null);

        // Create the MouvementsStock, which fails.
        MouvementsStockDTO mouvementsStockDTO = mouvementsStockMapper.toDto(mouvementsStock);

        restMouvementsStockMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mouvementsStockDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActiveIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        mouvementsStock.setActive(null);

        // Create the MouvementsStock, which fails.
        MouvementsStockDTO mouvementsStockDTO = mouvementsStockMapper.toDto(mouvementsStock);

        restMouvementsStockMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mouvementsStockDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMouvementsStocks() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList
        restMouvementsStockMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mouvementsStock.getId().toString())))
            .andExpect(jsonPath("$.[*].epicerioId").value(hasItem(DEFAULT_EPICERIO_ID)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(DEFAULT_LAST_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].importedDate").value(hasItem(DEFAULT_IMPORTED_DATE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].utilisateur").value(hasItem(DEFAULT_UTILISATEUR)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].epicerioMouvement").value(hasItem(DEFAULT_EPICERIO_MOUVEMENT.doubleValue())))
            .andExpect(jsonPath("$.[*].mouvement").value(hasItem(DEFAULT_MOUVEMENT.doubleValue())))
            .andExpect(jsonPath("$.[*].solde").value(hasItem(DEFAULT_SOLDE.doubleValue())))
            .andExpect(jsonPath("$.[*].vente").value(hasItem(DEFAULT_VENTE)))
            .andExpect(jsonPath("$.[*].codeProduit").value(hasItem(DEFAULT_CODE_PRODUIT)))
            .andExpect(jsonPath("$.[*].produit").value(hasItem(DEFAULT_PRODUIT)))
            .andExpect(jsonPath("$.[*].responsableProduit").value(hasItem(DEFAULT_RESPONSABLE_PRODUIT)))
            .andExpect(jsonPath("$.[*].fournisseurProduit").value(hasItem(DEFAULT_FOURNISSEUR_PRODUIT)))
            .andExpect(jsonPath("$.[*].codeFournisseur").value(hasItem(DEFAULT_CODE_FOURNISSEUR)))
            .andExpect(jsonPath("$.[*].reduction").value(hasItem(DEFAULT_REDUCTION.doubleValue())))
            .andExpect(jsonPath("$.[*].ponderation").value(hasItem(DEFAULT_PONDERATION.doubleValue())))
            .andExpect(jsonPath("$.[*].venteChf").value(hasItem(DEFAULT_VENTE_CHF.doubleValue())))
            .andExpect(jsonPath("$.[*].valeurChf").value(hasItem(DEFAULT_VALEUR_CHF.doubleValue())))
            .andExpect(jsonPath("$.[*].remarques").value(hasItem(DEFAULT_REMARQUES.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    void getMouvementsStock() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get the mouvementsStock
        restMouvementsStockMockMvc
            .perform(get(ENTITY_API_URL_ID, mouvementsStock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mouvementsStock.getId().toString()))
            .andExpect(jsonPath("$.epicerioId").value(DEFAULT_EPICERIO_ID))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdatedDate").value(DEFAULT_LAST_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.importedDate").value(DEFAULT_IMPORTED_DATE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.utilisateur").value(DEFAULT_UTILISATEUR))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.epicerioMouvement").value(DEFAULT_EPICERIO_MOUVEMENT.doubleValue()))
            .andExpect(jsonPath("$.mouvement").value(DEFAULT_MOUVEMENT.doubleValue()))
            .andExpect(jsonPath("$.solde").value(DEFAULT_SOLDE.doubleValue()))
            .andExpect(jsonPath("$.vente").value(DEFAULT_VENTE))
            .andExpect(jsonPath("$.codeProduit").value(DEFAULT_CODE_PRODUIT))
            .andExpect(jsonPath("$.produit").value(DEFAULT_PRODUIT))
            .andExpect(jsonPath("$.responsableProduit").value(DEFAULT_RESPONSABLE_PRODUIT))
            .andExpect(jsonPath("$.fournisseurProduit").value(DEFAULT_FOURNISSEUR_PRODUIT))
            .andExpect(jsonPath("$.codeFournisseur").value(DEFAULT_CODE_FOURNISSEUR))
            .andExpect(jsonPath("$.reduction").value(DEFAULT_REDUCTION.doubleValue()))
            .andExpect(jsonPath("$.ponderation").value(DEFAULT_PONDERATION.doubleValue()))
            .andExpect(jsonPath("$.venteChf").value(DEFAULT_VENTE_CHF.doubleValue()))
            .andExpect(jsonPath("$.valeurChf").value(DEFAULT_VALEUR_CHF.doubleValue()))
            .andExpect(jsonPath("$.remarques").value(DEFAULT_REMARQUES.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingMouvementsStock() throws Exception {
        // Get the mouvementsStock
        restMouvementsStockMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMouvementsStock() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mouvementsStock
        MouvementsStock updatedMouvementsStock = mouvementsStockRepository.findById(mouvementsStock.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMouvementsStock are not directly saved in db
        em.detach(updatedMouvementsStock);
        updatedMouvementsStock
            .epicerioId(UPDATED_EPICERIO_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .importedDate(UPDATED_IMPORTED_DATE)
            .date(UPDATED_DATE)
            .utilisateur(UPDATED_UTILISATEUR)
            .type(UPDATED_TYPE)
            .epicerioMouvement(UPDATED_EPICERIO_MOUVEMENT)
            .mouvement(UPDATED_MOUVEMENT)
            .solde(UPDATED_SOLDE)
            .vente(UPDATED_VENTE)
            .codeProduit(UPDATED_CODE_PRODUIT)
            .produit(UPDATED_PRODUIT)
            .responsableProduit(UPDATED_RESPONSABLE_PRODUIT)
            .fournisseurProduit(UPDATED_FOURNISSEUR_PRODUIT)
            .codeFournisseur(UPDATED_CODE_FOURNISSEUR)
            .reduction(UPDATED_REDUCTION)
            .ponderation(UPDATED_PONDERATION)
            .venteChf(UPDATED_VENTE_CHF)
            .valeurChf(UPDATED_VALEUR_CHF)
            .remarques(UPDATED_REMARQUES)
            .active(UPDATED_ACTIVE);
        MouvementsStockDTO mouvementsStockDTO = mouvementsStockMapper.toDto(updatedMouvementsStock);

        restMouvementsStockMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mouvementsStockDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(mouvementsStockDTO))
            )
            .andExpect(status().isOk());

        // Validate the MouvementsStock in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMouvementsStockToMatchAllProperties(updatedMouvementsStock);
    }

    @Test
    @Transactional
    void putNonExistingMouvementsStock() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mouvementsStock.setId(UUID.randomUUID());

        // Create the MouvementsStock
        MouvementsStockDTO mouvementsStockDTO = mouvementsStockMapper.toDto(mouvementsStock);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMouvementsStockMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mouvementsStockDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(mouvementsStockDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MouvementsStock in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMouvementsStock() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mouvementsStock.setId(UUID.randomUUID());

        // Create the MouvementsStock
        MouvementsStockDTO mouvementsStockDTO = mouvementsStockMapper.toDto(mouvementsStock);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMouvementsStockMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(mouvementsStockDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MouvementsStock in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMouvementsStock() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mouvementsStock.setId(UUID.randomUUID());

        // Create the MouvementsStock
        MouvementsStockDTO mouvementsStockDTO = mouvementsStockMapper.toDto(mouvementsStock);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMouvementsStockMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mouvementsStockDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MouvementsStock in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMouvementsStockWithPatch() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mouvementsStock using partial update
        MouvementsStock partialUpdatedMouvementsStock = new MouvementsStock();
        partialUpdatedMouvementsStock.setId(mouvementsStock.getId());

        partialUpdatedMouvementsStock
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .importedDate(UPDATED_IMPORTED_DATE)
            .utilisateur(UPDATED_UTILISATEUR)
            .type(UPDATED_TYPE)
            .epicerioMouvement(UPDATED_EPICERIO_MOUVEMENT)
            .vente(UPDATED_VENTE)
            .codeProduit(UPDATED_CODE_PRODUIT)
            .produit(UPDATED_PRODUIT)
            .codeFournisseur(UPDATED_CODE_FOURNISSEUR)
            .reduction(UPDATED_REDUCTION)
            .venteChf(UPDATED_VENTE_CHF)
            .valeurChf(UPDATED_VALEUR_CHF);

        restMouvementsStockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMouvementsStock.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMouvementsStock))
            )
            .andExpect(status().isOk());

        // Validate the MouvementsStock in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMouvementsStockUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMouvementsStock, mouvementsStock),
            getPersistedMouvementsStock(mouvementsStock)
        );
    }

    @Test
    @Transactional
    void fullUpdateMouvementsStockWithPatch() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mouvementsStock using partial update
        MouvementsStock partialUpdatedMouvementsStock = new MouvementsStock();
        partialUpdatedMouvementsStock.setId(mouvementsStock.getId());

        partialUpdatedMouvementsStock
            .epicerioId(UPDATED_EPICERIO_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .importedDate(UPDATED_IMPORTED_DATE)
            .date(UPDATED_DATE)
            .utilisateur(UPDATED_UTILISATEUR)
            .type(UPDATED_TYPE)
            .epicerioMouvement(UPDATED_EPICERIO_MOUVEMENT)
            .mouvement(UPDATED_MOUVEMENT)
            .solde(UPDATED_SOLDE)
            .vente(UPDATED_VENTE)
            .codeProduit(UPDATED_CODE_PRODUIT)
            .produit(UPDATED_PRODUIT)
            .responsableProduit(UPDATED_RESPONSABLE_PRODUIT)
            .fournisseurProduit(UPDATED_FOURNISSEUR_PRODUIT)
            .codeFournisseur(UPDATED_CODE_FOURNISSEUR)
            .reduction(UPDATED_REDUCTION)
            .ponderation(UPDATED_PONDERATION)
            .venteChf(UPDATED_VENTE_CHF)
            .valeurChf(UPDATED_VALEUR_CHF)
            .remarques(UPDATED_REMARQUES)
            .active(UPDATED_ACTIVE);

        restMouvementsStockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMouvementsStock.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMouvementsStock))
            )
            .andExpect(status().isOk());

        // Validate the MouvementsStock in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMouvementsStockUpdatableFieldsEquals(
            partialUpdatedMouvementsStock,
            getPersistedMouvementsStock(partialUpdatedMouvementsStock)
        );
    }

    @Test
    @Transactional
    void patchNonExistingMouvementsStock() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mouvementsStock.setId(UUID.randomUUID());

        // Create the MouvementsStock
        MouvementsStockDTO mouvementsStockDTO = mouvementsStockMapper.toDto(mouvementsStock);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMouvementsStockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mouvementsStockDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(mouvementsStockDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MouvementsStock in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMouvementsStock() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mouvementsStock.setId(UUID.randomUUID());

        // Create the MouvementsStock
        MouvementsStockDTO mouvementsStockDTO = mouvementsStockMapper.toDto(mouvementsStock);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMouvementsStockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(mouvementsStockDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MouvementsStock in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMouvementsStock() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mouvementsStock.setId(UUID.randomUUID());

        // Create the MouvementsStock
        MouvementsStockDTO mouvementsStockDTO = mouvementsStockMapper.toDto(mouvementsStock);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMouvementsStockMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(mouvementsStockDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MouvementsStock in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMouvementsStock() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the mouvementsStock
        restMouvementsStockMockMvc
            .perform(delete(ENTITY_API_URL_ID, mouvementsStock.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return mouvementsStockRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected MouvementsStock getPersistedMouvementsStock(MouvementsStock mouvementsStock) {
        return mouvementsStockRepository.findById(mouvementsStock.getId()).orElseThrow();
    }

    protected void assertPersistedMouvementsStockToMatchAllProperties(MouvementsStock expectedMouvementsStock) {
        assertMouvementsStockAllPropertiesEquals(expectedMouvementsStock, getPersistedMouvementsStock(expectedMouvementsStock));
    }

    protected void assertPersistedMouvementsStockToMatchUpdatableProperties(MouvementsStock expectedMouvementsStock) {
        assertMouvementsStockAllUpdatablePropertiesEquals(expectedMouvementsStock, getPersistedMouvementsStock(expectedMouvementsStock));
    }
}
