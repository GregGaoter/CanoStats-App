import { Text } from 'app/shared/component/Text';
import { ApiMapResponse, IMouvementsStock } from 'app/shared/model/mouvements-stock.model';
import { getInventoryByPieceQueryParams, getInventoryByWeightQueryParams } from 'app/shared/util/QueryParamsUtil';
import axios from 'axios';
import dayjs from 'dayjs';
import { drop, fromPairs, keys, map, mapValues, sortBy, sumBy, take } from 'lodash';
import { BlockUI } from 'primereact/blockui';
import { Card } from 'primereact/card';
import { Chart } from 'primereact/chart';
import { Column } from 'primereact/column';
import { DataTable } from 'primereact/datatable';
import { Paginator, PaginatorPageChangeEvent } from 'primereact/paginator';
import { ProgressSpinner } from 'primereact/progressspinner';
import { TabPanel, TabView } from 'primereact/tabview';
import React, { useEffect, useState } from 'react';
import { apiUrl } from '../../../../entities/mouvements-stock/mouvements-stock.reducer';
import { InventoryByPieceFilter } from './InventoryByPieceFilter';
import { Display, InventoryByWeightFilter } from './InventoryByWeightFilter';

interface ChartDatasets {
  type: string;
  label: string;
  data: number[];
  backgroundColor: string;
  borderColor: string;
  borderWidth: number;
}

interface ChartData {
  labels: string[];
  datasets: ChartDatasets[];
}

