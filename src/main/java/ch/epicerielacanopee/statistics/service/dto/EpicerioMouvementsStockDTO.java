package ch.epicerielacanopee.statistics.service.dto;

import java.time.Instant;

public class EpicerioMouvementsStockDTO {

    private int id;
    private Instant date;
    private String utilisateur;
    private String type;
    private float mouvement;
    private float solde;
    private String vente;
    private String codeProduit;
    private String produit;
    private String responsableProduit;
    private String fournisseurProduit;
    private String codeFournisseur;
    private float reduction;
    private float ponderation;
    private float venteChf;
    private float valeurChf;
    private String remarques;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public float getMouvement() {
        return mouvement;
    }

    public void setMouvement(float mouvement) {
        this.mouvement = mouvement;
    }

    public float getSolde() {
        return solde;
    }

    public void setSolde(float solde) {
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

    public float getReduction() {
        return reduction;
    }

    public void setReduction(float reduction) {
        this.reduction = reduction;
    }

    public float getPonderation() {
        return ponderation;
    }

    public void setPonderation(float ponderation) {
        this.ponderation = ponderation;
    }

    public float getVenteChf() {
        return venteChf;
    }

    public void setVenteChf(float venteChf) {
        this.venteChf = venteChf;
    }

    public float getValeurChf() {
        return valeurChf;
    }

    public void setValeurChf(float valeurChf) {
        this.valeurChf = valeurChf;
    }

    public String getRemarques() {
        return remarques;
    }

    public void setRemarques(String remarques) {
        this.remarques = remarques;
    }
}
