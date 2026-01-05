import { useAppSelector } from 'app/config/store';
import { apiUrl } from 'app/entities/mouvements-stock/mouvements-stock.reducer';
import { Icon } from 'app/shared/component/Icon';
import { Text } from 'app/shared/component/Text';
import { MonthlyAnalysisTableHeader } from 'app/shared/model/enumeration/MonthlyAnalysisTableHeader';
import { MonthlyAnalysisStats } from 'app/shared/model/MonthlyAnalysisStats';
import { MouvementsStockDateRange } from 'app/shared/model/MouvementsStockDateRange';
import { transformMonthlyAnalysisToChartData } from 'app/shared/util/ChartDataTransformer';
import { lineOptions } from 'app/shared/util/ChartOptionsUtils';
import { prefixWithDateTime } from 'app/shared/util/date-utils';
import { getMonthlyAnalysisQueryParams } from 'app/shared/util/QueryParamsUtil';
import { formatStats, getProductUnit } from 'app/shared/util/Utils';
import axios from 'axios';
import dayjs from 'dayjs';
import 'dayjs/locale/fr';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';
import { capitalize, groupBy } from 'lodash';
import { BlockUI } from 'primereact/blockui';
import { Button } from 'primereact/button';
import { Card } from 'primereact/card';
import { Chart } from 'primereact/chart';
import { Fieldset } from 'primereact/fieldset';
import { ProgressBar } from 'primereact/progressbar';
import { SelectButton, SelectButtonChangeEvent } from 'primereact/selectbutton';
import { Toolbar } from 'primereact/toolbar';
import React, { useEffect, useRef, useState } from 'react';
import { MonthlyAnalysisFilter } from './MonthlyAnalysisFilter';
import { MonthlyAnalysisTable } from './MonthlyAnalysisTable';

interface ApiMapResponse {
  [month: number]: MonthlyAnalysisStats[];
}

interface ChartData {
  labels: string[];
  datasets: Array<{
    label: string;
    data: number[];
  }>;
}

export interface MovementTypeOption {
  label: string;
  value: string;
}

export interface ProductTypeOption {
  label: string;
  value: string;
}

enum ResultDisplay {
  TABLE = 'TABLE',
  CHART = 'CHART',
}

interface ResultDisplayOption {
  icon: JSX.Element;
  display: ResultDisplay;
}

interface YearMonth {
  year: number;
  month: number;
}

