import { Text } from 'app/shared/component/Text';
import { LowestSalesResult } from 'app/shared/model/LowestSalesResult';
import { LowestSalesTableHeaders } from 'app/shared/model/LowestSalesTableHeaders';
import { getProductUnit } from 'app/shared/util/Utils';
import { Column } from 'primereact/column';
import { DataTable } from 'primereact/datatable';
import React from 'react';

interface LowestSalesTableProps {
  lowestSalesResults: LowestSalesResult[];
  headers: LowestSalesTableHeaders;
}

export const LowestSalesTable = (props: LowestSalesTableProps) => {
  const percentageTemplate = (data: LowestSalesResult) => <Text>{`${Math.round(data.percentage).toString()}%`}</Text>;

  const quantityTemplate = (data: LowestSalesResult) => (
    <Text>{`${Math.round(data.quantity).toString()}${getProductUnit(data.unit)}`}</Text>
  );

  return (
    <DataTable value={props.lowestSalesResults} dataKey="productCode">
      <Column field="productCode" header={props.headers.productCode}></Column>
      <Column field="product" header={props.headers.product}></Column>
      <Column field="percentage" header={props.headers.percentage} body={percentageTemplate}></Column>
      <Column field="quantity" header={props.headers.quantity} body={quantityTemplate}></Column>
    </DataTable>
  );
};
