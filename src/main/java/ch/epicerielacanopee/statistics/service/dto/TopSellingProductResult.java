package ch.epicerielacanopee.statistics.service.dto;

public class TopSellingProductResult {

    private String productCode;
    private String product;
    private float soldPercentage;
    private float soldQuantity;

    public TopSellingProductResult(String productCode, String product, float soldPercentage, float soldQuantity) {
        this.productCode = productCode;
        this.product = product;
        this.soldPercentage = soldPercentage;
        this.soldQuantity = soldQuantity;
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
}
