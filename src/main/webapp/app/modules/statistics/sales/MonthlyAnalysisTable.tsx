import { Text } from 'app/shared/component/Text';
import { MonthlyAnalysisTableHeader } from 'app/shared/model/enumeration/MonthlyAnalysisTableHeader';
import { MonthlyAnalysisStats } from 'app/shared/model/MonthlyAnalysisStats';
import { formatStats, getProductUnit } from 'app/shared/util/Utils';
import { Column } from 'primereact/column';
import { DataTable } from 'primereact/datatable';
import React from 'react';

interface MonthlyAnalysisTableProps {
  monthlyAnalysisStats: MonthlyAnalysisStats[];
  isMonthMulti: boolean;
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
      <Column field="productCode" header={MonthlyAnalysisTableHeader.PRODUCT_CODE}></Column>
      <Column field="product" header={MonthlyAnalysisTableHeader.PRODUCT}></Column>
      <Column field="percentageStats" header={MonthlyAnalysisTableHeader.AVERAGE_PERCENTAGE} body={percentageTemplate}></Column>
      <Column field="quantityStats" header={MonthlyAnalysisTableHeader.AVERAGE_QUANTITY} body={quantityTemplate}></Column>
      <Column field="availableStockStats" header={MonthlyAnalysisTableHeader.AVAILABLE_STOCK} body={availableStockTemplate}></Column>
      <Column field="nbDeliveriesStats" header={MonthlyAnalysisTableHeader.NB_DELIVERIES} body={nbDeliveriesTemplate}></Column>
      <Column field="nbSalesStats" header={MonthlyAnalysisTableHeader.NB_SALES} body={nbSalesTemplate}></Column>
      <Column field="nbLossesStats" header={MonthlyAnalysisTableHeader.NB_LOSSES} body={nbLossesTemplate}></Column>
      <Column field="nbInventoriesStats" header={MonthlyAnalysisTableHeader.NB_INVENTORIES} body={nbInventoriesTemplate}></Column>
    </DataTable>
  );
};
