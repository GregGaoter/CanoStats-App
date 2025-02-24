import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Icon } from 'app/shared/component/Icon';
import { IMouvementsStock } from 'app/shared/model/mouvements-stock.model';
import { getInventoryByWeightQueryParams } from 'app/shared/util/QueryParamsUtil';
import { Button } from 'primereact/button';
import { Calendar } from 'primereact/calendar';
import { Card } from 'primereact/card';
import { Chart } from 'primereact/chart';
import { InputNumber } from 'primereact/inputnumber';
import { TabPanel, TabView } from 'primereact/tabview';
import { Toolbar } from 'primereact/toolbar';
import React, { useEffect, useState } from 'react';
import { getEntities as getMouvementsStocks } from '../../../entities/mouvements-stock/mouvements-stock.reducer';

export const Inventory = () => {
  const [mouvement, setMouvement] = useState<number>(100);
  const [dates, setDates] = useState<Date[]>([new Date(new Date().getFullYear(), 0, 1), new Date()]);
  const [inventoryByWeightData, setInventoryByWeightData] = useState({});
  const [barOptions, setBarOptions] = useState({});

  const dispatch = useAppDispatch();

  const mouvementsStocks: IMouvementsStock[] = useAppSelector(state => state.mouvementsStock.entities);
  const loading = useAppSelector(state => state.mouvementsStock.loading);

  const documentStyle = getComputedStyle(document.documentElement);
  const textColor = documentStyle.getPropertyValue('--text-color');
  const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
  const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

  useEffect(() => {
    setBarOptions({
      plugins: {
        legend: {
          display: false,
        },
        // tooltip: {
        //   callbacks: {
        //     label: context => {
        //       let label = context.dataset.label || '';
        //       if (label) {
        //         label += ': ';
        //       }
        //       if (context.parsed.y !== null) {
        //         label += context.parsed.y;
        //       }
        //       return label;
        //     },
        //   },
        // },
      },
      scales: {
        x: {
          ticks: {
            color: textColorSecondary,
            font: {
              weight: '500',
            },
          },
          grid: {
            display: false,
          },
          border: {
            display: false,
          },
        },
        y: {
          ticks: {
            color: textColorSecondary,
          },
          grid: {
            color: surfaceBorder,
          },
          border: {
            display: false,
          },
        },
      },
    });
  }, []);

  useEffect(() => {
    if (!loading) {
      setInventoryByWeightData({
        labels: mouvementsStocks.map(m => m.codeProduit),
        datasets: [
          {
            backgroundColor: documentStyle.getPropertyValue('--blue-600'),
            borderColor: documentStyle.getPropertyValue('--blue-600'),
            data: mouvementsStocks.map(m => -m.mouvement),
          },
        ],
      });
    }
  }, [loading]);

  const fetchMouvementsStocks = () => {
    dispatch(getMouvementsStocks(getInventoryByWeightQueryParams(mouvement / 100, dates)));
  };

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
          dateFormat="dd/mm/yy"
          readOnlyInput
          hideOnRangeSelection
          showIcon
          required
        />
      </div>
      <div className="flex flex-column gap-2">
        <Button label="Appliquer" icon={<Icon icon="filter" marginRight />} onClick={() => fetchMouvementsStocks()} />
      </div>
    </div>
  );

  return (
    <TabView>
      <TabPanel header="Au poids">
        <div className="grid align-items-center">
          <div className="col-12">
            <Toolbar start={startContent} />
          </div>
          <div className="col-6">
            <Card>
              <Chart type="bar" data={inventoryByWeightData} options={barOptions}></Chart>
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
