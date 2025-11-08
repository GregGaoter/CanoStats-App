package ch.epicerielacanopee.statistics.service.util;

public class SoldValues {

  private double soldPercentage;
  private double soldQuantity;

  public SoldValues(double soldPercentage, double soldQuantity) {
    this.soldPercentage = soldPercentage;
    this.soldQuantity = soldQuantity;
  }

  public double getSoldPercentage() {
    return soldPercentage;
  }

  public void setSoldPercentage(double soldPercentage) {
    this.soldPercentage = soldPercentage;
  }

  public double getSoldQuantity() {
    return soldQuantity;
  }

  public void setSoldQuantity(double soldQuantity) {
    this.soldQuantity = soldQuantity;
  }
}
