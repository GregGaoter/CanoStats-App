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
