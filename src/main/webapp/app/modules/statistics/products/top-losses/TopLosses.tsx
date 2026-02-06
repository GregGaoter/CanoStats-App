import { useAppSelector } from 'app/config/store';
import { apiUrl } from 'app/entities/mouvements-stock/mouvements-stock.reducer';
import { AnalysisProgress } from 'app/shared/component/analysis/AnalysisProgress';
import { StatisticsCard } from 'app/shared/component/StatisticsCard';
import { StatisticsColor } from 'app/shared/model/enumeration/StatisticsColor';
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
import { TabPanel, TabView } from 'primereact/tabview';
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
      {loadingData && <AnalysisProgress message={progressMessage} percentage={progressPercentage} />}
      {chartData.labels.length > 0 && (
        <div className="col-12">
          <TabView>
            <TabPanel header="Indicateurs clés">
              <div className="flex gap-3">
                <StatisticsCard title="Pertes totales (quantité)" icon="arrow-trend-down" color={StatisticsColor.SALES} value="128.4 kg" />
                <StatisticsCard title="Pertes totales (CHF)" icon="arrow-trend-down" color={StatisticsColor.SALES} value="1’245.50 CHF" />
                <StatisticsCard
                  title="Catégorie la plus touchée"
                  icon="arrow-trend-down"
                  color={StatisticsColor.SALES}
                  value="Fruits & Légumes"
                />
                <StatisticsCard title="Produit le plus touché" icon="arrow-trend-down" color={StatisticsColor.SALES} value="Bananes" />
              </div>
            </TabPanel>
            <TabPanel header="Graphique">
              <Card>
                <Chart
                  ref={chartRef}
                  type="bar"
                  data={chartData}
                  options={topLossesOptions('Les 50 produits les plus en pertes', '', '% moyen perdu')}
                />
              </Card>
            </TabPanel>
            <TabPanel header="Tableau">
              <Card>
                <p className="m-0">
                  At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti
                  quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia
                  deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam
                  libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus.
                </p>
              </Card>
            </TabPanel>
          </TabView>
        </div>
      )}
    </div>
  );
};
