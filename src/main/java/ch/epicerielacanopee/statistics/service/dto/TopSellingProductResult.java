package ch.epicerielacanopee.statistics.service.dto;

public class TopSellingProductResult {

    private String productCode;
    private String product;
    private float soldPercentage;
    private float soldQuantity;
    private String saleType;

    public TopSellingProductResult(String productCode, String product, float soldPercentage, float soldQuantity, String saleType) {
        this.productCode = productCode;
        this.product = product;
        this.soldPercentage = soldPercentage;
        this.soldQuantity = soldQuantity;
        this.saleType = saleType;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getProduct() {
        return product;
    }

    public float getSoldPercentage() {
        return soldPercentage;
    }

    public float getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(float soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public String getSaleType() {
        return saleType;
    }

    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }
}
