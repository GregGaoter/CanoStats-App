import { useAppSelector } from 'app/config/store';
import { apiUrl } from 'app/entities/mouvements-stock/mouvements-stock.reducer';
import { Icon } from 'app/shared/component/Icon';
import { Text } from 'app/shared/component/Text';
import { MonthlyAnalysisStats } from 'app/shared/model/MonthlyAnalysisStats';
import { MouvementsStockDateRange } from 'app/shared/model/MouvementsStockDateRange';
import { StatisticalQuantities } from 'app/shared/model/StatisticalQuantities';
import { transformMonthlyAnalysisToChartData } from 'app/shared/util/ChartDataTransformer';
import { lineOptions } from 'app/shared/util/ChartOptionsUtils';
import { getMonthlyAnalysisQueryParams } from 'app/shared/util/QueryParamsUtil';
import axios from 'axios';
import dayjs from 'dayjs';
import 'dayjs/locale/fr';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';
import { capitalize } from 'lodash';
import { BlockUI } from 'primereact/blockui';
import { Button } from 'primereact/button';
import { Card } from 'primereact/card';
import { Chart } from 'primereact/chart';
import { Column } from 'primereact/column';
import { DataTable } from 'primereact/datatable';
import { ProgressSpinner } from 'primereact/progressspinner';
import { SelectButton, SelectButtonChangeEvent } from 'primereact/selectbutton';
import { Toolbar } from 'primereact/toolbar';
import React, { useEffect, useRef, useState } from 'react';
import { MonthlyAnalysisFilter } from './MonthlyAnalysisFilter';

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

enum TableHeader {
  PRODUCT_CODE = 'Code',
  PRODUCT = 'Produit',
  AVERAGE_PERCENTAGE = '% moyen',
  AVERAGE_QUANTITY = 'Quantité moyenne',
  AVAILABLE_STOCK = 'Stock disponible',
  NB_DELIVERIES = 'Nb Livraisons',
  NB_SALES = 'Nb Ventes',
  NB_LOSSES = 'Nb Pertes',
  NB_INVENTORIES = 'Nb Inventaires',
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
  const [minDate, setMinDate] = useState<Date>(undefined);
  const [maxDate, setMaxDate] = useState<Date>(undefined);
  const [movementType, setMovementType] = useState<string>('');
  const [productTypes, setProductTypes] = useState<string[]>([]);
  const [apiMapResponse, setApiMapResponse] = useState<ApiMapResponse>({});
  const [loadingData, setLoadingData] = useState<boolean>(false);
  const [chartData, setChartData] = useState<ChartData>({ labels: [], datasets: [] });
  const [resultDisplay, setResultDisplay] = useState<ResultDisplay>(ResultDisplay.TABLE);

  const productTypeOptions: ProductTypeOption[] = productTypesByCode.map(pt => ({ label: pt, value: pt }));

  dayjs.locale('fr');

  useEffect(() => {
    if (mouvementsStockDateRange?.startDate) setMinDate(new Date(mouvementsStockDateRange.startDate));
    if (mouvementsStockDateRange?.endDate) {
      const endDate: Date = new Date(mouvementsStockDateRange.endDate);
      setMaxDate(endDate);
      setDates([new Date(endDate.getFullYear(), 0, 1), new Date(endDate.getFullYear(), endDate.getMonth(), 1)]);
    }
  }, [mouvementsStockDateRange]);

  const getMonthlyAnalysis = (): void => {
    setLoadingData(true);
    setApiMapResponse({});
    setChartData({ labels: [], datasets: [] });
    axios
      .get<ApiMapResponse>(`${apiUrl}/monthly-analysis?${getMonthlyAnalysisQueryParams(movementType, productTypes, dates)}`, {
        timeout: 3600000,
      })
      .then(response => {
        setApiMapResponse(response.data);
        setChartData(transformMonthlyAnalysisToChartData(response.data, productTypes));
      })
      .finally(() => setLoadingData(false));
  };

  const getUnit = (vente: string): string => (vente === 'Au poids' ? 'kg' : 'pièces');

  const formatStats = (stats: StatisticalQuantities, unit: string): string => {
    const unitDisplayed: string = unit ? ` ${unit}` : '';
    return `${Math.ceil(stats.mean).toString()}${unitDisplayed} ± ${Math.ceil(stats.standardDeviation).toString()}${unitDisplayed}`;
  };

  const percentageTemplate = (data: MonthlyAnalysisStats) => <Text>{formatStats(data.percentageStats, '%')}</Text>;

  const quantityTemplate = (data: MonthlyAnalysisStats) => <Text>{formatStats(data.quantityStats, getUnit(data.unit))}</Text>;

