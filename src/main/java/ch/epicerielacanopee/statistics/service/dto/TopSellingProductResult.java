package ch.epicerielacanopee.statistics.service.dto;

public class TopSellingProductResult {

    private String productCode;
    private float soldPercentage;
    private float soldQuantity;
    private float availableStock;

    public TopSellingProductResult(String productCode, float soldPercentage, float soldQuantity, float availableStock) {
        this.productCode = productCode;
        this.soldPercentage = soldPercentage;
        this.soldQuantity = soldQuantity;
        this.availableStock = availableStock;
    }

    public String getProductCode() {
        return productCode;
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
