package ch.epicerielacanopee.statistics.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * A MouvementsStock.
 */
@Entity
@Table(name = "mouvements_stock")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MouvementsStock implements Serializable {

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

    @Column(name = "date")
    private Instant date;

    @Column(name = "utilisateur")
    private String utilisateur;

    @Column(name = "type")
    private String type;

    @Column(name = "epicerio_mouvement")
    private Float epicerioMouvement;

    @Column(name = "mouvement")
    private Float mouvement;

    @Column(name = "solde")
    private Float solde;

    @Column(name = "vente")
    private String vente;

    @Column(name = "code_produit")
    private String codeProduit;

    @Column(name = "produit")
    private String produit;

    @Column(name = "responsable_produit")
    private String responsableProduit;

    @Column(name = "fournisseur_produit")
    private String fournisseurProduit;

    @Column(name = "code_fournisseur")
    private String codeFournisseur;

    @Column(name = "reduction")
    private Float reduction;

    @Column(name = "ponderation")
    private Float ponderation;

    @Column(name = "vente_chf")
    private Float venteChf;

    @Column(name = "valeur_chf")
    private Float valeurChf;

    @Lob
    @Column(name = "remarques")
    private String remarques;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    public UUID getId() {
        return this.id;
    }

    public MouvementsStock id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getEpicerioId() {
        return this.epicerioId;
    }

    public MouvementsStock epicerioId(Integer epicerioId) {
        this.setEpicerioId(epicerioId);
        return this;
    }

    public void setEpicerioId(Integer epicerioId) {
        this.epicerioId = epicerioId;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public MouvementsStock createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastUpdatedDate() {
        return this.lastUpdatedDate;
    }

    public MouvementsStock lastUpdatedDate(Instant lastUpdatedDate) {
        this.setLastUpdatedDate(lastUpdatedDate);
        return this;
    }

    public void setLastUpdatedDate(Instant lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Instant getImportedDate() {
        return this.importedDate;
    }

    public MouvementsStock importedDate(Instant importedDate) {
        this.setImportedDate(importedDate);
        return this;
    }

    public void setImportedDate(Instant importedDate) {
        this.importedDate = importedDate;
    }

    public Instant getDate() {
        return this.date;
    }

    public MouvementsStock date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getUtilisateur() {
        return this.utilisateur;
    }

    public MouvementsStock utilisateur(String utilisateur) {
        this.setUtilisateur(utilisateur);
        return this;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getType() {
        return this.type;
    }

    public MouvementsStock type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getEpicerioMouvement() {
        return this.epicerioMouvement;
    }

    public MouvementsStock epicerioMouvement(Float epicerioMouvement) {
        this.setEpicerioMouvement(epicerioMouvement);
        return this;
    }

    public void setEpicerioMouvement(Float epicerioMouvement) {
        this.epicerioMouvement = epicerioMouvement;
    }

    public Float getMouvement() {
        return this.mouvement;
    }

    public MouvementsStock mouvement(Float mouvement) {
        this.setMouvement(mouvement);
        return this;
    }

    public void setMouvement(Float mouvement) {
        this.mouvement = mouvement;
    }

    public Float getSolde() {
        return this.solde;
    }

    public MouvementsStock solde(Float solde) {
        this.setSolde(solde);
        return this;
    }

    public void setSolde(Float solde) {
        this.solde = solde;
    }

    public String getVente() {
        return this.vente;
    }

    public MouvementsStock vente(String vente) {
        this.setVente(vente);
        return this;
    }

    public void setVente(String vente) {
        this.vente = vente;
    }

    public String getCodeProduit() {
        return this.codeProduit;
    }

    public MouvementsStock codeProduit(String codeProduit) {
        this.setCodeProduit(codeProduit);
        return this;
    }

    public void setCodeProduit(String codeProduit) {
        this.codeProduit = codeProduit;
    }

    public String getProduit() {
        return this.produit;
    }

    public MouvementsStock produit(String produit) {
        this.setProduit(produit);
        return this;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    public String getResponsableProduit() {
        return this.responsableProduit;
    }

    public MouvementsStock responsableProduit(String responsableProduit) {
        this.setResponsableProduit(responsableProduit);
        return this;
    }

    public void setResponsableProduit(String responsableProduit) {
        this.responsableProduit = responsableProduit;
    }

    public String getFournisseurProduit() {
        return this.fournisseurProduit;
    }

    public MouvementsStock fournisseurProduit(String fournisseurProduit) {
        this.setFournisseurProduit(fournisseurProduit);
        return this;
    }

    public void setFournisseurProduit(String fournisseurProduit) {
        this.fournisseurProduit = fournisseurProduit;
    }

    public String getCodeFournisseur() {
        return this.codeFournisseur;
    }

    public MouvementsStock codeFournisseur(String codeFournisseur) {
        this.setCodeFournisseur(codeFournisseur);
        return this;
    }

    public void setCodeFournisseur(String codeFournisseur) {
        this.codeFournisseur = codeFournisseur;
    }

    public Float getReduction() {
        return this.reduction;
    }

    public MouvementsStock reduction(Float reduction) {
        this.setReduction(reduction);
        return this;
    }

    public void setReduction(Float reduction) {
        this.reduction = reduction;
    }

    public Float getPonderation() {
        return this.ponderation;
    }

    public MouvementsStock ponderation(Float ponderation) {
        this.setPonderation(ponderation);
        return this;
    }

    public void setPonderation(Float ponderation) {
        this.ponderation = ponderation;
    }

    public Float getVenteChf() {
        return this.venteChf;
    }

    public MouvementsStock venteChf(Float venteChf) {
        this.setVenteChf(venteChf);
        return this;
    }

    public void setVenteChf(Float venteChf) {
        this.venteChf = venteChf;
    }

    public Float getValeurChf() {
        return this.valeurChf;
    }

    public MouvementsStock valeurChf(Float valeurChf) {
        this.setValeurChf(valeurChf);
        return this;
    }

    public void setValeurChf(Float valeurChf) {
        this.valeurChf = valeurChf;
    }

    public String getRemarques() {
        return this.remarques;
    }

    public MouvementsStock remarques(String remarques) {
        this.setRemarques(remarques);
        return this;
    }

    public void setRemarques(String remarques) {
        this.remarques = remarques;
    }

    public Boolean getActive() {
        return this.active;
    }

    public MouvementsStock active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MouvementsStock)) {
            return false;
        }
        return getId() != null && getId().equals(((MouvementsStock) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MouvementsStock{" +
            "id=" + getId() +
            ", epicerioId=" + getEpicerioId() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", importedDate='" + getImportedDate() + "'" +
            ", date='" + getDate() + "'" +
            ", utilisateur='" + getUtilisateur() + "'" +
            ", type='" + getType() + "'" +
            ", epicerioMouvement=" + getEpicerioMouvement() +
            ", mouvement=" + getMouvement() +
            ", solde=" + getSolde() +
            ", vente='" + getVente() + "'" +
            ", codeProduit='" + getCodeProduit() + "'" +
            ", produit='" + getProduit() + "'" +
            ", responsableProduit='" + getResponsableProduit() + "'" +
            ", fournisseurProduit='" + getFournisseurProduit() + "'" +
            ", codeFournisseur='" + getCodeFournisseur() + "'" +
            ", reduction=" + getReduction() +
            ", ponderation=" + getPonderation() +
            ", venteChf=" + getVenteChf() +
            ", valeurChf=" + getValeurChf() +
            ", remarques='" + getRemarques() + "'" +
            ", active='" + getActive() + "'" +
            "}";
    }
}
