import { Text } from 'app/shared/component/Text';
import { TopLossesResult } from 'app/shared/model/TopLossesResult';
import { TopLossesTableHeaders } from 'app/shared/model/TopLossesTableHeaders';
import { getProductUnit } from 'app/shared/util/Utils';
import { Column } from 'primereact/column';
import { DataTable } from 'primereact/datatable';
import React from 'react';

interface TopLossesTableProps {
  topLossesResults: TopLossesResult[];
  headers: TopLossesTableHeaders;
}

export const TopLossesTable = (props: TopLossesTableProps) => {
  const percentageTemplate = (data: TopLossesResult) => <Text>{`${Math.round(data.percentage).toString()}%`}</Text>;

  const quantityTemplate = (data: TopLossesResult) => <Text>{`${Math.round(data.quantity).toString()}${getProductUnit(data.unit)}`}</Text>;

  return (
    <DataTable value={props.topLossesResults} dataKey="productCode">
      <Column field="productCode" header={props.headers.productCode}></Column>
      <Column field="product" header={props.headers.product}></Column>
      <Column field="percentage" header={props.headers.percentage} body={percentageTemplate}></Column>
      <Column field="quantity" header={props.headers.quantity} body={quantityTemplate}></Column>
    </DataTable>
  );
};
