package ch.epicerielacanopee.statistics.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link ch.epicerielacanopee.statistics.domain.Produit} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProduitDTO implements Serializable {

    private UUID id;

    private Integer epicerioId;

    @NotNull
    private Instant createdDate;

    @NotNull
    private Instant lastUpdatedDate;

    @NotNull
    private Instant importedDate;

    private String nom;

    private String code;

    private String disponible;

    private Float prixFournisseur;

    private String htTtc;

    private Float tauxTva;

    private Float margeProfit;

    private Float prixVente;

    private String vendu;

    private Float quantiteParPiece;

    private String unite;

    private String prixParUnite;

    @Lob
    private String description;

    @Lob
    private String remarquesInternes;

    private String fournisseur;

    private String refFournisseur;

    private Float stock;

    private Float commandesClients;

    private LocalDate derniereVerificationDate;

    private LocalDate derniereLivraisonDate;

    private String achatFournisseur;

    private LocalDate dernierAchatDate;

    private Float dernierAchatQuantite;

    private Float statsLivraison;

    private Float statsPerte;

    private Float statsVente;

    private Float statsVenteSpeciale;

    @Lob
    private String tags;

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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

    public Float getPrixFournisseur() {
        return prixFournisseur;
    }

    public void setPrixFournisseur(Float prixFournisseur) {
        this.prixFournisseur = prixFournisseur;
    }

    public String getHtTtc() {
        return htTtc;
    }

    public void setHtTtc(String htTtc) {
        this.htTtc = htTtc;
    }

    public Float getTauxTva() {
        return tauxTva;
    }

    public void setTauxTva(Float tauxTva) {
        this.tauxTva = tauxTva;
    }

    public Float getMargeProfit() {
        return margeProfit;
    }

    public void setMargeProfit(Float margeProfit) {
        this.margeProfit = margeProfit;
    }

    public Float getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(Float prixVente) {
        this.prixVente = prixVente;
    }

    public String getVendu() {
        return vendu;
    }

    public void setVendu(String vendu) {
        this.vendu = vendu;
    }

    public Float getQuantiteParPiece() {
        return quantiteParPiece;
    }

    public void setQuantiteParPiece(Float quantiteParPiece) {
        this.quantiteParPiece = quantiteParPiece;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public String getPrixParUnite() {
        return prixParUnite;
    }

    public void setPrixParUnite(String prixParUnite) {
        this.prixParUnite = prixParUnite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemarquesInternes() {
        return remarquesInternes;
    }

    public void setRemarquesInternes(String remarquesInternes) {
        this.remarquesInternes = remarquesInternes;
    }

    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public String getRefFournisseur() {
        return refFournisseur;
    }

    public void setRefFournisseur(String refFournisseur) {
        this.refFournisseur = refFournisseur;
    }

    public Float getStock() {
        return stock;
    }

    public void setStock(Float stock) {
        this.stock = stock;
    }

    public Float getCommandesClients() {
        return commandesClients;
    }

    public void setCommandesClients(Float commandesClients) {
        this.commandesClients = commandesClients;
    }

    public LocalDate getDerniereVerificationDate() {
        return derniereVerificationDate;
    }

    public void setDerniereVerificationDate(LocalDate derniereVerificationDate) {
        this.derniereVerificationDate = derniereVerificationDate;
    }

    public LocalDate getDerniereLivraisonDate() {
        return derniereLivraisonDate;
    }

    public void setDerniereLivraisonDate(LocalDate derniereLivraisonDate) {
        this.derniereLivraisonDate = derniereLivraisonDate;
    }

    public String getAchatFournisseur() {
        return achatFournisseur;
    }

    public void setAchatFournisseur(String achatFournisseur) {
        this.achatFournisseur = achatFournisseur;
    }

    public LocalDate getDernierAchatDate() {
        return dernierAchatDate;
    }

    public void setDernierAchatDate(LocalDate dernierAchatDate) {
        this.dernierAchatDate = dernierAchatDate;
    }

    public Float getDernierAchatQuantite() {
        return dernierAchatQuantite;
    }

    public void setDernierAchatQuantite(Float dernierAchatQuantite) {
        this.dernierAchatQuantite = dernierAchatQuantite;
    }

    public Float getStatsLivraison() {
        return statsLivraison;
    }

    public void setStatsLivraison(Float statsLivraison) {
        this.statsLivraison = statsLivraison;
    }

    public Float getStatsPerte() {
        return statsPerte;
    }

    public void setStatsPerte(Float statsPerte) {
        this.statsPerte = statsPerte;
    }

    public Float getStatsVente() {
        return statsVente;
    }

    public void setStatsVente(Float statsVente) {
        this.statsVente = statsVente;
    }

    public Float getStatsVenteSpeciale() {
        return statsVenteSpeciale;
    }

    public void setStatsVenteSpeciale(Float statsVenteSpeciale) {
        this.statsVenteSpeciale = statsVenteSpeciale;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProduitDTO)) {
            return false;
        }

        ProduitDTO produitDTO = (ProduitDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, produitDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitDTO{" +
            "id='" + getId() + "'" +
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
