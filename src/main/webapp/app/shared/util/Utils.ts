import { MonthlyAnalysisTableHeaders } from '../model/MonthlyAnalysisTableHeaders';
import { StatisticalQuantities } from '../model/StatisticalQuantities';

export const getProductUnit = (vente: string): string => (vente === 'Au poids' ? 'kg' : 'p');

export const formatStats = (stats: StatisticalQuantities, unit: string, showStandardDeviation: boolean): string => {
  const unitDisplayed: string = unit ? `${unit}` : '';
  const mean: string = `${Math.ceil(stats.mean).toString()}${unitDisplayed}`;
  return showStandardDeviation ? `${mean} ± ${Math.ceil(stats.standardDeviation).toString()}${unitDisplayed}` : mean;
};

export const getMonthlyAnalysisTableHeaders = (movementType: string): MonthlyAnalysisTableHeaders => {
  const percentage: string[] = ['%'];
  const quantity: string[] = ['Quantité'];
  switch (movementType) {
    case 'Vente':
      percentage.push('vendu');
      quantity.push('vendue');
      break;
    case 'Perte':
      percentage.push('perdu');
      quantity.push('perdue');
      break;
    default:
      break;
  }
  return {
    productCode: 'Code',
    product: 'Produit',
    percentage: percentage.join(' '),
    quantity: quantity.join(' '),
    availableStock: 'Stock disponible',
    nbDeliveries: 'Nb Livraisons',
    nbSales: 'Nb Ventes',
    nbLosses: 'Nb Pertes',
    nbInventories: 'Nb Inventaires',
  };
};

export const getMonthlyAnalysisChartYLabel = (movementType: string): string => {
  switch (movementType) {
    case 'Vente':
      return '% moyen vendu';
    case 'Perte':
      return '% moyen perdu';
    default:
      return '% moyen';
  }
};
