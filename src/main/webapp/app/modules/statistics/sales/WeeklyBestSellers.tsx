import { apiUrl } from 'app/entities/mouvements-stock/mouvements-stock.reducer';
import { Text } from 'app/shared/component/Text';
import { TopSellingProductResult } from 'app/shared/model/TopSellingProductResult';
import { getWeeklyBestSellersQueryParams } from 'app/shared/util/QueryParamsUtil';
import axios from 'axios';
import { BlockUI } from 'primereact/blockui';
import { Card } from 'primereact/card';
import { Column } from 'primereact/column';
import { DataTable } from 'primereact/datatable';
import { ProgressSpinner } from 'primereact/progressspinner';
import React, { useState } from 'react';
import { WeeklyBestSellersFilter } from './WeeklyBestSellersFilter';

interface ApiMapResponse {
  [key: number]: TopSellingProductResult[];
}

interface TableData extends TopSellingProductResult {
  week: number;
}

export const WeeklyBestSellers = () => {
  const [dates, setDates] = useState<Date[]>([new Date(new Date().getFullYear(), 0, 1), new Date()]);
  const [apiMapResponse, setApiMapResponse] = useState<ApiMapResponse>({});
  const [tableData, setTableData] = useState<TableData[]>([]);
  const [loadingData, setLoadingData] = useState<boolean>(false);

  const getTableData = (data: ApiMapResponse): TableData[] =>
    Object.entries(data).flatMap(([key, results]) => results.map(result => ({ ...result, week: Number(key) })));

  const fetchWeeklyBestSellers = (): void => {
    setLoadingData(true);
    setApiMapResponse({});
    setTableData([]);
    axios
      .get<ApiMapResponse>(`${apiUrl}/top-5-selling-products-per-week?${getWeeklyBestSellersQueryParams(dates)}`, {
        timeout: 3600000,
      })
      .then(response => {
        setApiMapResponse(response.data);
        setTableData(getTableData(response.data));
      })
      .finally(() => setLoadingData(false));
  };

  const headerTemplate = (data: TableData) => <Text className="font-bold">{`Semaine ${String(data.week).padStart(2, '0')}`}</Text>;

  const weekTemplate = (data: TableData) => <Text>{`S${String(data.week).padStart(2, '0')}`}</Text>;

  const soldPercentageTemplate = (data: TableData) => <Text>{`${data.soldPercentage.toFixed(0)}`}</Text>;

  return (
    <div className="grid align-items-center">
      <div className="col-12">
        <BlockUI blocked={loadingData} template={<ProgressSpinner />}>
          <WeeklyBestSellersFilter
            dates={dates}
            loadingData={loadingData}
            onDatesChange={d => setDates(d)}
            onApplyFilter={() => fetchWeeklyBestSellers()}
          />
        </BlockUI>
      </div>
      <div className="col-12">
        <Card>
          <DataTable
            value={tableData}
            rowGroupMode="subheader"
            groupRowsBy="week"
            rowGroupHeaderTemplate={headerTemplate}
            dataKey="week"
            scrollable
            scrollHeight="600px"
          >
            <Column field="week" header="Semaine" body={weekTemplate}></Column>
            <Column field="productCode" header="Code"></Column>
            <Column field="product" header="Produit"></Column>
            <Column field="soldPercentage" header="% moyen vendu" body={soldPercentageTemplate}></Column>
          </DataTable>
        </Card>
      </div>
    </div>
  );
};
