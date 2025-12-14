import { useAppSelector } from 'app/config/store';
import { apiUrl } from 'app/entities/mouvements-stock/mouvements-stock.reducer';
import { Text } from 'app/shared/component/Text';
import { MonthlyAnalysisResult } from 'app/shared/model/MonthlyAnalysisResult';
import { getMonthlyAnalysisQueryParams } from 'app/shared/util/QueryParamsUtil';
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
import { MonthlyAnalysisFilter } from './MonthlyAnalysisFilter';

interface ApiMapResponse {
  [month: number]: MonthlyAnalysisResult[];
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
  const productTypeOptions = useAppSelector(state => state.produit.productTypesByCode);
  const mouvementsStockDateRange = useAppSelector(state => state.mouvementsStock.dateRange);

  const [dates, setDates] = useState<Date[]>([new Date(new Date().getFullYear(), 0, 1), new Date()]);
  const [movementType, setMovementType] = useState<string>('');
  const [productTypes, setProductTypes] = useState<string[]>([]);
  const [apiMapResponse, setApiMapResponse] = useState<ApiMapResponse>({});
  const [loadingData, setLoadingData] = useState<boolean>(false);

  const movementTypeOptions: MovementTypeOption[] = [
    { label: 'Vente', value: 'Vente' },
    { label: 'Perte', value: 'Perte' },
  ];

  dayjs.locale('fr');

  const getMonthlyAnalysis = (): void => {
    setLoadingData(true);
    setApiMapResponse({});
    axios
      .get<ApiMapResponse>(`${apiUrl}/monthly-analysis?${getMonthlyAnalysisQueryParams(movementType, productTypes, dates)}`, {
        timeout: 3600000,
      })
      .then(response => setApiMapResponse(response.data))
      .finally(() => setLoadingData(false));
  };

  const soldPercentageTemplate = (data: MonthlyAnalysisResult) => (
    <Text>{`${Math.round(data.percentageAverage).toString()} % ± ${Math.round(data.percentageStandardDeviation).toString()} %`}</Text>
  );

  const soldQuantityTemplate = (data: MonthlyAnalysisResult) => {
    const unit: string = data.unit === 'Au poids' ? 'kg' : 'pièces';
    return (
      <Text>{`${Math.ceil(data.quantityAverage).toString()} ${unit} ± ${Math.ceil(data.quantityStandardDeviation).toString()} ${unit}`}</Text>
    );
  };

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
            loadingData={loadingData}
            onMovementTypeChange={mt => setMovementType(mt)}
            onProductTypesChange={pt => setProductTypes(pt)}
            onDatesChange={d => setDates(d)}
            onApplyFilter={() => getMonthlyAnalysis()}
          />
        </BlockUI>
      </div>
      {Object.entries(apiMapResponse).map(([month, monthlyAnalysisResults]) => (
        <div className="col-12" key={month}>
          <Card
            title={capitalize(
              dayjs()
                .month(Number(month) - 1)
                .format('MMMM'),
            )}
          >
            <DataTable value={monthlyAnalysisResults} dataKey="productCode">
              <Column field="productCode" header="Code"></Column>
              <Column field="product" header="Produit"></Column>
              <Column field="soldPercentageAverage" header="% moyen vendu" body={soldPercentageTemplate}></Column>
              <Column field="soldQuantityAverage" header="Quantité moyenne vendu" body={soldQuantityTemplate}></Column>
            </DataTable>
          </Card>
        </div>
      ))}
    </div>
  );
};
