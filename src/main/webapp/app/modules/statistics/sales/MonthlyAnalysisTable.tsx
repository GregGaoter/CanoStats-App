import { Text } from 'app/shared/component/Text';
import { MonthlyAnalysisStats } from 'app/shared/model/MonthlyAnalysisStats';
import { MonthlyAnalysisTableHeaders } from 'app/shared/model/MonthlyAnalysisTableHeaders';
import { formatStats, getProductUnit } from 'app/shared/util/Utils';
import { Column } from 'primereact/column';
import { DataTable } from 'primereact/datatable';
import React from 'react';

interface MonthlyAnalysisTableProps {
  monthlyAnalysisStats: MonthlyAnalysisStats[];
  isMonthMulti: boolean;
  headers: MonthlyAnalysisTableHeaders;
}

export const MonthlyAnalysisTable = (props: MonthlyAnalysisTableProps) => {
  const percentageTemplate = (data: MonthlyAnalysisStats) => <Text>{formatStats(data.percentageStats, '%', props.isMonthMulti)}</Text>;

  const quantityTemplate = (data: MonthlyAnalysisStats) => (
    <Text>{formatStats(data.quantityStats, getProductUnit(data.unit), props.isMonthMulti)}</Text>
  );

  const availableStockTemplate = (data: MonthlyAnalysisStats) => (
    <Text>{formatStats(data.availableStockStats, getProductUnit(data.unit), props.isMonthMulti)}</Text>
  );

  const nbDeliveriesTemplate = (data: MonthlyAnalysisStats) => (
    <Text>{formatStats(data.nbDeliveriesStats, undefined, props.isMonthMulti)}</Text>
  );

  const nbSalesTemplate = (data: MonthlyAnalysisStats) => <Text>{formatStats(data.nbSalesStats, undefined, props.isMonthMulti)}</Text>;

  const nbLossesTemplate = (data: MonthlyAnalysisStats) => <Text>{formatStats(data.nbLossesStats, undefined, props.isMonthMulti)}</Text>;

  const nbInventoriesTemplate = (data: MonthlyAnalysisStats) => (
    <Text>{formatStats(data.nbInventoriesStats, undefined, props.isMonthMulti)}</Text>
  );

  return (
    <DataTable value={props.monthlyAnalysisStats} dataKey="productCode">
      <Column field="productCode" header={props.headers.productCode}></Column>
      <Column field="product" header={props.headers.product}></Column>
      <Column field="percentageStats" header={props.headers.percentage} body={percentageTemplate}></Column>
      <Column field="quantityStats" header={props.headers.quantity} body={quantityTemplate}></Column>
      <Column field="availableStockStats" header={props.headers.availableStock} body={availableStockTemplate}></Column>
      <Column field="nbDeliveriesStats" header={props.headers.nbDeliveries} body={nbDeliveriesTemplate}></Column>
      <Column field="nbSalesStats" header={props.headers.nbSales} body={nbSalesTemplate}></Column>
      <Column field="nbLossesStats" header={props.headers.nbLosses} body={nbLossesTemplate}></Column>
      <Column field="nbInventoriesStats" header={props.headers.nbInventories} body={nbInventoriesTemplate}></Column>
    </DataTable>
  );
};
