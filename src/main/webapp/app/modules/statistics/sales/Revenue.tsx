import { Card } from 'primereact/card';
import { Chart } from 'primereact/chart';
import React, { useEffect, useState } from 'react';

export const Revenue = () => {
  const [data, setData] = useState({});
  const [options, setOptions] = useState({});

  useEffect(() => {
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');
    const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
    const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

    setData({
      labels: ['2023', '2024', '2025'],
      datasets: [
        {
          label: `Chiffre d'affaires`,
          backgroundColor: documentStyle.getPropertyValue('--blue-600'),
          borderColor: documentStyle.getPropertyValue('--blue-600'),
          data: [50000, 60000, 70000],
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
          title={`Chiffre d'affaires`}
          subTitle={`Calcul du chiffre d'affaires total en multipliant la quantité vendue par le prix de vente`}
        >
          <Chart type="bar" data={data} options={options}></Chart>
        </Card>
      </div>
    </div>
  );
};
