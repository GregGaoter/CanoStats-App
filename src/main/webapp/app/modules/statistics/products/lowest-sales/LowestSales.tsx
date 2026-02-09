import { useAppSelector } from 'app/config/store';
import { apiUrl } from 'app/entities/mouvements-stock/mouvements-stock.reducer';
import { Icon } from 'app/shared/component/Icon';
import { StatisticsCard } from 'app/shared/component/StatisticsCard';
import { StatisticsColor } from 'app/shared/model/enumeration/StatisticsColor';
import { LowestSalesResult } from 'app/shared/model/LowestSalesResult';
import { LowestSalesTableHeaders } from 'app/shared/model/LowestSalesTableHeaders';
import { MouvementsStockDateRange } from 'app/shared/model/MouvementsStockDateRange';
import { transformLowestSalesToChartData } from 'app/shared/util/ChartDataTransformer';
import { topLossesOptions } from 'app/shared/util/ChartOptionsUtils';
import { formatDateRange, prefixWithDateTime } from 'app/shared/util/date-utils';
import { getLowestSalesQueryParams } from 'app/shared/util/QueryParamsUtil';
import { getProductUnit } from 'app/shared/util/Utils';
import axios from 'axios';
import { ChartData } from 'chart.js';
import dayjs from 'dayjs';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';
import { Button } from 'primereact/button';
import { Card } from 'primereact/card';
import { Chart } from 'primereact/chart';
import { Fieldset } from 'primereact/fieldset';
import { TabPanel, TabView } from 'primereact/tabview';
import React, { useEffect, useRef, useState } from 'react';
import { LowestSalesFilter } from './LowestSalesFilter';
import { LowestSalesTable } from './LowestSalesTable';

export const LowestSales = () => {
  const chartRef = useRef(null);

  const mouvementsStockDateRange: MouvementsStockDateRange = useAppSelector<MouvementsStockDateRange>(
    state => state.mouvementsStock.dateRange,
  );

  const now: Date = new Date();
  const tableHeaders: LowestSalesTableHeaders = {
    productCode: 'Code',
    product: 'Produit',
    percentage: '% du stock vendu',
    quantity: 'Quantité du stock vendu',
  };

  const [dates, setDates] = useState<Date[]>([new Date(now.getFullYear(), 0, 1), now]);
  const [minDate, setMinDate] = useState<Date>(undefined);
  const [maxDate, setMaxDate] = useState<Date>(undefined);
  const [lowestSales, setLowestSales] = useState<LowestSalesResult[]>([]);
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

  const getLowestSales = (): void => {
    setLoadingData(true);
    setLowestSales([]);
    axios
      .get<LowestSalesResult[]>(`${apiUrl}/lowest-sales?${getLowestSalesQueryParams(dates)}`, {
        timeout: 3600000,
      })
      .then(response => {
        setLowestSales(response.data);
        setChartData(transformLowestSalesToChartData(response.data));
      })
      .finally(() => setLoadingData(false));
  };

  const downloadChartImage = () => {
    const chart = chartRef.current?.getChart();
    if (!chart) return;

    const url = chart.toBase64Image();

    const link = document.createElement('a');
    link.href = url;
    link.download = prefixWithDateTime('produits-les-moins-vendus-graphique.png');
    link.click();
  };

  const downloadTablesPdf = () => {
    const pdf = new jsPDF('p', 'mm', 'a4') as any;

    const startY = pdf.lastAutoTable ? pdf.lastAutoTable.finalY + 15 : 20;
    pdf.text(`Produits les moins vendus sur la période ${formatDateRange(dates)}`, 10, startY - 5);
    autoTable(pdf, {
      startY,
      head: [Object.values(tableHeaders)],
      body: lowestSales.map(ls => [
        ls.productCode,
        ls.product,
        `${Math.round(ls.percentage).toString()}%`,
        `${Math.round(ls.quantity).toString()}${getProductUnit(ls.unit)}`,
      ]),
      theme: 'grid',
      headStyles: { fontStyle: 'bold', textColor: 0, fillColor: 225 },
      bodyStyles: { textColor: 0 },
      margin: 10,
    });

    pdf.save(prefixWithDateTime('produits-les-moins-vendus-tableau.pdf'));
  };

  return (
    <div className="grid align-items-center">
      <div className="col-12">
        <Fieldset legend="Analyse des produits les moins vendus" pt={{ legend: { className: 'bg-blue-800' } }}>
          <LowestSalesFilter
            dates={dates}
            minDate={minDate}
            maxDate={maxDate}
            loadingData={loadingData}
            progressMessage={progressMessage}
            progressPercentage={progressPercentage}
            onDatesChange={d => setDates(d)}
            onApplyFilter={() => getLowestSales()}
          />
        </Fieldset>
      </div>
      <div className="col-12">
        <TabView>
          <TabPanel header="Indicateurs clés" disabled={chartData.labels.length === 0}>
            <div className="flex gap-3">
              <StatisticsCard title="Ventes totales" icon="arrow-trend-down" color={StatisticsColor.SALES} value="128.4 kg" />
              <StatisticsCard
                title="Produits à risque (faible rotation)"
                icon="arrow-trend-down"
                color={StatisticsColor.SALES}
                value="P1, P2"
              />
              <StatisticsCard
                title="Catégories les plus concernées"
                icon="arrow-trend-down"
                color={StatisticsColor.SALES}
                value="Fruits & Légumes"
              />
            </div>
          </TabPanel>
          <TabPanel header="Graphique" disabled={chartData.labels.length === 0}>
            <Card>
              <div className="flex flex-column gap-2">
                <Chart
                  ref={chartRef}
                  type="bar"
                  data={chartData}
                  options={topLossesOptions(`Produits les moins vendus sur la période ${formatDateRange(dates)}`, '% du stock vendu', '')}
                />
                <Button
                  label="Télécharger l'image du graphique"
                  icon={<Icon icon="download" marginRight />}
                  className="align-self-end"
                  onClick={downloadChartImage}
                />
              </div>
            </Card>
          </TabPanel>
          <TabPanel header="Tableau" disabled={chartData.labels.length === 0}>
            <Card>
              <div className="flex flex-column gap-2">
                <LowestSalesTable lowestSalesResults={lowestSales} headers={tableHeaders} />
                <Button
                  label="Télécharger le tableau en PDF"
                  icon={<Icon icon="download" marginRight />}
                  className="align-self-end"
                  onClick={downloadTablesPdf}
                />
              </div>
            </Card>
          </TabPanel>
        </TabView>
      </div>
    </div>
  );
};
