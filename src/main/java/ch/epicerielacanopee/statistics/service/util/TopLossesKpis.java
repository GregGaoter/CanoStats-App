package ch.epicerielacanopee.statistics.service.util;

public class TopLossesKpis {

  private String totalLosses;
  private String mostImpactedCategories;
  private String mostImpactedProduct;

  public TopLossesKpis(String totalLosses, String mostImpactedCategories, String mostImpactedProduct) {
    this.totalLosses = totalLosses;
    this.mostImpactedCategories = mostImpactedCategories;
    this.mostImpactedProduct = mostImpactedProduct;
  }

  public String getTotalLosses() {
    return totalLosses;
  }

  public void setTotalLosses(String totalLosses) {
    this.totalLosses = totalLosses;
  }

  public String getMostImpactedCategories() {
    return mostImpactedCategories;
  }

  public void setMostImpactedCategories(String mostImpactedCategories) {
    this.mostImpactedCategories = mostImpactedCategories;
  }

  public String getMostImpactedProduct() {
    return mostImpactedProduct;
  }

  public void setMostImpactedProduct(String mostImpactedProduct) {
    this.mostImpactedProduct = mostImpactedProduct;
  }
}
