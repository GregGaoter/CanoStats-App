export interface TopSellingProductResult {
  productCode?: string;
  product?: string;
  soldPercentageAverage?: number | null;
  soldPercentageStandardDeviation?: number | null;
  soldQuantityAverage?: number | null;
  soldQuantityStandardDeviation?: number | null;
  saleType?: string;
}
