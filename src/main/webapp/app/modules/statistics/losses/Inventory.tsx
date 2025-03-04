import { Icon } from 'app/shared/component/Icon';
import { Text } from 'app/shared/component/Text';
import { ApiMapResponse, IMouvementsStock } from 'app/shared/model/mouvements-stock.model';
import { getInventoryByWeightQueryParams } from 'app/shared/util/QueryParamsUtil';
import axios from 'axios';
import dayjs from 'dayjs';
import { fromPairs, keys, map, mapValues, sortBy, sumBy } from 'lodash';
import { Button } from 'primereact/button';
import { Calendar } from 'primereact/calendar';
import { Card } from 'primereact/card';
import { Chart } from 'primereact/chart';
import { Column } from 'primereact/column';
import { DataTable } from 'primereact/datatable';
import { InputNumber } from 'primereact/inputnumber';
import { ScrollPanel } from 'primereact/scrollpanel';
import { SelectButton, SelectButtonChangeEvent } from 'primereact/selectbutton';
import { SelectItem } from 'primereact/selectitem';
import { TabPanel, TabView } from 'primereact/tabview';
import { Toolbar } from 'primereact/toolbar';
import React, { useEffect, useState } from 'react';
import { apiUrl } from '../../../entities/mouvements-stock/mouvements-stock.reducer';

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

enum Display {
  CHART = 'CHART',
  TABLE = 'TABLE',
}

export const Inventory = () => {
  const [mouvement, setMouvement] = useState<number>(100);
  const [dates, setDates] = useState<Date[]>([new Date(new Date().getFullYear(), 0, 1), new Date()]);
  const [apiMapResponse, setApiMapResponse] = useState<ApiMapResponse>({});
  const [inventoryByWeightData, setInventoryByWeightData] = useState<ChartData>({ labels: [], datasets: [] });
  const [inventoryByWeightTableData, setInventoryByWeightTableData] = useState<IMouvementsStock[]>([]);
  const [exportData, setExportData] = useState<IMouvementsStock[]>([]);
  const [barOptions, setBarOptions] = useState({});
  const [loading, setLoading] = useState(false);
  const [selectedDisplay, setSelectedDisplay] = useState<Display>(Display.CHART);

  const documentStyle = getComputedStyle(document.documentElement);
  const textColor = documentStyle.getPropertyValue('--text-color');
  const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
  const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

  useEffect(() => {
    setBarOptions({
      plugins: {
        title: {
          display: true,
          text: `Inventaires en 2024 de plus de 50g d'écart avec l'entrée précédente.`,
          color: textColorSecondary,
          font: {
            size: 24,
          },
        },
        subtitle: {
          display: true,
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

  const transformApiResponseData = (data: ApiMapResponse) => {
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
        setInventoryByWeightData(transformApiResponseData(response.data));
        setInventoryByWeightTableData(getTableData(response.data));
        setExportData(getExportData(response.data));
      })
      .finally(() => setLoading(false));
  };

  const selectDisplayOptions: SelectItem[] = [
    { label: 'Graphique', value: Display.CHART },
    { label: 'Tableau', value: Display.TABLE },
  ];

  const startContent = (
    <div className="flex gap-3 align-items-end">
      <div className="flex flex-column gap-2">
        <label>Mouvement</label>
        <InputNumber prefix="≥ " suffix="g" value={mouvement} onChange={e => setMouvement(e.value)} />
      </div>
      <div className="flex flex-column gap-2">
        <label>Période</label>
        <Calendar
          value={dates}
          onChange={e => setDates(e.value)}
          selectionMode="range"
          dateFormat="dd.mm.yy"
          readOnlyInput
          hideOnRangeSelection
          showIcon
          required
        />
      </div>
      <div className="flex flex-column gap-2">
        <Button label="Appliquer" icon={<Icon icon="filter" marginRight />} loading={loading} onClick={() => fetchMouvementsStocks()} />
      </div>
    </div>
  );

  const endContent = (
    <div className="flex flex-column gap-2">
      <label>Affichage</label>
      <SelectButton
        value={selectedDisplay}
        onChange={(e: SelectButtonChangeEvent) => setSelectedDisplay(e.value)}
        options={selectDisplayOptions}
      />
    </div>
  );

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

  const exportExcel = () => {
    import('xlsx').then(xlsx => {
      const worksheet = xlsx.utils.json_to_sheet(exportData);
      const workbook = { Sheets: { data: worksheet }, SheetNames: ['Inventaires'] };
      const excelBuffer = xlsx.write(workbook, {
        bookType: 'xlsx',
        type: 'array',
      });
      saveAsExcelFile(excelBuffer, 'inventaires-50g-2024-tableau');
    });
  };

  const saveAsExcelFile = (buffer, fileName) => {
    import('file-saver').then(module => {
      if (module && module.default) {
        const EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
        const EXCEL_EXTENSION = '.xlsx';
        const data = new Blob([buffer], {
          type: EXCEL_TYPE,
        });
        module.default.saveAs(data, fileName + EXCEL_EXTENSION);
      }
    });
  };

  const header = (
    <div className="flex align-items-center justify-content-end">
      <Button type="button" icon={<Icon icon="file-excel" />} severity="success" rounded onClick={exportExcel} />
    </div>
  );

  return (
    <TabView>
      <TabPanel header="Au poids">
        <div className="grid align-items-center">
          <div className="col-12">
            <Toolbar start={startContent} end={endContent} />
          </div>
          <div className="col-12">
            <Card
            // title={`Inventaires de plus de ${mouvement}g cummulés par produit du ${dayjs(dates[0]).format('DD.MM.YYYY')} au ${dayjs(dates[1]).format('DD.MM.YYYY')}`}
            // subTitle={`Inventaires dont la différence de solde avec l'entrée précédente est de plus de ${mouvement}g.`}
            >
              {selectedDisplay === Display.CHART ? (
                <div className="flex flex-column" style={{ height: '100%' }}>
                  <ScrollPanel style={{ flexGrow: 1, height: 'auto', width: '100%' }}>
                    <Chart
                      type="bar"
                      data={inventoryByWeightData}
                      options={barOptions}
                      width={
                        inventoryByWeightData.labels.length <= 30 ? '1000px' : `${(inventoryByWeightData.labels.length * 1000) / 30}px`
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
                  header={header}
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
