import { SortOrder } from 'primereact/datatable';

export interface Pagination<T> {
  first: number;
  page: number;
  size: number;
  sort: T;
  order: PaginationOrder;
  total: number;
}

export enum PaginationOrder {
  ASC = 'asc',
  DESC = 'desc',
}

export const getPaginationOrderValue = (order: PaginationOrder): SortOrder => {
  switch (order) {
    case PaginationOrder.ASC:
      return 1;
    case PaginationOrder.DESC:
      return -1;
    default:
      return 0;
  }
};

export const getValueOfOrder = (order: SortOrder): PaginationOrder => {
  switch (order) {
    case 1:
      return PaginationOrder.ASC;
    case -1:
      return PaginationOrder.DESC;
    default:
      return undefined;
  }
};
