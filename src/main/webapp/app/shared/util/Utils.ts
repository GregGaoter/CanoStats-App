import { MonthlyAnalysisTableHeaders } from '../model/MonthlyAnalysisTableHeaders';
import { StatisticalQuantities } from '../model/StatisticalQuantities';

export const getProductUnit = (vente: string): string => (vente === 'Au poids' ? 'kg' : 'p');

export const formatStats = (stats: StatisticalQuantities, unit: string, showStandardDeviation: boolean): string => {
  const unitDisplayed: string = unit ? `${unit}` : '';
  const mean: string = `${Math.ceil(stats.mean).toString()}${unitDisplayed}`;
  return showStandardDeviation ? `${mean} ± ${Math.ceil(stats.standardDeviation).toString()}${unitDisplayed}` : mean;
};

export const getMonthlyAnalysisTableHeaders = (movementType: string): MonthlyAnalysisTableHeaders => ({
  productCode: 'Code',
  product: 'Produit',
  percentage: movementType === 'Vente' ? '% vendu' : '% perdu',
  quantity: movementType === 'Vente' ? 'Quantité vendue' : 'Quantité perdue',
  availableStock: 'Stock disponible',
  nbDeliveries: 'Nb Livraisons',
  nbSales: 'Nb Ventes',
  nbLosses: 'Nb Pertes',
  nbInventories: 'Nb Inventaires',
});
