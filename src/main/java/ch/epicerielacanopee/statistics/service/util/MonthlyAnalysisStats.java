package ch.epicerielacanopee.statistics.service.util;

public class MonthlyAnalysisStats {

  private String productCode;
  private String product;
  private StatisticalQuantities percentageStats;
  private StatisticalQuantities quantityStats;
  private StatisticalQuantities availableStockStats;
  private StatisticalQuantities nbDeliveriesStats;
  private StatisticalQuantities nbSalesStats;
  private StatisticalQuantities nbLossesStats;
  private StatisticalQuantities nbInventoriesStats;
  private String unit;

  public MonthlyAnalysisStats(String productCode, String product, StatisticalQuantities percentageStats,
      StatisticalQuantities quantityStats, StatisticalQuantities availableStockStats,
      StatisticalQuantities nbDeliveriesStats, StatisticalQuantities nbSalesStats, StatisticalQuantities nbLossesStats,
      StatisticalQuantities nbInventoriesStats, String unit) {
    this.productCode = productCode;
    this.product = product;
    this.percentageStats = percentageStats;
    this.quantityStats = quantityStats;
    this.availableStockStats = availableStockStats;
    this.nbDeliveriesStats = nbDeliveriesStats;
    this.nbSalesStats = nbSalesStats;
    this.nbLossesStats = nbLossesStats;
    this.nbInventoriesStats = nbInventoriesStats;
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

  public StatisticalQuantities getPercentageStats() {
    return percentageStats;
  }

  public void setPercentageStats(StatisticalQuantities percentageStats) {
    this.percentageStats = percentageStats;
  }

  public StatisticalQuantities getQuantityStats() {
    return quantityStats;
  }

  public void setQuantityStats(StatisticalQuantities quantityStats) {
    this.quantityStats = quantityStats;
  }

  public StatisticalQuantities getAvailableStockStats() {
    return availableStockStats;
  }

  public void setAvailableStockStats(StatisticalQuantities availableStockStats) {
    this.availableStockStats = availableStockStats;
  }

  public StatisticalQuantities getNbDeliveriesStats() {
    return nbDeliveriesStats;
  }

  public void setNbDeliveriesStats(StatisticalQuantities nbDeliveriesStats) {
    this.nbDeliveriesStats = nbDeliveriesStats;
  }

  public StatisticalQuantities getNbSalesStats() {
    return nbSalesStats;
  }

  public void setNbSalesStats(StatisticalQuantities nbSalesStats) {
    this.nbSalesStats = nbSalesStats;
  }

  public StatisticalQuantities getNbLossesStats() {
    return nbLossesStats;
  }

  public void setNbLossesStats(StatisticalQuantities nbLossesStats) {
    this.nbLossesStats = nbLossesStats;
  }

  public StatisticalQuantities getNbInventoriesStats() {
    return nbInventoriesStats;
  }

  public void setNbInventoriesStats(StatisticalQuantities nbInventoriesStats) {
    this.nbInventoriesStats = nbInventoriesStats;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }
}
