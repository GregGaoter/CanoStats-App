import { StatisticalQuantities } from './StatisticalQuantities';

export interface MonthlyAnalysisStats {
  productCode?: string;
  product?: string;
  percentageStats?: StatisticalQuantities | null;
  quantityStats?: StatisticalQuantities | null;
  availableStockStats?: StatisticalQuantities | null;
  nbDeliveriesStats?: StatisticalQuantities | null;
  nbSalesStats?: StatisticalQuantities | null;
  nbLossesStats?: StatisticalQuantities | null;
  nbInventoriesStats?: StatisticalQuantities | null;
  unit?: string;
}
