package ch.epicerielacanopee.statistics.service.dto;

public class MonthlyAnalysisResult {

    private String productCode;
    private String product;
    private float percentageAverage;
    private float percentageStandardDeviation;
    private float quantityAverage;
    private float quantityStandardDeviation;
    private String unit;

    public MonthlyAnalysisResult(String productCode, String product, float percentageAverage,
            float percentageStandardDeviation, float quantityAverage, float quantityStandardDeviation, String unit) {
        this.productCode = productCode;
        this.product = product;
        this.percentageAverage = percentageAverage;
        this.percentageStandardDeviation = percentageStandardDeviation;
        this.quantityAverage = quantityAverage;
        this.quantityStandardDeviation = quantityStandardDeviation;
        this.unit = unit;
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

    public float getPercentageAverage() {
        return percentageAverage;
    }

    public void setPercentageAverage(float percentageAverage) {
        this.percentageAverage = percentageAverage;
    }

    public float getPercentageStandardDeviation() {
        return percentageStandardDeviation;
    }

    public void setPercentageStandardDeviation(float percentageStandardDeviation) {
        this.percentageStandardDeviation = percentageStandardDeviation;
    }

    public float getQuantityAverage() {
        return quantityAverage;
    }

    public void setQuantityAverage(float quantityAverage) {
        this.quantityAverage = quantityAverage;
    }

    public float getQuantityStandardDeviation() {
        return quantityStandardDeviation;
    }

    public void setQuantityStandardDeviation(float quantityStandardDeviation) {
        this.quantityStandardDeviation = quantityStandardDeviation;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
