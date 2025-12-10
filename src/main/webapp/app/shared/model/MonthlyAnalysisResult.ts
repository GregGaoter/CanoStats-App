export interface MonthlyAnalysisResult {
  productCode?: string;
  product?: string;
  percentageAverage?: number | null;
  percentageStandardDeviation?: number | null;
  quantityAverage?: number | null;
  quantityStandardDeviation?: number | null;
  unit?: string;
}
