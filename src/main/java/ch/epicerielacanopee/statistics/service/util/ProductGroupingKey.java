package ch.epicerielacanopee.statistics.service.util;

import java.util.Objects;

public class ProductGroupingKey {

    private String codeProduit;
    private String produit;

    public ProductGroupingKey(String codeProduit, String produit) {
        this.codeProduit = codeProduit;
        this.produit = produit;
    }

    public String getCodeProduit() {
        return codeProduit;
    }

    public String getProduit() {
        return produit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductGroupingKey)) return false;
        ProductGroupingKey that = (ProductGroupingKey) o;
        return Objects.equals(codeProduit, that.codeProduit) && Objects.equals(produit, that.produit);
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
