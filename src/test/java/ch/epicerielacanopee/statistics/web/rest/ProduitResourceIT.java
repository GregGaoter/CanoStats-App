package ch.epicerielacanopee.statistics.web.rest;

import static ch.epicerielacanopee.statistics.domain.ProduitAsserts.*;
import static ch.epicerielacanopee.statistics.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.epicerielacanopee.statistics.IntegrationTest;
import ch.epicerielacanopee.statistics.domain.Produit;
import ch.epicerielacanopee.statistics.repository.ProduitRepository;
import ch.epicerielacanopee.statistics.service.dto.ProduitDTO;
import ch.epicerielacanopee.statistics.service.mapper.ProduitMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link ProduitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProduitResourceIT {

    private static final Integer DEFAULT_EPICERIO_ID = 1;
    private static final Integer UPDATED_EPICERIO_ID = 2;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_IMPORTED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_IMPORTED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DISPONIBLE = "AAAAAAAAAA";
    private static final String UPDATED_DISPONIBLE = "BBBBBBBBBB";

    private static final Float DEFAULT_PRIX_FOURNISSEUR = 1F;
    private static final Float UPDATED_PRIX_FOURNISSEUR = 2F;

    private static final String DEFAULT_HT_TTC = "AAAAAAAAAA";
    private static final String UPDATED_HT_TTC = "BBBBBBBBBB";

    private static final Float DEFAULT_TAUX_TVA = 1F;
    private static final Float UPDATED_TAUX_TVA = 2F;

    private static final Float DEFAULT_MARGE_PROFIT = 1F;
    private static final Float UPDATED_MARGE_PROFIT = 2F;

    private static final Float DEFAULT_PRIX_VENTE = 1F;
    private static final Float UPDATED_PRIX_VENTE = 2F;

    private static final String DEFAULT_VENDU = "AAAAAAAAAA";
    private static final String UPDATED_VENDU = "BBBBBBBBBB";

    private static final Float DEFAULT_QUANTITE_PAR_PIECE = 1F;
    private static final Float UPDATED_QUANTITE_PAR_PIECE = 2F;

    private static final String DEFAULT_UNITE = "AAAAAAAAAA";
    private static final String UPDATED_UNITE = "BBBBBBBBBB";

    private static final String DEFAULT_PRIX_PAR_UNITE = "AAAAAAAAAA";
    private static final String UPDATED_PRIX_PAR_UNITE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_REMARQUES_INTERNES = "AAAAAAAAAA";
    private static final String UPDATED_REMARQUES_INTERNES = "BBBBBBBBBB";

    private static final String DEFAULT_FOURNISSEUR = "AAAAAAAAAA";
    private static final String UPDATED_FOURNISSEUR = "BBBBBBBBBB";

    private static final String DEFAULT_REF_FOURNISSEUR = "AAAAAAAAAA";
    private static final String UPDATED_REF_FOURNISSEUR = "BBBBBBBBBB";

    private static final Float DEFAULT_STOCK = 1F;
    private static final Float UPDATED_STOCK = 2F;

    private static final Float DEFAULT_COMMANDES_CLIENTS = 1F;
    private static final Float UPDATED_COMMANDES_CLIENTS = 2F;

    private static final LocalDate DEFAULT_DERNIERE_VERIFICATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DERNIERE_VERIFICATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DERNIERE_LIVRAISON_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DERNIERE_LIVRAISON_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ACHAT_FOURNISSEUR = "AAAAAAAAAA";
    private static final String UPDATED_ACHAT_FOURNISSEUR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DERNIER_ACHAT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DERNIER_ACHAT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_DERNIER_ACHAT_QUANTITE = 1F;
    private static final Float UPDATED_DERNIER_ACHAT_QUANTITE = 2F;

    private static final Float DEFAULT_STATS_LIVRAISON = 1F;
    private static final Float UPDATED_STATS_LIVRAISON = 2F;

    private static final Float DEFAULT_STATS_PERTE = 1F;
    private static final Float UPDATED_STATS_PERTE = 2F;

    private static final Float DEFAULT_STATS_VENTE = 1F;
    private static final Float UPDATED_STATS_VENTE = 2F;

    private static final Float DEFAULT_STATS_VENTE_SPECIALE = 1F;
    private static final Float UPDATED_STATS_VENTE_SPECIALE = 2F;

    private static final String DEFAULT_TAGS = "AAAAAAAAAA";
    private static final String UPDATED_TAGS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/produits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private ProduitMapper produitMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProduitMockMvc;

    private Produit produit;

    private Produit insertedProduit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Produit createEntity() {
        return new Produit()
            .epicerioId(DEFAULT_EPICERIO_ID)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .importedDate(DEFAULT_IMPORTED_DATE)
            .nom(DEFAULT_NOM)
            .code(DEFAULT_CODE)
            .disponible(DEFAULT_DISPONIBLE)
            .prixFournisseur(DEFAULT_PRIX_FOURNISSEUR)
            .htTtc(DEFAULT_HT_TTC)
            .tauxTva(DEFAULT_TAUX_TVA)
            .margeProfit(DEFAULT_MARGE_PROFIT)
            .prixVente(DEFAULT_PRIX_VENTE)
            .vendu(DEFAULT_VENDU)
            .quantiteParPiece(DEFAULT_QUANTITE_PAR_PIECE)
            .unite(DEFAULT_UNITE)
            .prixParUnite(DEFAULT_PRIX_PAR_UNITE)
            .description(DEFAULT_DESCRIPTION)
            .remarquesInternes(DEFAULT_REMARQUES_INTERNES)
            .fournisseur(DEFAULT_FOURNISSEUR)
            .refFournisseur(DEFAULT_REF_FOURNISSEUR)
            .stock(DEFAULT_STOCK)
            .commandesClients(DEFAULT_COMMANDES_CLIENTS)
            .derniereVerificationDate(DEFAULT_DERNIERE_VERIFICATION_DATE)
            .derniereLivraisonDate(DEFAULT_DERNIERE_LIVRAISON_DATE)
            .achatFournisseur(DEFAULT_ACHAT_FOURNISSEUR)
            .dernierAchatDate(DEFAULT_DERNIER_ACHAT_DATE)
            .dernierAchatQuantite(DEFAULT_DERNIER_ACHAT_QUANTITE)
            .statsLivraison(DEFAULT_STATS_LIVRAISON)
            .statsPerte(DEFAULT_STATS_PERTE)
            .statsVente(DEFAULT_STATS_VENTE)
            .statsVenteSpeciale(DEFAULT_STATS_VENTE_SPECIALE)
            .tags(DEFAULT_TAGS);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Produit createUpdatedEntity() {
        return new Produit()
            .epicerioId(UPDATED_EPICERIO_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .importedDate(UPDATED_IMPORTED_DATE)
            .nom(UPDATED_NOM)
            .code(UPDATED_CODE)
            .disponible(UPDATED_DISPONIBLE)
            .prixFournisseur(UPDATED_PRIX_FOURNISSEUR)
            .htTtc(UPDATED_HT_TTC)
            .tauxTva(UPDATED_TAUX_TVA)
            .margeProfit(UPDATED_MARGE_PROFIT)
            .prixVente(UPDATED_PRIX_VENTE)
            .vendu(UPDATED_VENDU)
            .quantiteParPiece(UPDATED_QUANTITE_PAR_PIECE)
            .unite(UPDATED_UNITE)
            .prixParUnite(UPDATED_PRIX_PAR_UNITE)
            .description(UPDATED_DESCRIPTION)
            .remarquesInternes(UPDATED_REMARQUES_INTERNES)
            .fournisseur(UPDATED_FOURNISSEUR)
            .refFournisseur(UPDATED_REF_FOURNISSEUR)
            .stock(UPDATED_STOCK)
            .commandesClients(UPDATED_COMMANDES_CLIENTS)
            .derniereVerificationDate(UPDATED_DERNIERE_VERIFICATION_DATE)
            .derniereLivraisonDate(UPDATED_DERNIERE_LIVRAISON_DATE)
            .achatFournisseur(UPDATED_ACHAT_FOURNISSEUR)
            .dernierAchatDate(UPDATED_DERNIER_ACHAT_DATE)
            .dernierAchatQuantite(UPDATED_DERNIER_ACHAT_QUANTITE)
            .statsLivraison(UPDATED_STATS_LIVRAISON)
            .statsPerte(UPDATED_STATS_PERTE)
            .statsVente(UPDATED_STATS_VENTE)
            .statsVenteSpeciale(UPDATED_STATS_VENTE_SPECIALE)
            .tags(UPDATED_TAGS);
    }

    @BeforeEach
    public void initTest() {
        produit = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedProduit != null) {
            produitRepository.delete(insertedProduit);
            insertedProduit = null;
        }
    }

    @Test
    @Transactional
    void createProduit() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Produit
        ProduitDTO produitDTO = produitMapper.toDto(produit);
        var returnedProduitDTO = om.readValue(
            restProduitMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(produitDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ProduitDTO.class
        );

        // Validate the Produit in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedProduit = produitMapper.toEntity(returnedProduitDTO);
        assertProduitUpdatableFieldsEquals(returnedProduit, getPersistedProduit(returnedProduit));

        insertedProduit = returnedProduit;
    }

    @Test
    @Transactional
    void createProduitWithExistingId() throws Exception {
        // Create the Produit with an existing ID
        insertedProduit = produitRepository.saveAndFlush(produit);
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProduitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(produitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Produit in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        produit.setCreatedDate(null);

        // Create the Produit, which fails.
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        restProduitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(produitDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastUpdatedDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        produit.setLastUpdatedDate(null);

        // Create the Produit, which fails.
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        restProduitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(produitDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkImportedDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        produit.setImportedDate(null);

        // Create the Produit, which fails.
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        restProduitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(produitDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProduits() throws Exception {
        // Initialize the database
        insertedProduit = produitRepository.saveAndFlush(produit);

        // Get all the produitList
        restProduitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produit.getId().toString())))
            .andExpect(jsonPath("$.[*].epicerioId").value(hasItem(DEFAULT_EPICERIO_ID)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(DEFAULT_LAST_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].importedDate").value(hasItem(DEFAULT_IMPORTED_DATE.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].disponible").value(hasItem(DEFAULT_DISPONIBLE)))
            .andExpect(jsonPath("$.[*].prixFournisseur").value(hasItem(DEFAULT_PRIX_FOURNISSEUR.doubleValue())))
            .andExpect(jsonPath("$.[*].htTtc").value(hasItem(DEFAULT_HT_TTC)))
            .andExpect(jsonPath("$.[*].tauxTva").value(hasItem(DEFAULT_TAUX_TVA.doubleValue())))
            .andExpect(jsonPath("$.[*].margeProfit").value(hasItem(DEFAULT_MARGE_PROFIT.doubleValue())))
            .andExpect(jsonPath("$.[*].prixVente").value(hasItem(DEFAULT_PRIX_VENTE.doubleValue())))
            .andExpect(jsonPath("$.[*].vendu").value(hasItem(DEFAULT_VENDU)))
            .andExpect(jsonPath("$.[*].quantiteParPiece").value(hasItem(DEFAULT_QUANTITE_PAR_PIECE.doubleValue())))
            .andExpect(jsonPath("$.[*].unite").value(hasItem(DEFAULT_UNITE)))
            .andExpect(jsonPath("$.[*].prixParUnite").value(hasItem(DEFAULT_PRIX_PAR_UNITE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].remarquesInternes").value(hasItem(DEFAULT_REMARQUES_INTERNES.toString())))
            .andExpect(jsonPath("$.[*].fournisseur").value(hasItem(DEFAULT_FOURNISSEUR)))
            .andExpect(jsonPath("$.[*].refFournisseur").value(hasItem(DEFAULT_REF_FOURNISSEUR)))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK.doubleValue())))
            .andExpect(jsonPath("$.[*].commandesClients").value(hasItem(DEFAULT_COMMANDES_CLIENTS.doubleValue())))
            .andExpect(jsonPath("$.[*].derniereVerificationDate").value(hasItem(DEFAULT_DERNIERE_VERIFICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].derniereLivraisonDate").value(hasItem(DEFAULT_DERNIERE_LIVRAISON_DATE.toString())))
            .andExpect(jsonPath("$.[*].achatFournisseur").value(hasItem(DEFAULT_ACHAT_FOURNISSEUR)))
            .andExpect(jsonPath("$.[*].dernierAchatDate").value(hasItem(DEFAULT_DERNIER_ACHAT_DATE.toString())))
            .andExpect(jsonPath("$.[*].dernierAchatQuantite").value(hasItem(DEFAULT_DERNIER_ACHAT_QUANTITE.doubleValue())))
            .andExpect(jsonPath("$.[*].statsLivraison").value(hasItem(DEFAULT_STATS_LIVRAISON.doubleValue())))
            .andExpect(jsonPath("$.[*].statsPerte").value(hasItem(DEFAULT_STATS_PERTE.doubleValue())))
            .andExpect(jsonPath("$.[*].statsVente").value(hasItem(DEFAULT_STATS_VENTE.doubleValue())))
            .andExpect(jsonPath("$.[*].statsVenteSpeciale").value(hasItem(DEFAULT_STATS_VENTE_SPECIALE.doubleValue())))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS.toString())));
    }

    @Test
    @Transactional
    void getProduit() throws Exception {
        // Initialize the database
        insertedProduit = produitRepository.saveAndFlush(produit);

        // Get the produit
        restProduitMockMvc
            .perform(get(ENTITY_API_URL_ID, produit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(produit.getId().toString()))
            .andExpect(jsonPath("$.epicerioId").value(DEFAULT_EPICERIO_ID))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdatedDate").value(DEFAULT_LAST_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.importedDate").value(DEFAULT_IMPORTED_DATE.toString()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.disponible").value(DEFAULT_DISPONIBLE))
            .andExpect(jsonPath("$.prixFournisseur").value(DEFAULT_PRIX_FOURNISSEUR.doubleValue()))
            .andExpect(jsonPath("$.htTtc").value(DEFAULT_HT_TTC))
            .andExpect(jsonPath("$.tauxTva").value(DEFAULT_TAUX_TVA.doubleValue()))
            .andExpect(jsonPath("$.margeProfit").value(DEFAULT_MARGE_PROFIT.doubleValue()))
            .andExpect(jsonPath("$.prixVente").value(DEFAULT_PRIX_VENTE.doubleValue()))
            .andExpect(jsonPath("$.vendu").value(DEFAULT_VENDU))
            .andExpect(jsonPath("$.quantiteParPiece").value(DEFAULT_QUANTITE_PAR_PIECE.doubleValue()))
            .andExpect(jsonPath("$.unite").value(DEFAULT_UNITE))
            .andExpect(jsonPath("$.prixParUnite").value(DEFAULT_PRIX_PAR_UNITE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.remarquesInternes").value(DEFAULT_REMARQUES_INTERNES.toString()))
            .andExpect(jsonPath("$.fournisseur").value(DEFAULT_FOURNISSEUR))
            .andExpect(jsonPath("$.refFournisseur").value(DEFAULT_REF_FOURNISSEUR))
            .andExpect(jsonPath("$.stock").value(DEFAULT_STOCK.doubleValue()))
            .andExpect(jsonPath("$.commandesClients").value(DEFAULT_COMMANDES_CLIENTS.doubleValue()))
            .andExpect(jsonPath("$.derniereVerificationDate").value(DEFAULT_DERNIERE_VERIFICATION_DATE.toString()))
            .andExpect(jsonPath("$.derniereLivraisonDate").value(DEFAULT_DERNIERE_LIVRAISON_DATE.toString()))
            .andExpect(jsonPath("$.achatFournisseur").value(DEFAULT_ACHAT_FOURNISSEUR))
            .andExpect(jsonPath("$.dernierAchatDate").value(DEFAULT_DERNIER_ACHAT_DATE.toString()))
            .andExpect(jsonPath("$.dernierAchatQuantite").value(DEFAULT_DERNIER_ACHAT_QUANTITE.doubleValue()))
            .andExpect(jsonPath("$.statsLivraison").value(DEFAULT_STATS_LIVRAISON.doubleValue()))
            .andExpect(jsonPath("$.statsPerte").value(DEFAULT_STATS_PERTE.doubleValue()))
            .andExpect(jsonPath("$.statsVente").value(DEFAULT_STATS_VENTE.doubleValue()))
            .andExpect(jsonPath("$.statsVenteSpeciale").value(DEFAULT_STATS_VENTE_SPECIALE.doubleValue()))
            .andExpect(jsonPath("$.tags").value(DEFAULT_TAGS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProduit() throws Exception {
        // Get the produit
        restProduitMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProduit() throws Exception {
        // Initialize the database
        insertedProduit = produitRepository.saveAndFlush(produit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the produit
        Produit updatedProduit = produitRepository.findById(produit.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProduit are not directly saved in db
        em.detach(updatedProduit);
        updatedProduit
            .epicerioId(UPDATED_EPICERIO_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .importedDate(UPDATED_IMPORTED_DATE)
            .nom(UPDATED_NOM)
            .code(UPDATED_CODE)
            .disponible(UPDATED_DISPONIBLE)
            .prixFournisseur(UPDATED_PRIX_FOURNISSEUR)
            .htTtc(UPDATED_HT_TTC)
            .tauxTva(UPDATED_TAUX_TVA)
            .margeProfit(UPDATED_MARGE_PROFIT)
            .prixVente(UPDATED_PRIX_VENTE)
            .vendu(UPDATED_VENDU)
            .quantiteParPiece(UPDATED_QUANTITE_PAR_PIECE)
            .unite(UPDATED_UNITE)
            .prixParUnite(UPDATED_PRIX_PAR_UNITE)
            .description(UPDATED_DESCRIPTION)
            .remarquesInternes(UPDATED_REMARQUES_INTERNES)
            .fournisseur(UPDATED_FOURNISSEUR)
            .refFournisseur(UPDATED_REF_FOURNISSEUR)
            .stock(UPDATED_STOCK)
            .commandesClients(UPDATED_COMMANDES_CLIENTS)
            .derniereVerificationDate(UPDATED_DERNIERE_VERIFICATION_DATE)
            .derniereLivraisonDate(UPDATED_DERNIERE_LIVRAISON_DATE)
            .achatFournisseur(UPDATED_ACHAT_FOURNISSEUR)
            .dernierAchatDate(UPDATED_DERNIER_ACHAT_DATE)
            .dernierAchatQuantite(UPDATED_DERNIER_ACHAT_QUANTITE)
            .statsLivraison(UPDATED_STATS_LIVRAISON)
            .statsPerte(UPDATED_STATS_PERTE)
            .statsVente(UPDATED_STATS_VENTE)
            .statsVenteSpeciale(UPDATED_STATS_VENTE_SPECIALE)
            .tags(UPDATED_TAGS);
        ProduitDTO produitDTO = produitMapper.toDto(updatedProduit);

        restProduitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, produitDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(produitDTO))
            )
            .andExpect(status().isOk());

        // Validate the Produit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProduitToMatchAllProperties(updatedProduit);
    }

    @Test
    @Transactional
    void putNonExistingProduit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        produit.setId(UUID.randomUUID());

        // Create the Produit
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProduitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, produitDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(produitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Produit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProduit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        produit.setId(UUID.randomUUID());

        // Create the Produit
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProduitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(produitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Produit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProduit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        produit.setId(UUID.randomUUID());

        // Create the Produit
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProduitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(produitDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Produit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProduitWithPatch() throws Exception {
        // Initialize the database
        insertedProduit = produitRepository.saveAndFlush(produit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the produit using partial update
        Produit partialUpdatedProduit = new Produit();
        partialUpdatedProduit.setId(produit.getId());

        partialUpdatedProduit
            .code(UPDATED_CODE)
            .prixFournisseur(UPDATED_PRIX_FOURNISSEUR)
            .htTtc(UPDATED_HT_TTC)
            .prixVente(UPDATED_PRIX_VENTE)
            .vendu(UPDATED_VENDU)
            .description(UPDATED_DESCRIPTION)
            .remarquesInternes(UPDATED_REMARQUES_INTERNES)
            .fournisseur(UPDATED_FOURNISSEUR)
            .refFournisseur(UPDATED_REF_FOURNISSEUR)
            .stock(UPDATED_STOCK)
            .commandesClients(UPDATED_COMMANDES_CLIENTS)
            .derniereVerificationDate(UPDATED_DERNIERE_VERIFICATION_DATE)
            .dernierAchatQuantite(UPDATED_DERNIER_ACHAT_QUANTITE)
            .tags(UPDATED_TAGS);

        restProduitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProduit))
            )
            .andExpect(status().isOk());

        // Validate the Produit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProduitUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedProduit, produit), getPersistedProduit(produit));
    }

    @Test
    @Transactional
    void fullUpdateProduitWithPatch() throws Exception {
        // Initialize the database
        insertedProduit = produitRepository.saveAndFlush(produit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the produit using partial update
        Produit partialUpdatedProduit = new Produit();
        partialUpdatedProduit.setId(produit.getId());

        partialUpdatedProduit
            .epicerioId(UPDATED_EPICERIO_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .importedDate(UPDATED_IMPORTED_DATE)
            .nom(UPDATED_NOM)
            .code(UPDATED_CODE)
            .disponible(UPDATED_DISPONIBLE)
            .prixFournisseur(UPDATED_PRIX_FOURNISSEUR)
            .htTtc(UPDATED_HT_TTC)
            .tauxTva(UPDATED_TAUX_TVA)
            .margeProfit(UPDATED_MARGE_PROFIT)
            .prixVente(UPDATED_PRIX_VENTE)
            .vendu(UPDATED_VENDU)
            .quantiteParPiece(UPDATED_QUANTITE_PAR_PIECE)
            .unite(UPDATED_UNITE)
            .prixParUnite(UPDATED_PRIX_PAR_UNITE)
            .description(UPDATED_DESCRIPTION)
            .remarquesInternes(UPDATED_REMARQUES_INTERNES)
            .fournisseur(UPDATED_FOURNISSEUR)
            .refFournisseur(UPDATED_REF_FOURNISSEUR)
            .stock(UPDATED_STOCK)
            .commandesClients(UPDATED_COMMANDES_CLIENTS)
            .derniereVerificationDate(UPDATED_DERNIERE_VERIFICATION_DATE)
            .derniereLivraisonDate(UPDATED_DERNIERE_LIVRAISON_DATE)
            .achatFournisseur(UPDATED_ACHAT_FOURNISSEUR)
            .dernierAchatDate(UPDATED_DERNIER_ACHAT_DATE)
            .dernierAchatQuantite(UPDATED_DERNIER_ACHAT_QUANTITE)
            .statsLivraison(UPDATED_STATS_LIVRAISON)
            .statsPerte(UPDATED_STATS_PERTE)
            .statsVente(UPDATED_STATS_VENTE)
            .statsVenteSpeciale(UPDATED_STATS_VENTE_SPECIALE)
            .tags(UPDATED_TAGS);

        restProduitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProduit))
            )
            .andExpect(status().isOk());

        // Validate the Produit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProduitUpdatableFieldsEquals(partialUpdatedProduit, getPersistedProduit(partialUpdatedProduit));
    }

    @Test
    @Transactional
    void patchNonExistingProduit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        produit.setId(UUID.randomUUID());

        // Create the Produit
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProduitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, produitDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(produitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Produit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProduit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        produit.setId(UUID.randomUUID());

        // Create the Produit
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProduitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(produitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Produit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProduit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        produit.setId(UUID.randomUUID());

        // Create the Produit
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProduitMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(produitDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Produit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProduit() throws Exception {
        // Initialize the database
        insertedProduit = produitRepository.saveAndFlush(produit);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the produit
        restProduitMockMvc
            .perform(delete(ENTITY_API_URL_ID, produit.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return produitRepository.count();
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

    protected Produit getPersistedProduit(Produit produit) {
        return produitRepository.findById(produit.getId()).orElseThrow();
    }

    protected void assertPersistedProduitToMatchAllProperties(Produit expectedProduit) {
        assertProduitAllPropertiesEquals(expectedProduit, getPersistedProduit(expectedProduit));
    }

    protected void assertPersistedProduitToMatchUpdatableProperties(Produit expectedProduit) {
        assertProduitAllUpdatablePropertiesEquals(expectedProduit, getPersistedProduit(expectedProduit));
    }
}
