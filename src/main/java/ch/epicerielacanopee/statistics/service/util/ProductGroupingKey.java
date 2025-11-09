package ch.epicerielacanopee.statistics.service.util;

import java.util.Objects;

public class ProductGroupingKey {

    private String codeProduit;
    private String produit;
    private String saleType;

    public ProductGroupingKey(String codeProduit, String produit, String saleType) {
        this.codeProduit = codeProduit;
        this.produit = produit;
        this.saleType = saleType;
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

    public String getSaleType() {
        return saleType;
    }

    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductGroupingKey)) return false;
        ProductGroupingKey that = (ProductGroupingKey) o;
        return codeProduit.equals(that.codeProduit) && produit.equals(that.produit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeProduit, produit);
    }

    @Override
    public String toString() {
        return codeProduit.concat(" - ").concat(produit);
    }
}