export const Inventory = () => {
  const chartDatasetsSize: number = 40;

  const [mouvementByWeight, setMouvementByWeight] = useState<number>(100);
  const [datesByWeight, setDatesByWeight] = useState<Date[]>([new Date(new Date().getFullYear(), 0, 1), new Date()]);
  const [apiMapResponseByWeight, setApiMapResponseByWeight] = useState<ApiMapResponse>({});
  const [inventoryByWeightChartData, setInventoryByWeightChartData] = useState<ChartData>({ labels: [], datasets: [] });
  const [inventoryByWeightChartDataPaginate, setInventoryByWeightChartDataPaginate] = useState<ChartData>({ labels: [], datasets: [] });
  const [inventoryByWeightTableData, setInventoryByWeightTableData] = useState<IMouvementsStock[]>([]);
  const [barOptionsByWeight, setBarOptionsByWeight] = useState({});
  const [selectedDisplayByWeight, setSelectedDisplayByWeight] = useState<Display>(Display.CHART);

  const [mouvementByPiece, setMouvementByPiece] = useState<number>(5);
  const [datesByPiece, setDatesByPiece] = useState<Date[]>([new Date(new Date().getFullYear(), 0, 1), new Date()]);
  const [apiMapResponseByPiece, setApiMapResponseByPiece] = useState<ApiMapResponse>({});
  const [inventoryByPieceChartData, setInventoryByPieceChartData] = useState<ChartData>({ labels: [], datasets: [] });
  const [inventoryByPieceChartDataPaginate, setInventoryByPieceChartDataPaginate] = useState<ChartData>({ labels: [], datasets: [] });
  const [inventoryByPieceTableData, setInventoryByPieceTableData] = useState<IMouvementsStock[]>([]);
  const [barOptionsByPiece, setBarOptionsByPiece] = useState({});
  const [selectedDisplayByPiece, setSelectedDisplayByPiece] = useState<Display>(Display.CHART);

  const [loadingData, setLoadingData] = useState<boolean>(false);

  const [firstByWeightChartData, setFirstByWeightChartData] = useState<number>(0);
  const [firstByPieceChartData, setFirstByPieceChartData] = useState<number>(0);

  const documentStyle = getComputedStyle(document.documentElement);
  const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
  const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

  useEffect(() => {
    setBarOptionsByWeight({
      indexAxis: 'y',
      maintainAspectRatio: false,
      plugins: {
        title: {
          display: false,
          text: `Inventaires en 2024 de plus de 50g d'écart avec l'entrée précédente.`,
          color: textColorSecondary,
          font: {
            size: 24,
          },
        },
        subtitle: {
          display: false,
          text: `Chaque barre représente les inventaires cumulés par produit. Les inventaires de plus de 17kg ne sont pas pris en compte.`,
          color: textColorSecondary,
          font: {
            size: 20,
          },
        },
        legend: {
          display: false,
        },
      },
      scales: {
        y: {
          stacked: true,
          ticks: {
            color: textColorSecondary,
            font: {
              weight: '500',
            },
            autoSkip: false,
          },
          grid: {
            color: surfaceBorder,
          },
          border: {
            display: false,
          },
        },
        x: {
          stacked: true,
          ticks: {
            color: textColorSecondary,
          },
          grid: {
            color: context => (context.tick.value === 0 ? 'red' : surfaceBorder),
          },
          border: {
            display: false,
          },
          title: {
            display: true,
            text: 'Poids [kg]',
            color: textColorSecondary,
            size: 16,
          },
        },
      },
    });
    setBarOptionsByPiece({
      indexAxis: 'y',
      maintainAspectRatio: false,
      plugins: {
        title: {
          display: false,
          text: `Inventaires en 2024 de plus de 50g d'écart avec l'entrée précédente.`,
          color: textColorSecondary,
          font: {
            size: 24,
          },
        },
        subtitle: {
          display: false,
          text: `Chaque barre représente les inventaires cumulés par produit. Les inventaires de plus de 17kg ne sont pas pris en compte.`,
          color: textColorSecondary,
          font: {
            size: 20,
          },
        },
        legend: {
          display: false,
        },
      },
      scales: {
        y: {
          stacked: true,
          ticks: {
            color: textColorSecondary,
            font: {
              weight: '500',
            },
            autoSkip: false,
          },
          grid: {
            color: surfaceBorder,
          },
          border: {
            display: false,
          },
        },
        x: {
          stacked: true,
          ticks: {
            color: textColorSecondary,
          },
          grid: {
            color: context => (context.tick.value === 0 ? 'red' : surfaceBorder),
          },
          border: {
            display: false,
          },
          title: {
            display: true,
            text: 'Pièces',
            color: textColorSecondary,
            size: 16,
          },
        },
      },
    });
  }, []);

  const sortApiResponseData = (data: ApiMapResponse): ApiMapResponse => {
    const sums = mapValues(data, mouvements => sumBy(mouvements, 'mouvement'));
    const sortedKeys = sortBy(keys(sums), key => sums[key]);
    return fromPairs(map(sortedKeys, key => [key, data[key]]));
  };

  const getChartData = (data: ApiMapResponse): ChartData => {
    const sortedData = sortApiResponseData(data);
    const labels: string[] = Object.keys(sortedData);
    const ids: number[] = Array.from(
      new Set(
        Object.values(sortedData)
          .flat()
          .map(ms => ms.epicerioId),
      ),
    );
    const datasets = ids.map(id => ({
      type: 'bar',
      label: `${id}`,
      data: labels.map(codeProduit => {
        const mouvementStock: IMouvementsStock = sortedData[codeProduit].find(ms => ms.epicerioId === id);
        return mouvementStock ? mouvementStock.mouvement : 0;
      }),
      backgroundColor: documentStyle.getPropertyValue('--blue-600'),
      borderColor: documentStyle.getPropertyValue('--surface-card'),
      borderWidth: 2,
    }));

    return { labels, datasets };
  };

  const getChartDataPaginate = (chartData: ChartData, firstIndex: number): ChartData => {
    const labelsDropped: string[] = drop(chartData.labels, firstIndex);
    const datasetsDropped: ChartDatasets[] = chartData.datasets.map(dataset => ({ ...dataset, data: drop(dataset.data, firstIndex) }));
    if (labelsDropped.length < chartDatasetsSize) {
      return { labels: labelsDropped, datasets: datasetsDropped };
    } else {
      return {
        labels: take(labelsDropped, chartDatasetsSize),
        datasets: datasetsDropped.map(dataset => ({ ...dataset, data: take(dataset.data, chartDatasetsSize) })),
      };
    }
  };

  const getTableData = (data: ApiMapResponse): IMouvementsStock[] => Object.values(sortApiResponseData(data)).flat();

  const fetchMouvementsStocksByWeight = (): void => {
    setLoadingData(true);
    setApiMapResponseByWeight({});
    setInventoryByWeightChartData({ labels: [], datasets: [] });
    setInventoryByWeightChartDataPaginate({ labels: [], datasets: [] });
    setInventoryByWeightTableData([]);
    axios
      .get<ApiMapResponse>(`${apiUrl}/inventory?${getInventoryByWeightQueryParams(mouvementByWeight / 1000, datesByWeight)}`, {
        timeout: 3600000,
      })
      .then(response => {
        setApiMapResponseByWeight(response.data);
        const chartData: ChartData = getChartData(response.data);
        setInventoryByWeightChartData(chartData);
        setInventoryByWeightChartDataPaginate(getChartDataPaginate(chartData, firstByWeightChartData));
        setInventoryByWeightTableData(getTableData(response.data));
      })
      .finally(() => setLoadingData(false));
  };

  const fetchMouvementsStocksByPiece = (): void => {
    setLoadingData(true);
    setApiMapResponseByPiece({});
    setInventoryByPieceChartData({ labels: [], datasets: [] });
    setInventoryByPieceChartDataPaginate({ labels: [], datasets: [] });
    setInventoryByPieceTableData([]);
    axios
      .get<ApiMapResponse>(`${apiUrl}/inventory?${getInventoryByPieceQueryParams(mouvementByPiece, datesByPiece)}`, {
        timeout: 3600000,
      })
      .then(response => {
        setApiMapResponseByPiece(response.data);
        const chartData: ChartData = getChartData(response.data);
        setInventoryByPieceChartData(chartData);
        setInventoryByPieceChartDataPaginate(getChartDataPaginate(chartData, firstByWeightChartData));
        setInventoryByPieceTableData(getTableData(response.data));
      })
      .finally(() => setLoadingData(false));
  };

  const headerTemplate = (data: IMouvementsStock) => <Text className="font-bold">{`${data.codeProduit} - ${data.produit}`}</Text>;

  const footerTemplateByWeight = (data: IMouvementsStock) => {
    return (
      <React.Fragment>
        <td colSpan={5}>
          <div className="flex justify-content-end font-bold w-full">{`Nombre: ${apiMapResponseByWeight[data.codeProduit].length}, Mouvement total: ${sumBy(apiMapResponseByWeight[data.codeProduit], 'mouvement').toFixed(3)}kg`}</div>
        </td>
      </React.Fragment>
    );
  };

  const footerTemplateByPiece = (data: IMouvementsStock) => {
    return (
      <React.Fragment>
        <td colSpan={5}>
          <div className="flex justify-content-end font-bold w-full">{`Nombre: ${apiMapResponseByPiece[data.codeProduit].length}, Mouvement total: ${sumBy(apiMapResponseByPiece[data.codeProduit], 'mouvement')} pièces`}</div>
        </td>
      </React.Fragment>
    );
  };

  const handlePageByWeightChange = (event: PaginatorPageChangeEvent): void => {
    setFirstByWeightChartData(event.first);
    setInventoryByWeightChartDataPaginate(getChartDataPaginate(inventoryByWeightChartData, event.first));
  };

  const handlePageByPieceChange = (event: PaginatorPageChangeEvent): void => {
    setFirstByPieceChartData(event.first);
    setInventoryByPieceChartDataPaginate(getChartDataPaginate(inventoryByPieceChartData, event.first));
  };

  const dateTemplate = (mouvementsStock: IMouvementsStock) => dayjs(mouvementsStock.date).format('DD.MM.YYYY HH:mm:ss');

  return (
    <TabView>
      <TabPanel header="Au poids">
        <div className="grid align-items-center">
          <div className="col-12">
            <BlockUI blocked={loadingData} template={<ProgressSpinner />}>
              <InventoryByWeightFilter
                mouvement={mouvementByWeight}
                dates={datesByWeight}
                loadingData={loadingData}
                display={selectedDisplayByWeight}
                onMouvementChange={m => setMouvementByWeight(m)}
                onDatesChange={d => setDatesByWeight(d)}
                onApplyFilter={() => fetchMouvementsStocksByWeight()}
                onDisplayChange={d => setSelectedDisplayByWeight(d)}
              />
            </BlockUI>
          </div>
          <div className="col-12">
            <Card>
              {selectedDisplayByWeight === Display.CHART ? (
                // <div className="flex flex-column" style={{ height: '100%' }}>
                //   <ScrollPanel style={{ flexGrow: 1, height: 'auto', width: '100%' }}>
                //     <Chart
                //       type="bar"
                //       data={inventoryByWeightChartData}
                //       options={barOptionsByWeight}
                //       height={
                //         inventoryByWeightChartData.labels.length <= 40
                //           ? 'auto'
                //           : `${(inventoryByWeightChartData.labels.length * 1000) / 40}px`
                //       }
                //     ></Chart>
                //   </ScrollPanel>
                // </div>
                <div className="flex flex-column">
                  <Chart type="bar" data={inventoryByWeightChartDataPaginate} options={barOptionsByWeight} height="600px"></Chart>
                  <Paginator
                    first={firstByWeightChartData}
                    rows={chartDatasetsSize}
                    totalRecords={inventoryByWeightChartData.labels.length}
                    onPageChange={handlePageByWeightChange}
                  />
                </div>
              ) : (
                <DataTable
                  value={inventoryByWeightTableData}
                  rowGroupMode="subheader"
                  groupRowsBy="codeProduit"
                  rowGroupHeaderTemplate={headerTemplate}
                  rowGroupFooterTemplate={footerTemplateByWeight}
                  dataKey="id"
                  scrollable
                  scrollHeight="600px"
                >
                  <Column field="date" header="Date" body={dateTemplate} style={{ width: '190px' }}></Column>
                  <Column field="mouvement" header="Mouvement"></Column>
                  <Column field="solde" header="Solde"></Column>
                  <Column field="utilisateur" header="Utilisateur"></Column>
                  <Column field="remarques" header="Remarques"></Column>
                </DataTable>
              )}
            </Card>
          </div>
        </div>
      </TabPanel>
      <TabPanel header="A la pièce">
        <div className="grid align-items-center">
          <div className="col-12">
            <BlockUI blocked={loadingData} template={<ProgressSpinner />}>
              <InventoryByPieceFilter
                mouvement={mouvementByPiece}
                dates={datesByPiece}
                loadingData={loadingData}
                display={selectedDisplayByPiece}
                onMouvementChange={m => setMouvementByPiece(m)}
                onDatesChange={d => setDatesByPiece(d)}
                onApplyFilter={() => fetchMouvementsStocksByPiece()}
                onDisplayChange={d => setSelectedDisplayByPiece(d)}
              />
            </BlockUI>
          </div>
          <div className="col-12">
            <Card>
              {selectedDisplayByPiece === Display.CHART ? (
                <div className="flex flex-column">
                  <Chart type="bar" data={inventoryByPieceChartDataPaginate} options={barOptionsByPiece} height="600px"></Chart>
                  <Paginator
                    first={firstByPieceChartData}
                    rows={chartDatasetsSize}
                    totalRecords={inventoryByPieceChartData.labels.length}
                    onPageChange={handlePageByPieceChange}
                  />
                </div>
              ) : (
                <DataTable
                  value={inventoryByPieceTableData}
                  rowGroupMode="subheader"
                  groupRowsBy="codeProduit"
                  rowGroupHeaderTemplate={headerTemplate}
                  rowGroupFooterTemplate={footerTemplateByPiece}
                  dataKey="id"
                  scrollable
                  scrollHeight="600px"
                >
                  <Column field="date" header="Date" body={dateTemplate} style={{ width: '190px' }}></Column>
                  <Column field="mouvement" header="Mouvement"></Column>
                  <Column field="solde" header="Solde"></Column>
                  <Column field="utilisateur" header="Utilisateur"></Column>
                  <Column field="remarques" header="Remarques"></Column>
                </DataTable>
              )}
            </Card>
          </div>
        </div>
      </TabPanel>
    </TabView>
  );
};
