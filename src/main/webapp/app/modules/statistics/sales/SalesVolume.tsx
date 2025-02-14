import { Text } from 'app/shared/component/Text';
import { Card } from 'primereact/card';
import { Chart } from 'primereact/chart';
import { Fieldset } from 'primereact/fieldset';
import React, { useEffect, useState } from 'react';

export const SalesVolume = () => {
  const [yearData, setYearData] = useState({});
  const [monthData, setMonthData] = useState({});
  const [barOptions, setBarOptions] = useState({});

  useEffect(() => {
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');
    const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
    const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

    setYearData({
      labels: ['Pommes', 'Bananes', 'Oranges', 'Tomates', 'Lait', 'Pain'],
      datasets: [
        {
          label: `Volume des ventes`,
          backgroundColor: documentStyle.getPropertyValue('--blue-600'),
          borderColor: documentStyle.getPropertyValue('--blue-600'),
          data: [200, 150, 120, 100, 90, 80],
        },
      ],
    });
    setMonthData({
      labels: ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre'],
      datasets: [
        {
          label: `Pommes`,
          backgroundColor: documentStyle.getPropertyValue('--blue-400'),
          borderColor: documentStyle.getPropertyValue('--blue-400'),
          data: [65, 59, 80, 81, 56, 55, 40, 45, 60, 70, 75, 90],
        },
        {
          label: `Bananes`,
          backgroundColor: documentStyle.getPropertyValue('--blue-600'),
          borderColor: documentStyle.getPropertyValue('--blue-600'),
          data: [28, 48, 40, 19, 86, 27, 90, 100, 85, 60, 50, 40],
        },
        {
          label: `Oranges`,
          backgroundColor: documentStyle.getPropertyValue('--blue-800'),
          borderColor: documentStyle.getPropertyValue('--blue-800'),
          data: [35, 40, 60, 70, 46, 33, 50, 55, 65, 75, 80, 95],
        },
      ],
    });
    setBarOptions({
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
      <div className="col-12">
        <Fieldset legend="Volume des ventes" pt={{ legend: { className: 'bg-blue-800' } }}>
          <Text>Analyse les quantités vendues par produit pour identifier les produits les plus populaires.</Text>
        </Fieldset>
      </div>
      <div className="col-6">
        <Card title="Annuel">
          <Chart type="bar" data={yearData} options={barOptions}></Chart>
        </Card>
      </div>
      <div className="col-6">
        <Card title="Mensuel">
          <Chart type="bar" data={monthData} options={barOptions}></Chart>
        </Card>
      </div>
    </div>
  );
};
