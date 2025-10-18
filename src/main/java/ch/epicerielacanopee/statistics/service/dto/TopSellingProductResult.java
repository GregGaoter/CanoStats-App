package ch.epicerielacanopee.statistics.service.dto;

public class TopSellingProductResult {

    private String productCode;
    private String product;
    private float soldPercentage;
    private float soldQuantity;
    private float availableStock;

    public TopSellingProductResult(String productCode, String product, float soldPercentage, float soldQuantity, float availableStock) {
        this.productCode = productCode;
        this.product = product;
        this.soldPercentage = soldPercentage;
        this.soldQuantity = soldQuantity;
        this.availableStock = availableStock;
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

    public float getAvailableStock() {
        return availableStock;
    }
}
