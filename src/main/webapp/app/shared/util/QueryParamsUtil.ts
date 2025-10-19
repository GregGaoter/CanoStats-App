import { DataTableFilterMetaData } from 'primereact/datatable';

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

export const getWeeklyBestSellersQueryParams = (period: Date[]): string => {
  const searchParams: URLSearchParams = new URLSearchParams();
  searchParams.set('startDate', period[0].toISOString());
  searchParams.set('endDate', period[1].toISOString());
  searchParams.set('cacheBuster', `${new Date().getTime()}`);
  return searchParams.toString();
};
