package ch.epicerielacanopee.statistics.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * A Produit.
 */
@Entity
@Table(name = "produit")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Produit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "epicerio_id")
    private Integer epicerioId;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @NotNull
    @Column(name = "last_updated_date", nullable = false)
    private Instant lastUpdatedDate;

    @NotNull
    @Column(name = "imported_date", nullable = false)
    private Instant importedDate;

    @Column(name = "nom")
    private String nom;

    @Column(name = "code")
    private String code;

    @Column(name = "disponible")
    private String disponible;

    @Column(name = "prix_fournisseur")
    private Float prixFournisseur;

    @Column(name = "ht_ttc")
    private String htTtc;

    @Column(name = "taux_tva")
    private Float tauxTva;

    @Column(name = "marge_profit")
    private Float margeProfit;

    @Column(name = "prix_vente")
    private Float prixVente;

    @Column(name = "vendu")
    private String vendu;

    @Column(name = "quantite_par_piece")
    private Float quantiteParPiece;

    @Column(name = "unite")
    private String unite;

    @Column(name = "prix_par_unite")
    private String prixParUnite;

    @Lob
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "remarques_internes")
    private String remarquesInternes;

    @Column(name = "fournisseur")
    private String fournisseur;

    @Column(name = "ref_fournisseur")
    private String refFournisseur;

    @Column(name = "stock")
    private Float stock;

    @Column(name = "commandes_clients")
    private Float commandesClients;

    @Column(name = "derniere_verification_date")
    private LocalDate derniereVerificationDate;

    @Column(name = "derniere_livraison_date")
    private LocalDate derniereLivraisonDate;

    @Column(name = "achat_fournisseur")
    private String achatFournisseur;

    @Column(name = "dernier_achat_date")
    private LocalDate dernierAchatDate;

    @Column(name = "dernier_achat_quantite")
    private Float dernierAchatQuantite;

    @Column(name = "stats_livraison")
    private Float statsLivraison;

    @Column(name = "stats_perte")
    private Float statsPerte;

    @Column(name = "stats_vente")
    private Float statsVente;

    @Column(name = "stats_vente_speciale")
    private Float statsVenteSpeciale;

    @Lob
    @Column(name = "tags")
    private String tags;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Produit id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getEpicerioId() {
        return this.epicerioId;
    }

    public Produit epicerioId(Integer epicerioId) {
        this.setEpicerioId(epicerioId);
        return this;
    }

    public void setEpicerioId(Integer epicerioId) {
        this.epicerioId = epicerioId;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Produit createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastUpdatedDate() {
        return this.lastUpdatedDate;
    }

    public Produit lastUpdatedDate(Instant lastUpdatedDate) {
        this.setLastUpdatedDate(lastUpdatedDate);
        return this;
    }

    public void setLastUpdatedDate(Instant lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Instant getImportedDate() {
        return this.importedDate;
    }

    public Produit importedDate(Instant importedDate) {
        this.setImportedDate(importedDate);
        return this;
    }

    public void setImportedDate(Instant importedDate) {
        this.importedDate = importedDate;
    }

    public String getNom() {
        return this.nom;
    }

    public Produit nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return this.code;
    }

    public Produit code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisponible() {
        return this.disponible;
    }

    public Produit disponible(String disponible) {
        this.setDisponible(disponible);
        return this;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

    public Float getPrixFournisseur() {
        return this.prixFournisseur;
    }

    public Produit prixFournisseur(Float prixFournisseur) {
        this.setPrixFournisseur(prixFournisseur);
        return this;
    }

    public void setPrixFournisseur(Float prixFournisseur) {
        this.prixFournisseur = prixFournisseur;
    }

    public String getHtTtc() {
        return this.htTtc;
    }

    public Produit htTtc(String htTtc) {
        this.setHtTtc(htTtc);
        return this;
    }

    public void setHtTtc(String htTtc) {
        this.htTtc = htTtc;
    }

    public Float getTauxTva() {
        return this.tauxTva;
    }

    public Produit tauxTva(Float tauxTva) {
        this.setTauxTva(tauxTva);
        return this;
    }

    public void setTauxTva(Float tauxTva) {
        this.tauxTva = tauxTva;
    }

    public Float getMargeProfit() {
        return this.margeProfit;
    }

    public Produit margeProfit(Float margeProfit) {
        this.setMargeProfit(margeProfit);
        return this;
    }

    public void setMargeProfit(Float margeProfit) {
        this.margeProfit = margeProfit;
    }

    public Float getPrixVente() {
        return this.prixVente;
    }

    public Produit prixVente(Float prixVente) {
        this.setPrixVente(prixVente);
        return this;
    }

    public void setPrixVente(Float prixVente) {
        this.prixVente = prixVente;
    }

    public String getVendu() {
        return this.vendu;
    }

    public Produit vendu(String vendu) {
        this.setVendu(vendu);
        return this;
    }

    public void setVendu(String vendu) {
        this.vendu = vendu;
    }

    public Float getQuantiteParPiece() {
        return this.quantiteParPiece;
    }

    public Produit quantiteParPiece(Float quantiteParPiece) {
        this.setQuantiteParPiece(quantiteParPiece);
        return this;
    }

    public void setQuantiteParPiece(Float quantiteParPiece) {
        this.quantiteParPiece = quantiteParPiece;
    }

    public String getUnite() {
        return this.unite;
    }

    public Produit unite(String unite) {
        this.setUnite(unite);
        return this;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public String getPrixParUnite() {
        return this.prixParUnite;
    }

    public Produit prixParUnite(String prixParUnite) {
        this.setPrixParUnite(prixParUnite);
        return this;
    }

    public void setPrixParUnite(String prixParUnite) {
        this.prixParUnite = prixParUnite;
    }

    public String getDescription() {
        return this.description;
    }

    public Produit description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemarquesInternes() {
        return this.remarquesInternes;
    }

    public Produit remarquesInternes(String remarquesInternes) {
        this.setRemarquesInternes(remarquesInternes);
        return this;
    }

    public void setRemarquesInternes(String remarquesInternes) {
        this.remarquesInternes = remarquesInternes;
    }

    public String getFournisseur() {
        return this.fournisseur;
    }

    public Produit fournisseur(String fournisseur) {
        this.setFournisseur(fournisseur);
        return this;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public String getRefFournisseur() {
        return this.refFournisseur;
    }

    public Produit refFournisseur(String refFournisseur) {
        this.setRefFournisseur(refFournisseur);
        return this;
    }

    public void setRefFournisseur(String refFournisseur) {
        this.refFournisseur = refFournisseur;
    }

    public Float getStock() {
        return this.stock;
    }

    public Produit stock(Float stock) {
        this.setStock(stock);
        return this;
    }

    public void setStock(Float stock) {
        this.stock = stock;
    }

    public Float getCommandesClients() {
        return this.commandesClients;
    }

    public Produit commandesClients(Float commandesClients) {
        this.setCommandesClients(commandesClients);
        return this;
    }

    public void setCommandesClients(Float commandesClients) {
        this.commandesClients = commandesClients;
    }

    public LocalDate getDerniereVerificationDate() {
        return this.derniereVerificationDate;
    }

    public Produit derniereVerificationDate(LocalDate derniereVerificationDate) {
        this.setDerniereVerificationDate(derniereVerificationDate);
        return this;
    }

    public void setDerniereVerificationDate(LocalDate derniereVerificationDate) {
        this.derniereVerificationDate = derniereVerificationDate;
    }

    public LocalDate getDerniereLivraisonDate() {
        return this.derniereLivraisonDate;
    }

    public Produit derniereLivraisonDate(LocalDate derniereLivraisonDate) {
        this.setDerniereLivraisonDate(derniereLivraisonDate);
        return this;
    }

    public void setDerniereLivraisonDate(LocalDate derniereLivraisonDate) {
        this.derniereLivraisonDate = derniereLivraisonDate;
    }

    public String getAchatFournisseur() {
        return this.achatFournisseur;
    }

    public Produit achatFournisseur(String achatFournisseur) {
        this.setAchatFournisseur(achatFournisseur);
        return this;
    }

    public void setAchatFournisseur(String achatFournisseur) {
        this.achatFournisseur = achatFournisseur;
    }

    public LocalDate getDernierAchatDate() {
        return this.dernierAchatDate;
    }

    public Produit dernierAchatDate(LocalDate dernierAchatDate) {
        this.setDernierAchatDate(dernierAchatDate);
        return this;
    }

    public void setDernierAchatDate(LocalDate dernierAchatDate) {
        this.dernierAchatDate = dernierAchatDate;
    }

    public Float getDernierAchatQuantite() {
        return this.dernierAchatQuantite;
    }

    public Produit dernierAchatQuantite(Float dernierAchatQuantite) {
        this.setDernierAchatQuantite(dernierAchatQuantite);
        return this;
    }

    public void setDernierAchatQuantite(Float dernierAchatQuantite) {
        this.dernierAchatQuantite = dernierAchatQuantite;
    }

    public Float getStatsLivraison() {
        return this.statsLivraison;
    }

    public Produit statsLivraison(Float statsLivraison) {
        this.setStatsLivraison(statsLivraison);
        return this;
    }

    public void setStatsLivraison(Float statsLivraison) {
        this.statsLivraison = statsLivraison;
    }

    public Float getStatsPerte() {
        return this.statsPerte;
    }

    public Produit statsPerte(Float statsPerte) {
        this.setStatsPerte(statsPerte);
        return this;
    }

    public void setStatsPerte(Float statsPerte) {
        this.statsPerte = statsPerte;
    }

    public Float getStatsVente() {
        return this.statsVente;
    }

    public Produit statsVente(Float statsVente) {
        this.setStatsVente(statsVente);
        return this;
    }

    public void setStatsVente(Float statsVente) {
        this.statsVente = statsVente;
    }

    public Float getStatsVenteSpeciale() {
        return this.statsVenteSpeciale;
    }

    public Produit statsVenteSpeciale(Float statsVenteSpeciale) {
        this.setStatsVenteSpeciale(statsVenteSpeciale);
        return this;
    }

    public void setStatsVenteSpeciale(Float statsVenteSpeciale) {
        this.statsVenteSpeciale = statsVenteSpeciale;
    }

    public String getTags() {
        return this.tags;
    }

    public Produit tags(String tags) {
        this.setTags(tags);
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produit)) {
            return false;
        }
        return getId() != null && getId().equals(((Produit) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Produit{" +
            "id=" + getId() +
            ", epicerioId=" + getEpicerioId() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", importedDate='" + getImportedDate() + "'" +
            ", nom='" + getNom() + "'" +
            ", code='" + getCode() + "'" +
            ", disponible='" + getDisponible() + "'" +
            ", prixFournisseur=" + getPrixFournisseur() +
            ", htTtc='" + getHtTtc() + "'" +
            ", tauxTva=" + getTauxTva() +
            ", margeProfit=" + getMargeProfit() +
            ", prixVente=" + getPrixVente() +
            ", vendu='" + getVendu() + "'" +
            ", quantiteParPiece=" + getQuantiteParPiece() +
            ", unite='" + getUnite() + "'" +
            ", prixParUnite='" + getPrixParUnite() + "'" +
            ", description='" + getDescription() + "'" +
            ", remarquesInternes='" + getRemarquesInternes() + "'" +
            ", fournisseur='" + getFournisseur() + "'" +
            ", refFournisseur='" + getRefFournisseur() + "'" +
            ", stock=" + getStock() +
            ", commandesClients=" + getCommandesClients() +
            ", derniereVerificationDate='" + getDerniereVerificationDate() + "'" +
            ", derniereLivraisonDate='" + getDerniereLivraisonDate() + "'" +
            ", achatFournisseur='" + getAchatFournisseur() + "'" +
            ", dernierAchatDate='" + getDernierAchatDate() + "'" +
            ", dernierAchatQuantite=" + getDernierAchatQuantite() +
            ", statsLivraison=" + getStatsLivraison() +
            ", statsPerte=" + getStatsPerte() +
            ", statsVente=" + getStatsVente() +
            ", statsVenteSpeciale=" + getStatsVenteSpeciale() +
            ", tags='" + getTags() + "'" +
            "}";
    }
}
