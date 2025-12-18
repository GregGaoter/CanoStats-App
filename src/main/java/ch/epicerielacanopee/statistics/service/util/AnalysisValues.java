package ch.epicerielacanopee.statistics.service.util;

public class AnalysisValues {

  private double percentage;
  private double quantity;
  private float availableStock;
  private int nbDeliveries;
  private int nbSales;
  private int nbLosses;
  private int nbInventories;

  public AnalysisValues(double percentage, double quantity, float availableStock, int nbDeliveries, int nbSales,
      int nbLosses, int nbInventories) {
    this.percentage = percentage;
    this.quantity = quantity;
    this.availableStock = availableStock;
    this.nbDeliveries = nbDeliveries;
    this.nbSales = nbSales;
    this.nbLosses = nbLosses;
    this.nbInventories = nbInventories;
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
}
