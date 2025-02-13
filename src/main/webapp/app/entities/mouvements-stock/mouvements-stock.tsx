import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Icon } from 'app/shared/component/Icon';
import { Text } from 'app/shared/component/Text';
import { IMouvementsStock, MouvementsStockField } from 'app/shared/model/mouvements-stock.model';
import { IQueryParams } from 'app/shared/reducers/reducer.utils';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { getPaginationOrderValue, getValueOfOrder, Pagination, PaginationOrder } from 'app/shared/util/PaginationUtils';
import dayjs from 'dayjs';
import { Button } from 'primereact/button';
import { Card } from 'primereact/card';
import { Column, ColumnEditorOptions } from 'primereact/column';
import { DataTable, DataTableStateEvent } from 'primereact/datatable';
import { InputNumber, InputNumberValueChangeEvent } from 'primereact/inputnumber';
import { Paginator, PaginatorPageChangeEvent } from 'primereact/paginator';
import React, { useEffect, useState } from 'react';
import { getEntities as getMouvementsStocks } from './mouvements-stock.reducer';

export const MouvementsStock = () => {
  const dispatch = useAppDispatch();

  const defaultPagination: Pagination<MouvementsStockField> = {
    first: 0,
    page: 0,
    size: ITEMS_PER_PAGE,
    sort: 'date',
    order: PaginationOrder.DESC,
    total: 0,
  };
  const [pagination, setPagination] = useState<Pagination<MouvementsStockField>>(defaultPagination);
  const [isComponentMounting, setComponentMounting] = useState<boolean>(true);
  const [selectedMouvementsStock, setSelectedMouvementsStock] = useState<IMouvementsStock[]>([]);

  const mouvementsStocks = useAppSelector(state => state.mouvementsStock.entities);
  const loading = useAppSelector(state => state.mouvementsStock.loading);
  const totalItems = useAppSelector(state => state.mouvementsStock.totalItems);

  const getAllMouvementsStocks = () => {
    const queryParams: IQueryParams = {
      query: undefined,
      page: pagination.page,
      size: pagination.size,
      sort: `${pagination.sort},${pagination.order}`,
    };
    dispatch(getMouvementsStocks(queryParams));
  };

  useEffect(() => {
    getAllMouvementsStocks();
  }, []);

  useEffect(() => {
    if (!loading) {
      setComponentMounting(false);
      setPagination({ ...pagination, total: totalItems });
    }
  }, [loading]);

  const handlePageChange = (event: PaginatorPageChangeEvent): void => {
    setPagination({ ...pagination, first: event.first, page: event.first / event.rows, size: event.rows });
    getAllMouvementsStocks();
  };

  const handleSort = (event: DataTableStateEvent): void => {
    setPagination({ ...pagination, sort: event.sortField as MouvementsStockField, order: getValueOfOrder(event.sortOrder) });
    getAllMouvementsStocks();
  };

  const cardTitle = (
    <div className="flex align-items-center justify-content-between">
      <Text>Mouvements de stock</Text>
      <Button icon={<Icon icon="ban" marginRight />} label="Désactiver" severity="danger" disabled={selectedMouvementsStock.length === 0} />
    </div>
  );

  const dateTemplate = (mouvementsStock: IMouvementsStock) => dayjs(mouvementsStock.date).format('DD-MM-YYYY HH:mm:ss');

  const mouvementEditor = (options: ColumnEditorOptions) => {
    return (
      <InputNumber
        value={options.value}
        onValueChange={(e: InputNumberValueChangeEvent) => options.editorCallback(e.value)}
        mode="decimal"
        onKeyDown={e => e.stopPropagation()}
      />
    );
  };

  return (
    <Card title={cardTitle}>
      <DataTable
        value={mouvementsStocks}
        emptyMessage="Aucun mouvement de stock trouvé"
        loading={loading}
        dataKey="id"
        sortField={pagination.sort}
        sortOrder={getPaginationOrderValue(pagination.order)}
        onSort={handleSort}
        selectionMode="checkbox"
        selection={selectedMouvementsStock}
        onSelectionChange={e => setSelectedMouvementsStock(e.value)}
        editMode="cell"
        rowHover
      >
        <Column selectionMode="multiple"></Column>
        <Column field="epicerioId" header="Id" sortable filter></Column>
        <Column field="codeProduit" header="Code" sortable filter></Column>
        <Column field="produit" header="Produit" sortable filter></Column>
        <Column field="type" header="Type" sortable filter></Column>
        <Column field="mouvement" header="Mouvement" sortable filter editor={options => mouvementEditor(options)}></Column>
        <Column field="solde" header="Solde" sortable filter></Column>
        <Column field="date" header="Date" body={dateTemplate} sortable filter></Column>
      </DataTable>
      <Paginator
        first={pagination.first}
        rows={pagination.size}
        totalRecords={pagination.total}
        rowsPerPageOptions={[10, 25, 50]}
        onPageChange={handlePageChange}
      />
    </Card>
  );
};

export default MouvementsStock;
