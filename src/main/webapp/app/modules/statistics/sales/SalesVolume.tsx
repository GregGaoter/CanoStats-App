import { Text } from 'app/shared/component/Text';
import { Card } from 'primereact/card';
import { Chart } from 'primereact/chart';
import { Dropdown } from 'primereact/dropdown';
import { Fieldset } from 'primereact/fieldset';
import React, { useEffect, useState } from 'react';

export const SalesVolume = () => {
  const [yearData, setYearData] = useState({});
  const [monthData2023, setMonthData2023] = useState({});
  const [monthData2024, setMonthData2024] = useState({});
  const [yearOptions, setYearOptions] = useState({});
  const [monthOptions, setMonthOptions] = useState({});
  const [selectedYear, setSelectedYear] = useState<number>(2023);

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
    setMonthData2023({
      labels: ['Jan', 'Fév', 'Mar', 'Avr', 'Mai', 'Jun', 'Jui', 'Aoû', 'Sep', 'Oct', 'Nov', 'Déc'],
      datasets: [
        {
          type: 'bar',
          label: `Pommes`,
          backgroundColor: documentStyle.getPropertyValue('--blue-600'),
          borderColor: documentStyle.getPropertyValue('--surface-card'),
          borderWidth: 2,
          data: [0, 59, 80, 81, 56, 55, 40, 45, 60, 70, 75, 90],
        },
        {
          type: 'bar',
          label: `Bananes`,
          backgroundColor: documentStyle.getPropertyValue('--yellow-600'),
          borderColor: documentStyle.getPropertyValue('--surface-card'),
          borderWidth: 2,
          data: [0, 48, 40, 19, 86, 27, 90, 100, 85, 60, 50, 40],
        },
        {
          type: 'bar',
          label: `Oranges`,
          backgroundColor: documentStyle.getPropertyValue('--green-600'),
          borderColor: documentStyle.getPropertyValue('--surface-card'),
          borderWidth: 2,
          data: [35, 40, 60, 70, 46, 33, 50, 55, 65, 75, 80, 95],
        },
      ],
    });
    setMonthData2024({
      labels: ['Jan', 'Fév', 'Mar', 'Avr', 'Mai', 'Jun', 'Jui', 'Aoû', 'Sep', 'Oct', 'Nov', 'Déc'],
      datasets: [
        {
          type: 'bar',
          label: `Tomates`,
          backgroundColor: documentStyle.getPropertyValue('--blue-600'),
          borderColor: documentStyle.getPropertyValue('--blue-600'),
          data: [40, 32, 85, 29, 40, 45, 9, 16, 81, 5, 9, 42],
        },
        {
          type: 'bar',
          label: `Lait`,
          backgroundColor: documentStyle.getPropertyValue('--yellow-600'),
          borderColor: documentStyle.getPropertyValue('--yellow-600'),
          data: [6, 81, 16, 66, 37, 1, 36, 88, 27, 1, 72, 87],
        },
        {
          type: 'bar',
          label: `Pain`,
          backgroundColor: documentStyle.getPropertyValue('--green-600'),
          borderColor: documentStyle.getPropertyValue('--green-600'),
          data: [78, 46, 27, 27, 12, 90, 40, 87, 52, 89, 26, 85],
        },
      ],
    });
    setYearOptions({
      plugins: {
        legend: {
          display: false,
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
    setMonthOptions({
      plugins: {
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
        },
      },
    });
  }, []);

  const monthCardTitle = () => (
    <div className="flex justify-content-between">
      {'Mensuel'}
      <Dropdown value={selectedYear} onChange={e => setSelectedYear(e.value)} options={[2023, 2024]} />
    </div>
  );

  return (
    <div className="grid">
      <div className="col-12">
        <Fieldset legend="Volume des ventes" pt={{ legend: { className: 'bg-blue-800' } }}>
          <Text>Analyse les quantités vendues par produit pour identifier les produits les plus populaires.</Text>
        </Fieldset>
      </div>
      <div className="col-6">
        <Card title="Annuel">
          <Chart type="bar" data={yearData} options={yearOptions}></Chart>
        </Card>
      </div>
      <div className="col-6">
        <Card title={monthCardTitle}>
          <Chart type="bar" data={selectedYear === 2023 ? monthData2023 : monthData2024} options={monthOptions}></Chart>
        </Card>
      </div>
    </div>
  );
};
