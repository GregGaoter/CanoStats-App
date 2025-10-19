import { apiUrl } from 'app/entities/mouvements-stock/mouvements-stock.reducer';
import { TopSellingProductResult } from 'app/shared/model/TopSellingProductResult';
import { getWeeklyBestSellersQueryParams } from 'app/shared/util/QueryParamsUtil';
import axios from 'axios';
import React, { useState } from 'react';

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

  return <div>Weekly Best Sellers Component</div>;
};
