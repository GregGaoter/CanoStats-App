import { Text } from 'app/shared/component/Text';
import { ApiMapResponse, IMouvementsStock } from 'app/shared/model/mouvements-stock.model';
import { getInventoryByWeightQueryParams } from 'app/shared/util/QueryParamsUtil';
import axios from 'axios';
import dayjs from 'dayjs';
import { fromPairs, keys, map, mapValues, sortBy, sumBy } from 'lodash';
import { Card } from 'primereact/card';
import { Chart } from 'primereact/chart';
import { Column } from 'primereact/column';
import { DataTable } from 'primereact/datatable';
import { ScrollPanel } from 'primereact/scrollpanel';
import { TabPanel, TabView } from 'primereact/tabview';
import React, { useEffect, useState } from 'react';
import { apiUrl } from '../../../../entities/mouvements-stock/mouvements-stock.reducer';
import { Display, InventoryFilter } from './InventoryFilter';

interface ChartData {
  labels: string[];
  datasets: {
    type: string;
    label: string;
    data: number[];
    backgroundColor: string;
    borderColor: string;
    borderWidth: number;
  }[];
}

export const Inventory = () => {
  const [mouvement, setMouvement] = useState<number>(100);
  const [dates, setDates] = useState<Date[]>([new Date(new Date().getFullYear(), 0, 1), new Date()]);
  const [apiMapResponse, setApiMapResponse] = useState<ApiMapResponse>({});
  const [inventoryByWeightChartData, setInventoryByWeightChartData] = useState<ChartData>({ labels: [], datasets: [] });
  const [inventoryByWeightTableData, setInventoryByWeightTableData] = useState<IMouvementsStock[]>([]);
  const [barOptions, setBarOptions] = useState({});
  const [loading, setLoading] = useState(false);
  const [selectedDisplay, setSelectedDisplay] = useState<Display>(Display.CHART);

  const documentStyle = getComputedStyle(document.documentElement);
  const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
  const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

  useEffect(() => {
    setBarOptions({
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
        x: {
          stacked: true,
          ticks: {
            color: textColorSecondary,
            font: {
              weight: '500',
            },
            autoSkip: false,
          },
          grid: {
            display: false,
          },
          border: {
            display: false,
          },
        },
        y: {
          stacked: true,
          ticks: {
            color: textColorSecondary,
          },
          grid: {
            color: surfaceBorder,
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
  }, []);

  const sortApiResponseData = (data: ApiMapResponse): ApiMapResponse => {
    const sums = mapValues(data, mouvements => sumBy(mouvements, 'mouvement'));
    const sortedKeys = sortBy(keys(sums), key => sums[key]);
    return fromPairs(map(sortedKeys, key => [key, data[key]]));
  };

  const getChartData = (data: ApiMapResponse) => {
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
        return mouvementStock ? -mouvementStock.mouvement : 0;
      }),
      backgroundColor: documentStyle.getPropertyValue('--blue-600'),
      borderColor: documentStyle.getPropertyValue('--surface-card'),
      borderWidth: 2,
    }));

    return { labels, datasets };
  };

  const getTableData = (data: ApiMapResponse): IMouvementsStock[] => Object.values(sortApiResponseData(data)).flat();

  const getExportData = (data: ApiMapResponse): IMouvementsStock[] =>
    Object.values(sortApiResponseData(data))
      .flat()
      .map(m => ({ utilisateur: m.utilisateur, remarques: m.remarques }));

  const fetchMouvementsStocks = (): void => {
    setLoading(true);
    axios
      .get<ApiMapResponse>(`${apiUrl}/inventory-by-weight?${getInventoryByWeightQueryParams(mouvement / 1000, dates)}`, {
        timeout: 3600000,
      })
      .then(response => {
        setApiMapResponse(response.data);
        setInventoryByWeightChartData(getChartData(response.data));
        setInventoryByWeightTableData(getTableData(response.data));
      })
      .finally(() => setLoading(false));
  };

  const headerTemplate = (data: IMouvementsStock) => <Text className="font-bold">{`${data.codeProduit} - ${data.produit}`}</Text>;

  const footerTemplate = (data: IMouvementsStock) => {
    return (
      <React.Fragment>
        <td colSpan={5}>
          <div className="flex justify-content-end font-bold w-full">{`Nombre: ${apiMapResponse[data.codeProduit].length}, Mouvement total: ${sumBy(apiMapResponse[data.codeProduit], 'mouvement').toFixed(3)}kg`}</div>
        </td>
      </React.Fragment>
    );
  };

  const dateTemplate = (mouvementsStock: IMouvementsStock) => dayjs(mouvementsStock.date).format('DD.MM.YYYY HH:mm:ss');

  return (
    <TabView>
      <TabPanel header="Au poids">
        <div className="grid align-items-center">
          <div className="col-12">
            <InventoryFilter
              mouvement={mouvement}
              dates={dates}
              loadingData={loading}
              display={selectedDisplay}
              onMouvementChange={m => setMouvement(m)}
              onDatesChange={d => setDates(d)}
              onApplyFilter={() => fetchMouvementsStocks()}
              onDisplayChange={d => setSelectedDisplay(d)}
            />
          </div>
          <div className="col-12">
            <Card>
              {selectedDisplay === Display.CHART ? (
                <div className="flex flex-column" style={{ height: '100%' }}>
                  <ScrollPanel style={{ flexGrow: 1, height: 'auto', width: '100%' }}>
                    <Chart
                      type="bar"
                      data={inventoryByWeightChartData}
                      options={barOptions}
                      width={
                        inventoryByWeightChartData.labels.length <= 30
                          ? '1000px'
                          : `${(inventoryByWeightChartData.labels.length * 1000) / 30}px`
                      }
                    ></Chart>
                  </ScrollPanel>
                </div>
              ) : (
                <DataTable
                  value={inventoryByWeightTableData}
                  rowGroupMode="subheader"
                  groupRowsBy="codeProduit"
                  rowGroupHeaderTemplate={headerTemplate}
                  rowGroupFooterTemplate={footerTemplate}
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
        <p className="m-0">
          Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa
          quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas
          sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Consectetur,
          adipisci velit, sed quia non numquam eius modi.
        </p>
      </TabPanel>
    </TabView>
  );
};