export const MonthlyAnalysis = () => {
  const chartRef = useRef(null);

  const productTypesByCode: string[] = useAppSelector<string[]>(state => state.produit.productTypesByCode);
  const mouvementsStockDateRange: MouvementsStockDateRange = useAppSelector<MouvementsStockDateRange>(
    state => state.mouvementsStock.dateRange,
  );

  const now: Date = new Date();
  const movementTypeOptions: MovementTypeOption[] = [
    { label: 'Vente', value: 'Vente' },
    { label: 'Perte', value: 'Perte' },
  ];
  const resultDisplayOptions: ResultDisplayOption[] = [
    { icon: <Icon icon="table-list" />, display: ResultDisplay.TABLE },
    { icon: <Icon icon="chart-line" />, display: ResultDisplay.CHART },
  ];

  const [dates, setDates] = useState<Date[]>([new Date(now.getFullYear(), 0, 1), now]);
  const [monthToYears, setMonthToYears] = useState<Map<number, number[]>>(new Map());
  const [minDate, setMinDate] = useState<Date>(undefined);
  const [maxDate, setMaxDate] = useState<Date>(undefined);
  const [movementType, setMovementType] = useState<string>('');
  const [productTypes, setProductTypes] = useState<string[]>([]);
  const [apiMapResponse, setApiMapResponse] = useState<ApiMapResponse>({});
  const [loadingData, setLoadingData] = useState<boolean>(false);
  const [chartData, setChartData] = useState<ChartData>({ labels: [], datasets: [] });
  const [resultDisplay, setResultDisplay] = useState<ResultDisplay>(ResultDisplay.TABLE);
  const [progressPercentage, setProgressPercentage] = useState<number>(0);
  const [progressMessage, setProgressMessage] = useState<string>('');

  const productTypeOptions: ProductTypeOption[] = productTypesByCode.map(pt => ({ label: pt, value: pt }));

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

  const getMonthsRange = (): YearMonth[] => {
    const result: YearMonth[] = [];
    const date: Date = new Date(dates[0].getFullYear(), dates[0].getMonth(), 1);
    while (date <= dates[1]) {
      result.push({ year: date.getFullYear(), month: date.getMonth() + 1 });
      date.setMonth(date.getMonth() + 1);
    }
    return result;
  };

  const getMonthToYears = (): Map<number, number[]> =>
    new Map(
      Object.entries(groupBy(getMonthsRange(), 'month')).map(([month, yearMonths]) => [Number(month), yearMonths.map(ym => ym.year)]),
    );

  const isMonthMulti = (month: number): boolean => (monthToYears.get(month)?.length ?? 0) > 1;

  const getMonthlyAnalysis = (): void => {
    setLoadingData(true);
    setApiMapResponse({});
    setChartData({ labels: [], datasets: [] });
    axios
      .get<ApiMapResponse>(`${apiUrl}/monthly-analysis?${getMonthlyAnalysisQueryParams(movementType, productTypes, dates)}`, {
        timeout: 3600000,
      })
      .then(response => {
        const mty: Map<number, number[]> = getMonthToYears();
        setMonthToYears(mty);
        setApiMapResponse(response.data);
        setChartData(transformMonthlyAnalysisToChartData(response.data, productTypes, mty));
      })
      .finally(() => setLoadingData(false));
  };

  const toMonthName = (month: number): string =>
    capitalize(
      dayjs()
        .month(month - 1)
        .format('MMMM'),
    );

  const displayResultMonthHeader = (month: number): string =>
    `${toMonthName(month)} ${monthToYears.get(month).join('-')} - ${movementType}`;

  const downloadTablesPdf = () => {
    const pdf = new jsPDF('p', 'mm', 'a4') as any;

    Object.entries(apiMapResponse).forEach(([month, monthlyAnalysisStats]) => {
      const startY = pdf.lastAutoTable ? pdf.lastAutoTable.finalY + 15 : 20;
      const monthNumber: number = Number(month);
      pdf.text(displayResultMonthHeader(monthNumber), 10, startY - 5);
      autoTable(pdf, {
        startY,
        head: [Object.values(MonthlyAnalysisTableHeader)],
        body: monthlyAnalysisStats.map(stats => [
          stats.productCode,
          stats.product,
          formatStats(stats.percentageStats, '%', isMonthMulti(monthNumber)),
          formatStats(stats.quantityStats, getProductUnit(stats.unit), isMonthMulti(monthNumber)),
          formatStats(stats.availableStockStats, getProductUnit(stats.unit), isMonthMulti(monthNumber)),
          formatStats(stats.nbDeliveriesStats, undefined, isMonthMulti(monthNumber)),
          formatStats(stats.nbSalesStats, undefined, isMonthMulti(monthNumber)),
          formatStats(stats.nbLossesStats, undefined, isMonthMulti(monthNumber)),
          formatStats(stats.nbInventoriesStats, undefined, isMonthMulti(monthNumber)),
        ]),
        theme: 'grid',
        headStyles: { fontSize: 8, fontStyle: 'bold', textColor: 0, fillColor: 225 },
        bodyStyles: { fontSize: 8, textColor: 0 },
        columnStyles: { 2: { cellWidth: 20 } },
        margin: 10,
      });
    });

    pdf.save(prefixWithDateTime(`${movementType.toLowerCase()}-mensuelle-tableau.pdf`));
  };

  const downloadChartImage = () => {
    const chart = chartRef.current?.getChart();
    if (!chart) return;

    const url = chart.toBase64Image();

    const link = document.createElement('a');
    link.href = url;
    link.download = prefixWithDateTime(`${movementType.toLowerCase()}-mensuelle-graphique.png`);
    link.click();
  };

  const downloadDisplayedResult = () => {
    if (resultDisplay === ResultDisplay.TABLE) {
      downloadTablesPdf();
    } else {
      downloadChartImage();
    }
  };

  const toolbarStartContent: JSX.Element = (
    <div className="flex align-items-center gap-2">
      <Text>Affichage</Text>
      <SelectButton
        value={resultDisplay}
        onChange={(e: SelectButtonChangeEvent) => setResultDisplay(e.value)}
        optionLabel="icon"
        optionValue="display"
        options={resultDisplayOptions}
      />
    </div>
  );

  const toolbarEndContent: JSX.Element = (
    <Button label="Télécharger" icon={<Icon icon="download" marginRight />} onClick={downloadDisplayedResult} />
  );

  return (
    <div className="grid align-items-center">
      <div className="col-12">
        <Fieldset legend="Analyse des mouvements de stock mensuels" pt={{ legend: { className: 'bg-blue-800' } }} toggleable collapsed>
          <div className="flex flex-column gap-1">
            <Text>{`Cette analyse permet d'examiner l'évolution des stocks selon le type de mouvement (Vente ou Perte), la catégorie de produits et la période sélectionnée.`}</Text>
            <Text>{`Pour chaque mois, un tableau détaille les indicateurs clés : pourcentage moyen de stock, quantités moyennes, stock disponible, ainsi que le nombre de livraisons, ventes, pertes et inventaires.`}</Text>
            <Text>{`Lorsque plusieurs années sont incluses pour un même mois, les écarts‑types sont affichés pour visualiser la variabilité.`}</Text>
            <Text>{`Un graphique complète l'analyse en montrant l'évolution du pourcentage moyen par type de produit au fil des mois.`}</Text>
          </div>
        </Fieldset>
      </div>
      <div className="col-12">
        <BlockUI blocked={loadingData}>
          <MonthlyAnalysisFilter
            dates={dates}
            movementType={movementType}
            productTypes={productTypes}
            movementTypeOptions={movementTypeOptions}
            productTypeOptions={productTypeOptions}
            minDate={minDate}
            maxDate={maxDate}
            loadingData={loadingData}
            onMovementTypeChange={mt => setMovementType(mt)}
            onProductTypesChange={pt => setProductTypes(pt)}
            onDatesChange={d => setDates(d)}
            onApplyFilter={() => getMonthlyAnalysis()}
          />
        </BlockUI>
      </div>
      {loadingData && (
        <div className="col-12 text-center mt-4">
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
        <>
          <div className="col-12">
            <Toolbar start={toolbarStartContent} end={toolbarEndContent} />
          </div>
          <div className="col-12">
            {resultDisplay === ResultDisplay.TABLE ? (
              Object.entries(apiMapResponse).map(([month, monthlyAnalysisStats]) => (
                <div className="col-12" key={month}>
                  <Card title={displayResultMonthHeader(Number(month))}>
                    <MonthlyAnalysisTable monthlyAnalysisStats={monthlyAnalysisStats} isMonthMulti={isMonthMulti(Number(month))} />
                  </Card>
                </div>
              ))
            ) : (
              <div className="col-12">
                <Card>
                  <Chart ref={chartRef} type="line" data={chartData} options={lineOptions(movementType, '', '% du stock')} />
                </Card>
              </div>
            )}
          </div>
        </>
      )}
    </div>
  );
};
