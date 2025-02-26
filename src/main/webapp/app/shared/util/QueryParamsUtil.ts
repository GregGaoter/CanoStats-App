import { INTEGER_MAX_VALUE } from 'app/config/constants';
import { DataTableFilterMetaData } from 'primereact/datatable';
import { PaginationOrder } from './PaginationUtils';

export const getInventoryByWeightQueryParams = (mouvement: number, period: Date[]): string => {
  const searchParams: URLSearchParams = new URLSearchParams();
  searchParams.set('vente.equals', 'Au poids');
  searchParams.set('date.greaterThanOrEqual', period[0].toISOString());
  searchParams.set('date.lessThanOrEqual', period[1].toISOString());
  searchParams.set('page', `${0}`);
  searchParams.set('size', `${INTEGER_MAX_VALUE}`);
  searchParams.set('sort', `date,${PaginationOrder.DESC}`);
  searchParams.set('mouvementValue', `${mouvement}`);
  searchParams.set('cacheBuster', `${new Date().getTime()}`);
  return searchParams.toString();
};

// export const getInventoryByWeightQueryParams = (mouvement: number, period: Date[]): string => {
//   const searchParams: URLSearchParams = new URLSearchParams();
//   searchParams.set('type.equals', 'Inventaire');
//   searchParams.set('vente.equals', 'Au poids');
//   searchParams.set('mouvement.lessThanOrEqual', `${-mouvement}`);
//   searchParams.set('date.greaterThanOrEqual', period[0].toISOString());
//   searchParams.set('date.lessThanOrEqual', period[1].toISOString());
//   searchParams.set('page', `${0}`);
//   searchParams.set('size', `${INTEGER_MAX_VALUE}`);
//   searchParams.set('sort', `mouvement,${PaginationOrder.ASC}`);
//   searchParams.set('cacheBuster', `${new Date().getTime()}`);
//   return searchParams.toString();
// };

export const getMouvementsStockQueryParams = (filters: { [key: string]: DataTableFilterMetaData }): string => {
  const searchParams: URLSearchParams = new URLSearchParams();
  if (filters.epicerioId.value) searchParams.set('epicerioId.equals', `${filters.epicerioId.value}`);
  searchParams.set('cacheBuster', `${new Date().getTime()}`);
  return searchParams.toString();
};
