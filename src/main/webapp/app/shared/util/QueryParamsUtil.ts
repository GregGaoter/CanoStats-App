import { DataTableFilterMetaData } from 'primereact/datatable';
import { ProduitField } from '../model/produit.model';
import { Pagination } from './PaginationUtils';

export const getPaginationSearchParams = <T>(pagination: Pagination<T>): URLSearchParams => {
  const searchParams: URLSearchParams = new URLSearchParams();
  if (!pagination) return searchParams;
  searchParams.set('page', `${pagination.page}`);
  searchParams.set('size', `${pagination.size}`);
  searchParams.set('sort', `${pagination.sort as string},${pagination.order as string}`);
  return searchParams;
};

export const getInventoryByWeightQueryParams = (mouvement: number, period: Date[]): string => {
  const searchParams: URLSearchParams = new URLSearchParams();
  searchParams.set('vente', 'Au poids');
  searchParams.set('startDate', period[0].toISOString());
  searchParams.set('endDate', period[1].toISOString());
  searchParams.set('mouvement', `${mouvement}`);
  searchParams.set('cacheBuster', `${new Date().getTime()}`);
  return searchParams.toString();
};

export const getInventoryByPieceQueryParams = (mouvement: number, period: Date[]): string => {
  const searchParams: URLSearchParams = new URLSearchParams();
  searchParams.set('vente', 'A la pièce');
  searchParams.set('startDate', period[0].toISOString());
  searchParams.set('endDate', period[1].toISOString());
  searchParams.set('mouvement', `${mouvement}`);
  searchParams.set('cacheBuster', `${new Date().getTime()}`);
  return searchParams.toString();
};

export const getMouvementsStockQueryParams = (filters: { [key: string]: DataTableFilterMetaData }): string => {
  const searchParams: URLSearchParams = new URLSearchParams();
  if (filters.epicerioId.value) searchParams.set('epicerioId.equals', `${filters.epicerioId.value}`);
  searchParams.set('cacheBuster', `${new Date().getTime()}`);
  return searchParams.toString();
};

export const getProduitsQueryParams = (pagination: Pagination<ProduitField>): string => {
  const searchParams: URLSearchParams = new URLSearchParams(getPaginationSearchParams(pagination));
  searchParams.set('cacheBuster', `${new Date().getTime()}`);
  return searchParams.toString();
};

export const getMonthlyAnalysisQueryParams = (period: Date[]): string => {
  const startDate: string = period[0].toISOString();

  const year: number = period[1].getFullYear();
  const month: number = period[1].getMonth();
  const endDate: string = new Date(year, month + 1, 0).toISOString();

  const searchParams: URLSearchParams = new URLSearchParams();
  searchParams.set('startDate', startDate);
  searchParams.set('endDate', endDate);
  searchParams.set('cacheBuster', `${new Date().getTime()}`);
  return searchParams.toString();
};
