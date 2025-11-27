package ch.epicerielacanopee.statistics.service.dto;

public class SellingProductResult {

    private String productCode;
    private String product;
    private float soldPercentageAverage;
    private float soldPercentageStandardDeviation;
    private float soldQuantityAverage;
    private float soldQuantityStandardDeviation;
    private String saleType;

    public SellingProductResult(
            String productCode,
            String product,
            float soldPercentageAverage,
            float soldPercentageStandardDeviation,
            float soldQuantityAverage,
            float soldQuantityStandardDeviation,
            String saleType) {
        this.productCode = productCode;
        this.product = product;
        this.soldPercentageAverage = soldPercentageAverage;
        this.soldPercentageStandardDeviation = soldPercentageStandardDeviation;
        this.soldQuantityAverage = soldQuantityAverage;
        this.soldQuantityStandardDeviation = soldQuantityStandardDeviation;
        this.saleType = saleType;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public float getSoldPercentageAverage() {
        return soldPercentageAverage;
    }

    public void setSoldPercentageAverage(float soldPercentageAverage) {
        this.soldPercentageAverage = soldPercentageAverage;
    }

    public float getSoldPercentageStandardDeviation() {
        return soldPercentageStandardDeviation;
    }

    public void setSoldPercentageStandardDeviation(float soldPercentageStandardDeviation) {
        this.soldPercentageStandardDeviation = soldPercentageStandardDeviation;
    }

    public float getSoldQuantityAverage() {
        return soldQuantityAverage;
    }

    public void setSoldQuantityAverage(float soldQuantityAverage) {
        this.soldQuantityAverage = soldQuantityAverage;
    }

    public float getSoldQuantityStandardDeviation() {
        return soldQuantityStandardDeviation;
    }

    public void setSoldQuantityStandardDeviation(float soldQuantityStandardDeviation) {
        this.soldQuantityStandardDeviation = soldQuantityStandardDeviation;
    }

    public String getSaleType() {
        return saleType;
    }

    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }
}
