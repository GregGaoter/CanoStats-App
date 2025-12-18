package ch.epicerielacanopee.statistics.service.util;

public class MonthlyAnalysisResult {

    private String productCode;
    private String product;
    private float percentage;
    private float quantity;
    private float availableStock;
    private int nbDeliveries;
    private int nbSales;
    private int nbLosses;
    private int nbInventories;
    private String unit;

    public MonthlyAnalysisResult(String productCode, String product, float percentage, float quantity,
            float availableStock, int nbDeliveries, int nbSales, int nbLosses, int nbInventories, String unit) {
        this.productCode = productCode;
        this.product = product;
        this.percentage = percentage;
        this.quantity = quantity;
        this.availableStock = availableStock;
        this.nbDeliveries = nbDeliveries;
        this.nbSales = nbSales;
        this.nbLosses = nbLosses;
        this.nbInventories = nbInventories;
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

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(float availableStock) {
        this.availableStock = availableStock;
    }

    public int getNbDeliveries() {
        return nbDeliveries;
    }

    public void setNbDeliveries(int nbDeliveries) {
        this.nbDeliveries = nbDeliveries;
    }

    public int getNbSales() {
        return nbSales;
    }

    public void setNbSales(int nbSales) {
        this.nbSales = nbSales;
    }

    public int getNbLosses() {
        return nbLosses;
    }

    public void setNbLosses(int nbLosses) {
        this.nbLosses = nbLosses;
    }

    public int getNbInventories() {
        return nbInventories;
    }

    public void setNbInventories(int nbInventories) {
        this.nbInventories = nbInventories;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
