import { apiUrl } from 'app/entities/mouvements-stock/mouvements-stock.reducer';
import { Text } from 'app/shared/component/Text';
import { SellingProductResult } from 'app/shared/model/SellingProductResult';
import { getWeeklyBestSellersQueryParams } from 'app/shared/util/QueryParamsUtil';
import axios from 'axios';
import dayjs from 'dayjs';
import 'dayjs/locale/fr';
import { capitalize } from 'lodash';
import { BlockUI } from 'primereact/blockui';
import { Card } from 'primereact/card';
import { Column } from 'primereact/column';
import { DataTable } from 'primereact/datatable';
import { ProgressSpinner } from 'primereact/progressspinner';
import React, { useState } from 'react';
import { WeeklyBestSellersFilter } from './WeeklyBestSellersFilter';

interface ApiMapResponse {
  [key: number]: SellingProductResult[];
}

interface TableData extends SellingProductResult {
  month: string;
}

export const WeeklyBestSellers = () => {
  const [dates, setDates] = useState<Date[]>([new Date(new Date().getFullYear(), 0, 1), new Date()]);
  const [apiMapResponse, setApiMapResponse] = useState<ApiMapResponse>({});
  const [tableData, setTableData] = useState<TableData[]>([]);
  const [loadingData, setLoadingData] = useState<boolean>(false);

  dayjs.locale('fr');

  const getTableData = (data: ApiMapResponse): TableData[] =>
    Object.entries(data).flatMap(([key, results]) =>
      results.map(result => ({
        ...result,
        month: capitalize(
          dayjs()
            .month(Number(key) - 1)
            .format('MMMM'),
        ),
      })),
    );

  const fetchWeeklyBestSellers = (): void => {
    setLoadingData(true);
    setApiMapResponse({});
    setTableData([]);
    axios
      .get<ApiMapResponse>(`${apiUrl}/monthly-analysis?${getWeeklyBestSellersQueryParams(dates)}`, {
        timeout: 3600000,
      })
      .then(response => {
        setApiMapResponse(response.data);
        setTableData(getTableData(response.data));
      })
      .finally(() => setLoadingData(false));
  };

  const headerTemplate = (data: TableData) => <Text className="font-bold">{data.month}</Text>;

  const soldPercentageTemplate = (data: TableData) => (
    <Text>{`${Math.round(data.soldPercentageAverage).toString()} % ± ${Math.round(data.soldPercentageStandardDeviation).toString()} %`}</Text>
  );

  const soldQuantityTemplate = (data: TableData) => {
    const saleType: string = data.saleType === 'Au poids' ? 'kg' : 'pièces';
    return (
      <Text>{`${Math.ceil(data.soldQuantityAverage).toString()} ${saleType} ± ${Math.ceil(data.soldQuantityStandardDeviation).toString()} ${saleType}`}</Text>
    );
  };

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
            groupRowsBy="month"
            rowGroupHeaderTemplate={headerTemplate}
            dataKey="month"
            // scrollable
            // scrollHeight="600px"
          >
            <Column field="productCode" header="Code"></Column>
            <Column field="product" header="Produit"></Column>
            <Column field="soldPercentageAverage" header="% moyen vendu" body={soldPercentageTemplate}></Column>
            <Column field="soldQuantityAverage" header="Quantité moyenne vendu" body={soldQuantityTemplate}></Column>
          </DataTable>
        </Card>
      </div>
    </div>
  );
};
