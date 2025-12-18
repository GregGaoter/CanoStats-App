package ch.epicerielacanopee.statistics.service.util;

public class StatisticalQuantities {
  private float mean;
  private float standardDeviation;

  public StatisticalQuantities(float mean, float standardDeviation) {
    this.mean = mean;
    this.standardDeviation = standardDeviation;
  }

  public float getMean() {
    return mean;
  }

  public void setMean(float mean) {
    this.mean = mean;
  }

  public float getStandardDeviation() {
    return standardDeviation;
  }

  public void setStandardDeviation(float standardDeviation) {
    this.standardDeviation = standardDeviation;
  }
}
