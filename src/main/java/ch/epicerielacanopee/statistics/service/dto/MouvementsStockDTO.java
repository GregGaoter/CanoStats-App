package ch.epicerielacanopee.statistics.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link ch.epicerielacanopee.statistics.domain.MouvementsStock} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MouvementsStockDTO implements Serializable {

    private UUID id;

    private Integer epicerioId;

    @NotNull
    private Instant createdDate;

    @NotNull
    private Instant lastUpdatedDate;

    @NotNull
    private Instant importedDate;

    private Instant date;

    private String utilisateur;

    private String type;

    private Float epicerioMouvement;

    private Float mouvement;

    private Float solde;

    private String vente;

    private String codeProduit;

    private String produit;

    private String responsableProduit;

    private String fournisseurProduit;

    private String codeFournisseur;

    private Float reduction;

    private Float ponderation;

    private Float venteChf;

    private Float valeurChf;

    @Lob
    private String remarques;

    @NotNull
    private Boolean active;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getEpicerioId() {
        return epicerioId;
    }

    public void setEpicerioId(Integer epicerioId) {
        this.epicerioId = epicerioId;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Instant lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Instant getImportedDate() {
        return importedDate;
    }

    public void setImportedDate(Instant importedDate) {
        this.importedDate = importedDate;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getEpicerioMouvement() {
        return epicerioMouvement;
    }

    public void setEpicerioMouvement(Float epicerioMouvement) {
        this.epicerioMouvement = epicerioMouvement;
    }

    public Float getMouvement() {
        return mouvement;
    }

    public void setMouvement(Float mouvement) {
        this.mouvement = mouvement;
    }

    public Float getSolde() {
        return solde;
    }

    public void setSolde(Float solde) {
        this.solde = solde;
    }

    public String getVente() {
        return vente;
    }

    public void setVente(String vente) {
        this.vente = vente;
    }

    public String getCodeProduit() {
        return codeProduit;
    }

    public void setCodeProduit(String codeProduit) {
        this.codeProduit = codeProduit;
    }

    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    public String getResponsableProduit() {
        return responsableProduit;
    }

    public void setResponsableProduit(String responsableProduit) {
        this.responsableProduit = responsableProduit;
    }

    public String getFournisseurProduit() {
        return fournisseurProduit;
    }

    public void setFournisseurProduit(String fournisseurProduit) {
        this.fournisseurProduit = fournisseurProduit;
    }

    public String getCodeFournisseur() {
        return codeFournisseur;
    }

    public void setCodeFournisseur(String codeFournisseur) {
        this.codeFournisseur = codeFournisseur;
    }

    public Float getReduction() {
        return reduction;
    }

    public void setReduction(Float reduction) {
        this.reduction = reduction;
    }

    public Float getPonderation() {
        return ponderation;
    }

    public void setPonderation(Float ponderation) {
        this.ponderation = ponderation;
    }

    public Float getVenteChf() {
        return venteChf;
    }

    public void setVenteChf(Float venteChf) {
        this.venteChf = venteChf;
    }

    public Float getValeurChf() {
        return valeurChf;
    }

    public void setValeurChf(Float valeurChf) {
        this.valeurChf = valeurChf;
    }

    public String getRemarques() {
        return remarques;
    }

    public void setRemarques(String remarques) {
        this.remarques = remarques;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MouvementsStockDTO)) {
            return false;
        }

        MouvementsStockDTO mouvementsStockDTO = (MouvementsStockDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, mouvementsStockDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MouvementsStockDTO{" +
            "id='" + getId() + "'" +
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