  const availableStockTemplate = (data: MonthlyAnalysisStats) => <Text>{formatStats(data.availableStockStats, getUnit(data.unit))}</Text>;

  const nbDeliveriesTemplate = (data: MonthlyAnalysisStats) => <Text>{formatStats(data.nbDeliveriesStats, undefined)}</Text>;

  const nbSalesTemplate = (data: MonthlyAnalysisStats) => <Text>{formatStats(data.nbSalesStats, undefined)}</Text>;

  const nbLossesTemplate = (data: MonthlyAnalysisStats) => <Text>{formatStats(data.nbLossesStats, undefined)}</Text>;

  const nbInventoriesTemplate = (data: MonthlyAnalysisStats) => <Text>{formatStats(data.nbInventoriesStats, undefined)}</Text>;

  const formatFileNameDates = (): string => dates.map(date => dayjs(date).format('YYYY-MM')).join('-');

  const toMonthName = (monthNumber: string): string =>
    capitalize(
      dayjs()
        .month(Number(monthNumber) - 1)
        .format('MMMM'),
    );

  const downloadTablesPdf = () => {
    const pdf = new jsPDF('p', 'mm', 'a4') as any;

    Object.entries(apiMapResponse).forEach(([month, monthlyAnalysisStats]) => {
      const startY = pdf.lastAutoTable ? pdf.lastAutoTable.finalY + 15 : 20;
      pdf.text(toMonthName(month), 10, startY - 5);
      autoTable(pdf, {
        startY,
        head: [Object.values(TableHeader)],
        body: monthlyAnalysisStats.map(stats => [
          stats.productCode,
          stats.product,
          formatStats(stats.percentageStats, '%'),
          formatStats(stats.quantityStats, getUnit(stats.unit)),
          formatStats(stats.availableStockStats, getUnit(stats.unit)),
          formatStats(stats.nbDeliveriesStats, undefined),
          formatStats(stats.nbSalesStats, undefined),
          formatStats(stats.nbLossesStats, undefined),
          formatStats(stats.nbInventoriesStats, undefined),
        ]),
        styles: { fontSize: 8 },
        headStyles: { fillColor: [22, 160, 133] },
      });
    });

    pdf.save(`${formatFileNameDates()}-${movementType.toLowerCase()}-mensuelle-tableau.pdf`);
  };

  const downloadChartImage = () => {
    const chart = chartRef.current?.getChart();
    if (!chart) return;

    const url = chart.toBase64Image();

    const link = document.createElement('a');
    link.href = url;
    link.download = `${formatFileNameDates()}-${movementType.toLowerCase()}-mensuelle-graphique.png`;
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
        <BlockUI blocked={loadingData} template={<ProgressSpinner />}>
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
      {chartData.labels.length > 0 && (
        <>
          <div className="col-12">
            <Toolbar start={toolbarStartContent} end={toolbarEndContent} />
          </div>
          <div className="col-12">
            {resultDisplay === ResultDisplay.TABLE ? (
              Object.entries(apiMapResponse).map(([month, monthlyAnalysisStats]) => (
                <div className="col-12" key={month}>
                  <Card title={toMonthName(month)}>
                    <DataTable value={monthlyAnalysisStats} dataKey="productCode">
                      <Column field="productCode" header={TableHeader.PRODUCT_CODE}></Column>
                      <Column field="product" header={TableHeader.PRODUCT}></Column>
                      <Column field="percentageStats" header={TableHeader.AVERAGE_PERCENTAGE} body={percentageTemplate}></Column>
                      <Column field="quantityStats" header={TableHeader.AVERAGE_QUANTITY} body={quantityTemplate}></Column>
                      <Column field="availableStockStats" header={TableHeader.AVAILABLE_STOCK} body={availableStockTemplate}></Column>
                      <Column field="nbDeliveriesStats" header={TableHeader.NB_DELIVERIES} body={nbDeliveriesTemplate}></Column>
                      <Column field="nbSalesStats" header={TableHeader.NB_SALES} body={nbSalesTemplate}></Column>
                      <Column field="nbLossesStats" header={TableHeader.NB_LOSSES} body={nbLossesTemplate}></Column>
                      <Column field="nbInventoriesStats" header={TableHeader.NB_INVENTORIES} body={nbInventoriesTemplate}></Column>
                    </DataTable>
                  </Card>
                </div>
              ))
            ) : (
              <div className="col-12">
                <Card>
                  <Chart ref={chartRef} type="line" data={chartData} options={lineOptions} />
                </Card>
              </div>
            )}
          </div>
        </>
      )}
    </div>
  );
};
