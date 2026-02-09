package ch.epicerielacanopee.statistics.service.util;

public class LowestSalesResult {

  private String productCode;
  private String product;
  private float percentage;
  private float quantity;
  private String unit;

  public LowestSalesResult(String productCode, String product, float percentage, float quantity, String unit) {
    this.productCode = productCode;
    this.product = product;
    this.percentage = percentage;
    this.quantity = quantity;
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

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }
}
