package ch.epicerielacanopee.statistics.service.dto;

import java.time.LocalDate;

public class EpicerioProduitDTO {

  private int id;
  private String nom;
  private String code;
  private String disponible;
  private float prixFournisseur;
  private String htTtc;
  private float tauxTva;
  private float margeProfit;
  private float prixVente;
  private String vendu;
  private float quantiteParPiece;
  private String unite;
  private String prixParUnite;
  private String description;
  private String remarquesInternes;
  private String fournisseur;
  private String refFournisseur;
  private float stock;
  private float commandesClients;
  private LocalDate derniereVerificationDate;
  private LocalDate derniereLivraisonDate;
  private String achatFournisseur;
  private LocalDate dernierAchatDate;
  private float dernierAchatQuantite;
  private float statsLivraison;
  private float statsPerte;
  private float statsVente;
  private float statsVenteSpeciale;
  private String tags;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public float getPrixFournisseur() {
    return prixFournisseur;
  }

  public void setPrixFournisseur(float prixFournisseur) {
    this.prixFournisseur = prixFournisseur;
  }

  public String getHtTtc() {
    return htTtc;
  }

  public void setHtTtc(String htTtc) {
    this.htTtc = htTtc;
  }

  public float getTauxTva() {
    return tauxTva;
  }

  public void setTauxTva(float tauxTva) {
    this.tauxTva = tauxTva;
  }

  public float getMargeProfit() {
    return margeProfit;
  }

  public void setMargeProfit(float margeProfit) {
    this.margeProfit = margeProfit;
  }

  public float getPrixVente() {
    return prixVente;
  }

  public void setPrixVente(float prixVente) {
    this.prixVente = prixVente;
  }

  public String getVendu() {
    return vendu;
  }

  public void setVendu(String vendu) {
    this.vendu = vendu;
  }

  public float getQuantiteParPiece() {
    return quantiteParPiece;
  }

  public void setQuantiteParPiece(float quantiteParPiece) {
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

  public float getStock() {
    return stock;
  }

  public void setStock(float stock) {
    this.stock = stock;
  }

  public float getCommandesClients() {
    return commandesClients;
  }

  public void setCommandesClients(float commandesClients) {
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

  public float getDernierAchatQuantite() {
    return dernierAchatQuantite;
  }

  public void setDernierAchatQuantite(float dernierAchatQuantite) {
    this.dernierAchatQuantite = dernierAchatQuantite;
  }

  public float getStatsLivraison() {
    return statsLivraison;
  }

  public void setStatsLivraison(float statsLivraison) {
    this.statsLivraison = statsLivraison;
  }

  public float getStatsPerte() {
    return statsPerte;
  }

  public void setStatsPerte(float statsPerte) {
    this.statsPerte = statsPerte;
  }

  public float getStatsVente() {
    return statsVente;
  }

  public void setStatsVente(float statsVente) {
    this.statsVente = statsVente;
  }

  public float getStatsVenteSpeciale() {
    return statsVenteSpeciale;
  }

  public void setStatsVenteSpeciale(float statsVenteSpeciale) {
    this.statsVenteSpeciale = statsVenteSpeciale;
  }

  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }
}
