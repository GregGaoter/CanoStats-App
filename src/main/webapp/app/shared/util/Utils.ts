import { StatisticalQuantities } from '../model/StatisticalQuantities';

export const getProductUnit = (vente: string): string => (vente === 'Au poids' ? 'kg' : 'p');

export const formatStats = (stats: StatisticalQuantities, unit: string, showStandardDeviation: boolean): string => {
  const unitDisplayed: string = unit ? `${unit}` : '';
  const mean: string = `${Math.ceil(stats.mean).toString()}${unitDisplayed}`;
  return showStandardDeviation ? `${mean} ± ${Math.ceil(stats.standardDeviation).toString()}${unitDisplayed}` : mean;
};
