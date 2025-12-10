package ch.epicerielacanopee.statistics.service.util;

public class AnalysisValues {

  private double percentage;
  private double quantity;

  public AnalysisValues(double percentage, double quantity) {
    this.percentage = percentage;
    this.quantity = quantity;
  }

  public double getPercentage() {
    return percentage;
  }

  public void setPercentage(double percentage) {
    this.percentage = percentage;
  }

  public double getQuantity() {
    return quantity;
  }

  public void setQuantity(double quantity) {
    this.quantity = quantity;
  }
}
