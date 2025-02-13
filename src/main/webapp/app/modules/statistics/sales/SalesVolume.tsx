import { Card } from 'primereact/card';
import { Chart } from 'primereact/chart';
import React, { useEffect, useState } from 'react';

export const SalesVolume = () => {
  const [data, setData] = useState({});
  const [options, setOptions] = useState({});

  useEffect(() => {
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');
    const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
    const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

    setData({
      labels: ['Pommes', 'Bananes', 'Oranges', 'Tomates', 'Lait', 'Pain'],
      datasets: [
        {
          label: `Volume des ventes`,
          backgroundColor: documentStyle.getPropertyValue('--blue-600'),
          borderColor: documentStyle.getPropertyValue('--blue-600'),
          data: [150, 200, 100, 80, 120, 90],
        },
      ],
    });

    setOptions({
      plugins: {
        legend: {
          labels: {
            color: textColor,
          },
        },
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

  return (
    <div className="grid">
      <div className="col">
        <Card
          title={`Volume des ventes`}
          subTitle={`Analyse des quantités vendues par produit pour identifier les produits les plus populaires`}
        >
          <Chart type="bar" data={data} options={options}></Chart>
        </Card>
      </div>
    </div>
  );
};
