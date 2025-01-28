import { useAppDispatch, useAppSelector } from 'app/config/store';
import { MouvementsStockField } from 'app/shared/model/mouvements-stock.model';
import { IQueryParams } from 'app/shared/reducers/reducer.utils';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { Pagination, PaginationOrder } from 'app/shared/util/PaginationUtils';
import { Card } from 'primereact/card';
import { Column } from 'primereact/column';
import { DataTable } from 'primereact/datatable';
import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { getEntities as getMouvementsStocks } from './mouvements-stock.reducer';

export const MouvementsStock = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const defaultPagination: Pagination<MouvementsStockField> = {
    page: 1,
    size: ITEMS_PER_PAGE,
    sort: 'date',
    order: PaginationOrder.DESC,
    total: 0,
  };
  const [pagination, setPagination] = useState<Pagination<MouvementsStockField>>(defaultPagination);

  const [isComponentMounting, setComponentMounting] = useState<boolean>(true);

  const mouvementsStocks = useAppSelector(state => state.mouvementsStock.entities);
  const loading = useAppSelector(state => state.mouvementsStock.loading);
  const totalItems = useAppSelector(state => state.mouvementsStock.totalItems);

  const getAllMouvementsStocks = () => {
    const queryParams: IQueryParams = {
      query: undefined,
      page: pagination.page - 1,
      size: pagination.size,
      sort: `${pagination.sort},${pagination.order}`,
    };
    dispatch(getMouvementsStocks(queryParams));
  };

  useEffect(() => {
    getAllMouvementsStocks();
  }, []);

  useEffect(() => {
    if (!loading) setComponentMounting(false);
  }, [loading]);

  useEffect(() => {
    if (!isComponentMounting && !loading) getAllMouvementsStocks();
  }, [pagination]);

  const handleTableChange = (type: string, { page, sizePerPage, sortField, sortOrder }: any): void => {
    if (type === 'pagination') setPagination({ ...pagination, page, size: sizePerPage });
    if (type === 'sort') setPagination({ ...pagination, sort: sortField, order: sortOrder });
  };

  return (
    <Card title="Mouvements de stock">
      <DataTable value={mouvementsStocks}>
        <Column field="epicerioId" header="ID"></Column>
        <Column field="date" header="Date"></Column>
        <Column field="type" header="Type"></Column>
        <Column field="mouvement" header="Mouvement"></Column>
      </DataTable>
    </Card>
  );
};

export default MouvementsStock;
