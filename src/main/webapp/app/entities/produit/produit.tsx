import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Text } from 'app/shared/component/Text';
import { IProduit, ProduitField } from 'app/shared/model/produit.model';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { getPaginationOrderValue, getValueOfOrder, Pagination, PaginationOrder } from 'app/shared/util/PaginationUtils';
import { getProduitsQueryParams } from 'app/shared/util/QueryParamsUtil';
import dayjs from 'dayjs';
import { Card } from 'primereact/card';
import { Column } from 'primereact/column';
import { DataTable, DataTableStateEvent } from 'primereact/datatable';
import { Paginator, PaginatorPageChangeEvent } from 'primereact/paginator';
import React, { useEffect, useState } from 'react';
import { getEntities as getProduits } from './produit.reducer';

export const Produit = () => {
  const dispatch = useAppDispatch();

  const defaultPagination: Pagination<ProduitField> = {
    first: 0,
    page: 0,
    size: ITEMS_PER_PAGE,
    sort: 'nom',
    order: PaginationOrder.ASC,
    total: 0,
  };
  const [pagination, setPagination] = useState<Pagination<ProduitField>>(defaultPagination);
  const [isComponentMounting, setComponentMounting] = useState<boolean>(true);

  const produits = useAppSelector(state => state.produit.entities);
  const loading = useAppSelector(state => state.produit.loading);
  const totalItems = useAppSelector(state => state.produit.totalItems);

  const getAllProduits = () => {
    dispatch(getProduits(getProduitsQueryParams(pagination)));
  };

  useEffect(() => {
    getAllProduits();
  }, []);

  useEffect(() => {
    if (!loading) {
      setComponentMounting(false);
      setPagination({ ...pagination, total: totalItems });
    }
  }, [loading]);

  const handlePageChange = (event: PaginatorPageChangeEvent): void => {
    setPagination({ ...pagination, first: event.first, page: event.first / event.rows, size: event.rows });
    getAllProduits();
  };

  const handleSort = (event: DataTableStateEvent): void => {
    setPagination({ ...pagination, sort: event.sortField as ProduitField, order: getValueOfOrder(event.sortOrder) });
    getAllProduits();
  };

  const cardTitle = <Text>Produits</Text>;

  const dateTemplate = (date: dayjs.Dayjs): string => (date ? dayjs(date).format('DD-MM-YYYY') : '');

  const derniereLivraisonDateTemplate = (produit: IProduit): string => dateTemplate(produit.derniereLivraisonDate);

  return (
    <Card title={<Text>Produits</Text>}>
      <DataTable
        value={produits}
        emptyMessage="Aucun produit trouvé"
        loading={loading}
        dataKey="id"
        sortField={pagination.sort}
        sortOrder={getPaginationOrderValue(pagination.order)}
        onSort={handleSort}
        rowHover
      >
        <Column field="nom" header="Nom" sortable></Column>
        <Column field="code" header="Code" sortable></Column>
        <Column field="prixFournisseur" header="Prix fournisseur"></Column>
        <Column field="prixVente" header="Prix de vente"></Column>
        <Column field="derniereLivraisonDate" header="Dernière livraison" body={derniereLivraisonDateTemplate} sortable></Column>
        <Column field="tags" header="Tags"></Column>
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
