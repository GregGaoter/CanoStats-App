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
    private static final Integer SMALLER_EPICERIO_ID = 1 - 1;

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
    private static final Float SMALLER_EPICERIO_MOUVEMENT = 1F - 1F;

    private static final Float DEFAULT_MOUVEMENT = 1F;
    private static final Float UPDATED_MOUVEMENT = 2F;
    private static final Float SMALLER_MOUVEMENT = 1F - 1F;

    private static final Float DEFAULT_SOLDE = 1F;
    private static final Float UPDATED_SOLDE = 2F;
    private static final Float SMALLER_SOLDE = 1F - 1F;

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
    private static final Float SMALLER_REDUCTION = 1F - 1F;

    private static final Float DEFAULT_PONDERATION = 1F;
    private static final Float UPDATED_PONDERATION = 2F;
    private static final Float SMALLER_PONDERATION = 1F - 1F;

    private static final Float DEFAULT_VENTE_CHF = 1F;
    private static final Float UPDATED_VENTE_CHF = 2F;
    private static final Float SMALLER_VENTE_CHF = 1F - 1F;

    private static final Float DEFAULT_VALEUR_CHF = 1F;
    private static final Float UPDATED_VALEUR_CHF = 2F;
    private static final Float SMALLER_VALEUR_CHF = 1F - 1F;

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
    void getMouvementsStocksByIdFiltering() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        UUID id = mouvementsStock.getId();

        defaultMouvementsStockFiltering("id.equals=" + id, "id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByEpicerioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where epicerioId equals to
        defaultMouvementsStockFiltering("epicerioId.equals=" + DEFAULT_EPICERIO_ID, "epicerioId.equals=" + UPDATED_EPICERIO_ID);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByEpicerioIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where epicerioId in
        defaultMouvementsStockFiltering(
            "epicerioId.in=" + DEFAULT_EPICERIO_ID + "," + UPDATED_EPICERIO_ID,
            "epicerioId.in=" + UPDATED_EPICERIO_ID
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByEpicerioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where epicerioId is not null
        defaultMouvementsStockFiltering("epicerioId.specified=true", "epicerioId.specified=false");
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByEpicerioIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where epicerioId is greater than or equal to
        defaultMouvementsStockFiltering(
            "epicerioId.greaterThanOrEqual=" + DEFAULT_EPICERIO_ID,
            "epicerioId.greaterThanOrEqual=" + UPDATED_EPICERIO_ID
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByEpicerioIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where epicerioId is less than or equal to
        defaultMouvementsStockFiltering(
            "epicerioId.lessThanOrEqual=" + DEFAULT_EPICERIO_ID,
            "epicerioId.lessThanOrEqual=" + SMALLER_EPICERIO_ID
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByEpicerioIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where epicerioId is less than
        defaultMouvementsStockFiltering("epicerioId.lessThan=" + UPDATED_EPICERIO_ID, "epicerioId.lessThan=" + DEFAULT_EPICERIO_ID);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByEpicerioIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where epicerioId is greater than
        defaultMouvementsStockFiltering("epicerioId.greaterThan=" + SMALLER_EPICERIO_ID, "epicerioId.greaterThan=" + DEFAULT_EPICERIO_ID);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where createdDate equals to
        defaultMouvementsStockFiltering("createdDate.equals=" + DEFAULT_CREATED_DATE, "createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where createdDate in
        defaultMouvementsStockFiltering(
            "createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE,
            "createdDate.in=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where createdDate is not null
        defaultMouvementsStockFiltering("createdDate.specified=true", "createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where lastUpdatedDate equals to
        defaultMouvementsStockFiltering(
            "lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE,
            "lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where lastUpdatedDate in
        defaultMouvementsStockFiltering(
            "lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE,
            "lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where lastUpdatedDate is not null
        defaultMouvementsStockFiltering("lastUpdatedDate.specified=true", "lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByImportedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where importedDate equals to
        defaultMouvementsStockFiltering("importedDate.equals=" + DEFAULT_IMPORTED_DATE, "importedDate.equals=" + UPDATED_IMPORTED_DATE);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByImportedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where importedDate in
        defaultMouvementsStockFiltering(
            "importedDate.in=" + DEFAULT_IMPORTED_DATE + "," + UPDATED_IMPORTED_DATE,
            "importedDate.in=" + UPDATED_IMPORTED_DATE
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByImportedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where importedDate is not null
        defaultMouvementsStockFiltering("importedDate.specified=true", "importedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where date equals to
        defaultMouvementsStockFiltering("date.equals=" + DEFAULT_DATE, "date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where date in
        defaultMouvementsStockFiltering("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE, "date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where date is not null
        defaultMouvementsStockFiltering("date.specified=true", "date.specified=false");
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByUtilisateurIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where utilisateur equals to
        defaultMouvementsStockFiltering("utilisateur.equals=" + DEFAULT_UTILISATEUR, "utilisateur.equals=" + UPDATED_UTILISATEUR);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByUtilisateurIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where utilisateur in
        defaultMouvementsStockFiltering(
            "utilisateur.in=" + DEFAULT_UTILISATEUR + "," + UPDATED_UTILISATEUR,
            "utilisateur.in=" + UPDATED_UTILISATEUR
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByUtilisateurIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where utilisateur is not null
        defaultMouvementsStockFiltering("utilisateur.specified=true", "utilisateur.specified=false");
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByUtilisateurContainsSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where utilisateur contains
        defaultMouvementsStockFiltering("utilisateur.contains=" + DEFAULT_UTILISATEUR, "utilisateur.contains=" + UPDATED_UTILISATEUR);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByUtilisateurNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where utilisateur does not contain
        defaultMouvementsStockFiltering(
            "utilisateur.doesNotContain=" + UPDATED_UTILISATEUR,
            "utilisateur.doesNotContain=" + DEFAULT_UTILISATEUR
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where type equals to
        defaultMouvementsStockFiltering("type.equals=" + DEFAULT_TYPE, "type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where type in
        defaultMouvementsStockFiltering("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE, "type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where type is not null
        defaultMouvementsStockFiltering("type.specified=true", "type.specified=false");
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByTypeContainsSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where type contains
        defaultMouvementsStockFiltering("type.contains=" + DEFAULT_TYPE, "type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where type does not contain
        defaultMouvementsStockFiltering("type.doesNotContain=" + UPDATED_TYPE, "type.doesNotContain=" + DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByEpicerioMouvementIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where epicerioMouvement equals to
        defaultMouvementsStockFiltering(
            "epicerioMouvement.equals=" + DEFAULT_EPICERIO_MOUVEMENT,
            "epicerioMouvement.equals=" + UPDATED_EPICERIO_MOUVEMENT
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByEpicerioMouvementIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where epicerioMouvement in
        defaultMouvementsStockFiltering(
            "epicerioMouvement.in=" + DEFAULT_EPICERIO_MOUVEMENT + "," + UPDATED_EPICERIO_MOUVEMENT,
            "epicerioMouvement.in=" + UPDATED_EPICERIO_MOUVEMENT
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByEpicerioMouvementIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where epicerioMouvement is not null
        defaultMouvementsStockFiltering("epicerioMouvement.specified=true", "epicerioMouvement.specified=false");
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByEpicerioMouvementIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where epicerioMouvement is greater than or equal to
        defaultMouvementsStockFiltering(
            "epicerioMouvement.greaterThanOrEqual=" + DEFAULT_EPICERIO_MOUVEMENT,
            "epicerioMouvement.greaterThanOrEqual=" + UPDATED_EPICERIO_MOUVEMENT
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByEpicerioMouvementIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where epicerioMouvement is less than or equal to
        defaultMouvementsStockFiltering(
            "epicerioMouvement.lessThanOrEqual=" + DEFAULT_EPICERIO_MOUVEMENT,
            "epicerioMouvement.lessThanOrEqual=" + SMALLER_EPICERIO_MOUVEMENT
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByEpicerioMouvementIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where epicerioMouvement is less than
        defaultMouvementsStockFiltering(
            "epicerioMouvement.lessThan=" + UPDATED_EPICERIO_MOUVEMENT,
            "epicerioMouvement.lessThan=" + DEFAULT_EPICERIO_MOUVEMENT
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByEpicerioMouvementIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where epicerioMouvement is greater than
        defaultMouvementsStockFiltering(
            "epicerioMouvement.greaterThan=" + SMALLER_EPICERIO_MOUVEMENT,
            "epicerioMouvement.greaterThan=" + DEFAULT_EPICERIO_MOUVEMENT
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByMouvementIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where mouvement equals to
        defaultMouvementsStockFiltering("mouvement.equals=" + DEFAULT_MOUVEMENT, "mouvement.equals=" + UPDATED_MOUVEMENT);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByMouvementIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where mouvement in
        defaultMouvementsStockFiltering("mouvement.in=" + DEFAULT_MOUVEMENT + "," + UPDATED_MOUVEMENT, "mouvement.in=" + UPDATED_MOUVEMENT);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByMouvementIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where mouvement is not null
        defaultMouvementsStockFiltering("mouvement.specified=true", "mouvement.specified=false");
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByMouvementIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where mouvement is greater than or equal to
        defaultMouvementsStockFiltering(
            "mouvement.greaterThanOrEqual=" + DEFAULT_MOUVEMENT,
            "mouvement.greaterThanOrEqual=" + UPDATED_MOUVEMENT
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByMouvementIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where mouvement is less than or equal to
        defaultMouvementsStockFiltering("mouvement.lessThanOrEqual=" + DEFAULT_MOUVEMENT, "mouvement.lessThanOrEqual=" + SMALLER_MOUVEMENT);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByMouvementIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where mouvement is less than
        defaultMouvementsStockFiltering("mouvement.lessThan=" + UPDATED_MOUVEMENT, "mouvement.lessThan=" + DEFAULT_MOUVEMENT);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByMouvementIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where mouvement is greater than
        defaultMouvementsStockFiltering("mouvement.greaterThan=" + SMALLER_MOUVEMENT, "mouvement.greaterThan=" + DEFAULT_MOUVEMENT);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksBySoldeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where solde equals to
        defaultMouvementsStockFiltering("solde.equals=" + DEFAULT_SOLDE, "solde.equals=" + UPDATED_SOLDE);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksBySoldeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where solde in
        defaultMouvementsStockFiltering("solde.in=" + DEFAULT_SOLDE + "," + UPDATED_SOLDE, "solde.in=" + UPDATED_SOLDE);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksBySoldeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where solde is not null
        defaultMouvementsStockFiltering("solde.specified=true", "solde.specified=false");
    }

    @Test
    @Transactional
    void getAllMouvementsStocksBySoldeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where solde is greater than or equal to
        defaultMouvementsStockFiltering("solde.greaterThanOrEqual=" + DEFAULT_SOLDE, "solde.greaterThanOrEqual=" + UPDATED_SOLDE);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksBySoldeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where solde is less than or equal to
        defaultMouvementsStockFiltering("solde.lessThanOrEqual=" + DEFAULT_SOLDE, "solde.lessThanOrEqual=" + SMALLER_SOLDE);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksBySoldeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where solde is less than
        defaultMouvementsStockFiltering("solde.lessThan=" + UPDATED_SOLDE, "solde.lessThan=" + DEFAULT_SOLDE);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksBySoldeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where solde is greater than
        defaultMouvementsStockFiltering("solde.greaterThan=" + SMALLER_SOLDE, "solde.greaterThan=" + DEFAULT_SOLDE);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByVenteIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where vente equals to
        defaultMouvementsStockFiltering("vente.equals=" + DEFAULT_VENTE, "vente.equals=" + UPDATED_VENTE);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByVenteIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where vente in
        defaultMouvementsStockFiltering("vente.in=" + DEFAULT_VENTE + "," + UPDATED_VENTE, "vente.in=" + UPDATED_VENTE);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByVenteIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where vente is not null
        defaultMouvementsStockFiltering("vente.specified=true", "vente.specified=false");
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByVenteContainsSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where vente contains
        defaultMouvementsStockFiltering("vente.contains=" + DEFAULT_VENTE, "vente.contains=" + UPDATED_VENTE);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByVenteNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where vente does not contain
        defaultMouvementsStockFiltering("vente.doesNotContain=" + UPDATED_VENTE, "vente.doesNotContain=" + DEFAULT_VENTE);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByCodeProduitIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where codeProduit equals to
        defaultMouvementsStockFiltering("codeProduit.equals=" + DEFAULT_CODE_PRODUIT, "codeProduit.equals=" + UPDATED_CODE_PRODUIT);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByCodeProduitIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where codeProduit in
        defaultMouvementsStockFiltering(
            "codeProduit.in=" + DEFAULT_CODE_PRODUIT + "," + UPDATED_CODE_PRODUIT,
            "codeProduit.in=" + UPDATED_CODE_PRODUIT
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByCodeProduitIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where codeProduit is not null
        defaultMouvementsStockFiltering("codeProduit.specified=true", "codeProduit.specified=false");
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByCodeProduitContainsSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where codeProduit contains
        defaultMouvementsStockFiltering("codeProduit.contains=" + DEFAULT_CODE_PRODUIT, "codeProduit.contains=" + UPDATED_CODE_PRODUIT);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByCodeProduitNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where codeProduit does not contain
        defaultMouvementsStockFiltering(
            "codeProduit.doesNotContain=" + UPDATED_CODE_PRODUIT,
            "codeProduit.doesNotContain=" + DEFAULT_CODE_PRODUIT
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByProduitIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where produit equals to
        defaultMouvementsStockFiltering("produit.equals=" + DEFAULT_PRODUIT, "produit.equals=" + UPDATED_PRODUIT);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByProduitIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where produit in
        defaultMouvementsStockFiltering("produit.in=" + DEFAULT_PRODUIT + "," + UPDATED_PRODUIT, "produit.in=" + UPDATED_PRODUIT);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByProduitIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where produit is not null
        defaultMouvementsStockFiltering("produit.specified=true", "produit.specified=false");
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByProduitContainsSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where produit contains
        defaultMouvementsStockFiltering("produit.contains=" + DEFAULT_PRODUIT, "produit.contains=" + UPDATED_PRODUIT);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByProduitNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where produit does not contain
        defaultMouvementsStockFiltering("produit.doesNotContain=" + UPDATED_PRODUIT, "produit.doesNotContain=" + DEFAULT_PRODUIT);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByResponsableProduitIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where responsableProduit equals to
        defaultMouvementsStockFiltering(
            "responsableProduit.equals=" + DEFAULT_RESPONSABLE_PRODUIT,
            "responsableProduit.equals=" + UPDATED_RESPONSABLE_PRODUIT
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByResponsableProduitIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where responsableProduit in
        defaultMouvementsStockFiltering(
            "responsableProduit.in=" + DEFAULT_RESPONSABLE_PRODUIT + "," + UPDATED_RESPONSABLE_PRODUIT,
            "responsableProduit.in=" + UPDATED_RESPONSABLE_PRODUIT
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByResponsableProduitIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where responsableProduit is not null
        defaultMouvementsStockFiltering("responsableProduit.specified=true", "responsableProduit.specified=false");
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByResponsableProduitContainsSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where responsableProduit contains
        defaultMouvementsStockFiltering(
            "responsableProduit.contains=" + DEFAULT_RESPONSABLE_PRODUIT,
            "responsableProduit.contains=" + UPDATED_RESPONSABLE_PRODUIT
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByResponsableProduitNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where responsableProduit does not contain
        defaultMouvementsStockFiltering(
            "responsableProduit.doesNotContain=" + UPDATED_RESPONSABLE_PRODUIT,
            "responsableProduit.doesNotContain=" + DEFAULT_RESPONSABLE_PRODUIT
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByFournisseurProduitIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where fournisseurProduit equals to
        defaultMouvementsStockFiltering(
            "fournisseurProduit.equals=" + DEFAULT_FOURNISSEUR_PRODUIT,
            "fournisseurProduit.equals=" + UPDATED_FOURNISSEUR_PRODUIT
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByFournisseurProduitIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where fournisseurProduit in
        defaultMouvementsStockFiltering(
            "fournisseurProduit.in=" + DEFAULT_FOURNISSEUR_PRODUIT + "," + UPDATED_FOURNISSEUR_PRODUIT,
            "fournisseurProduit.in=" + UPDATED_FOURNISSEUR_PRODUIT
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByFournisseurProduitIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where fournisseurProduit is not null
        defaultMouvementsStockFiltering("fournisseurProduit.specified=true", "fournisseurProduit.specified=false");
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByFournisseurProduitContainsSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where fournisseurProduit contains
        defaultMouvementsStockFiltering(
            "fournisseurProduit.contains=" + DEFAULT_FOURNISSEUR_PRODUIT,
            "fournisseurProduit.contains=" + UPDATED_FOURNISSEUR_PRODUIT
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByFournisseurProduitNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where fournisseurProduit does not contain
        defaultMouvementsStockFiltering(
            "fournisseurProduit.doesNotContain=" + UPDATED_FOURNISSEUR_PRODUIT,
            "fournisseurProduit.doesNotContain=" + DEFAULT_FOURNISSEUR_PRODUIT
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByCodeFournisseurIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where codeFournisseur equals to
        defaultMouvementsStockFiltering(
            "codeFournisseur.equals=" + DEFAULT_CODE_FOURNISSEUR,
            "codeFournisseur.equals=" + UPDATED_CODE_FOURNISSEUR
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByCodeFournisseurIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where codeFournisseur in
        defaultMouvementsStockFiltering(
            "codeFournisseur.in=" + DEFAULT_CODE_FOURNISSEUR + "," + UPDATED_CODE_FOURNISSEUR,
            "codeFournisseur.in=" + UPDATED_CODE_FOURNISSEUR
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByCodeFournisseurIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where codeFournisseur is not null
        defaultMouvementsStockFiltering("codeFournisseur.specified=true", "codeFournisseur.specified=false");
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByCodeFournisseurContainsSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where codeFournisseur contains
        defaultMouvementsStockFiltering(
            "codeFournisseur.contains=" + DEFAULT_CODE_FOURNISSEUR,
            "codeFournisseur.contains=" + UPDATED_CODE_FOURNISSEUR
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByCodeFournisseurNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where codeFournisseur does not contain
        defaultMouvementsStockFiltering(
            "codeFournisseur.doesNotContain=" + UPDATED_CODE_FOURNISSEUR,
            "codeFournisseur.doesNotContain=" + DEFAULT_CODE_FOURNISSEUR
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByReductionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where reduction equals to
        defaultMouvementsStockFiltering("reduction.equals=" + DEFAULT_REDUCTION, "reduction.equals=" + UPDATED_REDUCTION);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByReductionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where reduction in
        defaultMouvementsStockFiltering("reduction.in=" + DEFAULT_REDUCTION + "," + UPDATED_REDUCTION, "reduction.in=" + UPDATED_REDUCTION);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByReductionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where reduction is not null
        defaultMouvementsStockFiltering("reduction.specified=true", "reduction.specified=false");
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByReductionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where reduction is greater than or equal to
        defaultMouvementsStockFiltering(
            "reduction.greaterThanOrEqual=" + DEFAULT_REDUCTION,
            "reduction.greaterThanOrEqual=" + UPDATED_REDUCTION
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByReductionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where reduction is less than or equal to
        defaultMouvementsStockFiltering("reduction.lessThanOrEqual=" + DEFAULT_REDUCTION, "reduction.lessThanOrEqual=" + SMALLER_REDUCTION);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByReductionIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where reduction is less than
        defaultMouvementsStockFiltering("reduction.lessThan=" + UPDATED_REDUCTION, "reduction.lessThan=" + DEFAULT_REDUCTION);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByReductionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where reduction is greater than
        defaultMouvementsStockFiltering("reduction.greaterThan=" + SMALLER_REDUCTION, "reduction.greaterThan=" + DEFAULT_REDUCTION);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByPonderationIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where ponderation equals to
        defaultMouvementsStockFiltering("ponderation.equals=" + DEFAULT_PONDERATION, "ponderation.equals=" + UPDATED_PONDERATION);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByPonderationIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where ponderation in
        defaultMouvementsStockFiltering(
            "ponderation.in=" + DEFAULT_PONDERATION + "," + UPDATED_PONDERATION,
            "ponderation.in=" + UPDATED_PONDERATION
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByPonderationIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where ponderation is not null
        defaultMouvementsStockFiltering("ponderation.specified=true", "ponderation.specified=false");
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByPonderationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where ponderation is greater than or equal to
        defaultMouvementsStockFiltering(
            "ponderation.greaterThanOrEqual=" + DEFAULT_PONDERATION,
            "ponderation.greaterThanOrEqual=" + UPDATED_PONDERATION
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByPonderationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where ponderation is less than or equal to
        defaultMouvementsStockFiltering(
            "ponderation.lessThanOrEqual=" + DEFAULT_PONDERATION,
            "ponderation.lessThanOrEqual=" + SMALLER_PONDERATION
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByPonderationIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where ponderation is less than
        defaultMouvementsStockFiltering("ponderation.lessThan=" + UPDATED_PONDERATION, "ponderation.lessThan=" + DEFAULT_PONDERATION);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByPonderationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where ponderation is greater than
        defaultMouvementsStockFiltering("ponderation.greaterThan=" + SMALLER_PONDERATION, "ponderation.greaterThan=" + DEFAULT_PONDERATION);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByVenteChfIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where venteChf equals to
        defaultMouvementsStockFiltering("venteChf.equals=" + DEFAULT_VENTE_CHF, "venteChf.equals=" + UPDATED_VENTE_CHF);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByVenteChfIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where venteChf in
        defaultMouvementsStockFiltering("venteChf.in=" + DEFAULT_VENTE_CHF + "," + UPDATED_VENTE_CHF, "venteChf.in=" + UPDATED_VENTE_CHF);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByVenteChfIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where venteChf is not null
        defaultMouvementsStockFiltering("venteChf.specified=true", "venteChf.specified=false");
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByVenteChfIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where venteChf is greater than or equal to
        defaultMouvementsStockFiltering(
            "venteChf.greaterThanOrEqual=" + DEFAULT_VENTE_CHF,
            "venteChf.greaterThanOrEqual=" + UPDATED_VENTE_CHF
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByVenteChfIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where venteChf is less than or equal to
        defaultMouvementsStockFiltering("venteChf.lessThanOrEqual=" + DEFAULT_VENTE_CHF, "venteChf.lessThanOrEqual=" + SMALLER_VENTE_CHF);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByVenteChfIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where venteChf is less than
        defaultMouvementsStockFiltering("venteChf.lessThan=" + UPDATED_VENTE_CHF, "venteChf.lessThan=" + DEFAULT_VENTE_CHF);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByVenteChfIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where venteChf is greater than
        defaultMouvementsStockFiltering("venteChf.greaterThan=" + SMALLER_VENTE_CHF, "venteChf.greaterThan=" + DEFAULT_VENTE_CHF);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByValeurChfIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where valeurChf equals to
        defaultMouvementsStockFiltering("valeurChf.equals=" + DEFAULT_VALEUR_CHF, "valeurChf.equals=" + UPDATED_VALEUR_CHF);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByValeurChfIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where valeurChf in
        defaultMouvementsStockFiltering(
            "valeurChf.in=" + DEFAULT_VALEUR_CHF + "," + UPDATED_VALEUR_CHF,
            "valeurChf.in=" + UPDATED_VALEUR_CHF
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByValeurChfIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where valeurChf is not null
        defaultMouvementsStockFiltering("valeurChf.specified=true", "valeurChf.specified=false");
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByValeurChfIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where valeurChf is greater than or equal to
        defaultMouvementsStockFiltering(
            "valeurChf.greaterThanOrEqual=" + DEFAULT_VALEUR_CHF,
            "valeurChf.greaterThanOrEqual=" + UPDATED_VALEUR_CHF
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByValeurChfIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where valeurChf is less than or equal to
        defaultMouvementsStockFiltering(
            "valeurChf.lessThanOrEqual=" + DEFAULT_VALEUR_CHF,
            "valeurChf.lessThanOrEqual=" + SMALLER_VALEUR_CHF
        );
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByValeurChfIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where valeurChf is less than
        defaultMouvementsStockFiltering("valeurChf.lessThan=" + UPDATED_VALEUR_CHF, "valeurChf.lessThan=" + DEFAULT_VALEUR_CHF);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByValeurChfIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where valeurChf is greater than
        defaultMouvementsStockFiltering("valeurChf.greaterThan=" + SMALLER_VALEUR_CHF, "valeurChf.greaterThan=" + DEFAULT_VALEUR_CHF);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where active equals to
        defaultMouvementsStockFiltering("active.equals=" + DEFAULT_ACTIVE, "active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where active in
        defaultMouvementsStockFiltering("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE, "active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllMouvementsStocksByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMouvementsStock = mouvementsStockRepository.saveAndFlush(mouvementsStock);

        // Get all the mouvementsStockList where active is not null
        defaultMouvementsStockFiltering("active.specified=true", "active.specified=false");
    }

    private void defaultMouvementsStockFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultMouvementsStockShouldBeFound(shouldBeFound);
        defaultMouvementsStockShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMouvementsStockShouldBeFound(String filter) throws Exception {
        restMouvementsStockMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
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

        // Check, that the count call also returns 1
        restMouvementsStockMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMouvementsStockShouldNotBeFound(String filter) throws Exception {
        restMouvementsStockMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMouvementsStockMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
