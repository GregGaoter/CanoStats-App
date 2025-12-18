import { useAppSelector } from 'app/config/store';
import { apiUrl } from 'app/entities/mouvements-stock/mouvements-stock.reducer';
import { Text } from 'app/shared/component/Text';
import { MonthlyAnalysisStats } from 'app/shared/model/MonthlyAnalysisStats';
import { MouvementsStockDateRange } from 'app/shared/model/MouvementsStockDateRange';
import { StatisticalQuantities } from 'app/shared/model/StatisticalQuantities';
import { transformMonthlyAnalysisToChartData } from 'app/shared/util/ChartDataTransformer';
import { getMonthlyAnalysisQueryParams } from 'app/shared/util/QueryParamsUtil';
import axios from 'axios';
import dayjs from 'dayjs';
import 'dayjs/locale/fr';
import { capitalize } from 'lodash';
import { BlockUI } from 'primereact/blockui';
import { Card } from 'primereact/card';
import { Chart } from 'primereact/chart';
import { Column } from 'primereact/column';
import { ColumnGroup } from 'primereact/columngroup';
import { DataTable } from 'primereact/datatable';
import { ProgressSpinner } from 'primereact/progressspinner';
import { Row } from 'primereact/row';
import React, { useEffect, useState } from 'react';
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

export const MonthlyAnalysis = () => {
  const productTypesByCode: string[] = useAppSelector<string[]>(state => state.produit.productTypesByCode);
  const mouvementsStockDateRange: MouvementsStockDateRange = useAppSelector<MouvementsStockDateRange>(
    state => state.mouvementsStock.dateRange,
  );

  const now: Date = new Date();

  const [dates, setDates] = useState<Date[]>([new Date(now.getFullYear(), 0, 1), now]);
  const [minDate, setMinDate] = useState<Date>(undefined);
  const [maxDate, setMaxDate] = useState<Date>(undefined);
  const [movementType, setMovementType] = useState<string>('');
  const [productTypes, setProductTypes] = useState<string[]>([]);
  const [apiMapResponse, setApiMapResponse] = useState<ApiMapResponse>({});
  const [loadingData, setLoadingData] = useState<boolean>(false);
  const [chartData, setChartData] = useState<ChartData>({ labels: [], datasets: [] });

  const movementTypeOptions: MovementTypeOption[] = [
    { label: 'Vente', value: 'Vente' },
    { label: 'Perte', value: 'Perte' },
  ];

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

  const headerColumnGroup = (
    <ColumnGroup>
      <Row>
        <Column header="Code" rowSpan={2} />
        <Column header="Produit" rowSpan={2} />
        <Column header="% moyen vendu" rowSpan={2} />
        <Column header="Quantité moyenne vendu" rowSpan={2} />
        <Column header="Stock disponible" rowSpan={2} />
        <Column header="Nb de mouvements de type" colSpan={4} />
      </Row>
      <Row>
        <Column header="Livraison" field="nbDeliveriesStats" />
        <Column header="Vente" field="nbSalesStats" />
        <Column header="Perte" field="nbLossesStats" />
        <Column header="Inventaire" field="nbInventoriesStats" />
      </Row>
    </ColumnGroup>
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
        <div className="col-12">
          <Card title="Analyse mensuelle - Graphique de tendance">
            <Chart type="line" data={chartData} />
          </Card>
        </div>
      )}
      {Object.entries(apiMapResponse).map(([month, monthlyAnalysisStats]) => (
        <div className="col-12" key={month}>
          <Card
            title={capitalize(
              dayjs()
                .month(Number(month) - 1)
                .format('MMMM'),
            )}
          >
            <DataTable value={monthlyAnalysisStats} dataKey="productCode" headerColumnGroup={headerColumnGroup}>
              <Column field="productCode"></Column>
              <Column field="product"></Column>
              <Column field="percentageStats" body={percentageTemplate}></Column>
              <Column field="quantityStats" body={quantityTemplate}></Column>
              <Column field="availableStockStats" body={availableStockTemplate}></Column>
              <Column field="nbDeliveriesStats" body={nbDeliveriesTemplate}></Column>
              <Column field="nbSalesStats" body={nbSalesTemplate}></Column>
              <Column field="nbLossesStats" body={nbLossesTemplate}></Column>
              <Column field="nbInventoriesStats" body={nbInventoriesTemplate}></Column>
            </DataTable>
          </Card>
        </div>
      ))}
    </div>
  );
};
