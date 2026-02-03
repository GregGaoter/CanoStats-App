import { useAppSelector } from 'app/config/store';
import { apiUrl } from 'app/entities/mouvements-stock/mouvements-stock.reducer';
import { Text } from 'app/shared/component/Text';
import { MouvementsStockDateRange } from 'app/shared/model/MouvementsStockDateRange';
import { TopLossesResult } from 'app/shared/model/TopLossesResult';
import { transformTopLossesToChartData } from 'app/shared/util/ChartDataTransformer';
import { topLossesOptions } from 'app/shared/util/ChartOptionsUtils';
import { getTopLossesQueryParams } from 'app/shared/util/QueryParamsUtil';
import axios from 'axios';
import { ChartData } from 'chart.js';
import dayjs from 'dayjs';
import { Card } from 'primereact/card';
import { Chart } from 'primereact/chart';
import { Fieldset } from 'primereact/fieldset';
import { ProgressBar } from 'primereact/progressbar';
import React, { useEffect, useRef, useState } from 'react';
import { TopLossesFilter } from './TopLossesFilter';

export const TopLosses = () => {
  const chartRef = useRef(null);

  const mouvementsStockDateRange: MouvementsStockDateRange = useAppSelector<MouvementsStockDateRange>(
    state => state.mouvementsStock.dateRange,
  );

  const now: Date = new Date();

  const [dates, setDates] = useState<Date[]>([new Date(now.getFullYear(), 0, 1), now]);
  const [minDate, setMinDate] = useState<Date>(undefined);
  const [maxDate, setMaxDate] = useState<Date>(undefined);
  const [topLosses, setTopLosses] = useState<TopLossesResult[]>([]);
  const [loadingData, setLoadingData] = useState<boolean>(false);
  const [chartData, setChartData] = useState<ChartData<'bar'>>({ labels: [], datasets: [] });
  const [progressPercentage, setProgressPercentage] = useState<number>(0);
  const [progressMessage, setProgressMessage] = useState<string>('');

  dayjs.locale('fr');

  useEffect(() => {
    const es = new EventSource(`http://localhost:8080/${apiUrl}/analysis/progress`);
    es.onmessage = event => {
      const data = JSON.parse(event.data);
      setProgressPercentage(data.progress);
      setProgressMessage(data.message);
    };
    es.onerror = () => {
      es.close();
    };
    return () => es.close();
  }, []);

  useEffect(() => {
    if (mouvementsStockDateRange?.startDate) setMinDate(new Date(mouvementsStockDateRange.startDate));
    if (mouvementsStockDateRange?.endDate) {
      const endDate: Date = new Date(mouvementsStockDateRange.endDate);
      setMaxDate(endDate);
      setDates([new Date(endDate.getFullYear(), 0, 1), new Date(endDate.getFullYear(), endDate.getMonth(), 1)]);
    }
  }, [mouvementsStockDateRange]);

  const getTopLosses = (): void => {
    setLoadingData(true);
    setTopLosses([]);
    axios
      .get<TopLossesResult[]>(`${apiUrl}/top-losses?${getTopLossesQueryParams(dates)}`, {
        timeout: 3600000,
      })
      .then(response => {
        setTopLosses(response.data);
        setChartData(transformTopLossesToChartData(response.data));
      })
      .finally(() => setLoadingData(false));
  };

  return (
    <div className="grid align-items-center">
      <div className="col-12">
        <Fieldset legend="Analyse des 50 produits les plus en perte" pt={{ legend: { className: 'bg-blue-800' } }}>
          <TopLossesFilter
            dates={dates}
            minDate={minDate}
            maxDate={maxDate}
            loadingData={loadingData}
            onDatesChange={d => setDates(d)}
            onApplyFilter={() => getTopLosses()}
          />
        </Fieldset>
      </div>
      {loadingData && (
        <div className="col-6 col-offset-3 text-center mt-4">
          <div className="mb-2">
            <Text>{progressMessage}</Text>
          </div>
          {progressPercentage === 0 ? (
            <ProgressBar mode="indeterminate"></ProgressBar>
          ) : (
            <ProgressBar value={progressPercentage}></ProgressBar>
          )}
        </div>
      )}
      {chartData.labels.length > 0 && (
        <div className="col-12">
          <Card>
            <Chart
              ref={chartRef}
              type="bar"
              data={chartData}
              options={topLossesOptions('Les 50 produits les plus en pertes', '', '% moyen perdu')}
            />
          </Card>
        </div>
      )}
    </div>
  );
};
