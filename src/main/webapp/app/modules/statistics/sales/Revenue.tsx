import { Text } from 'app/shared/component/Text';
import { Card } from 'primereact/card';
import { Chart } from 'primereact/chart';
import { Dropdown } from 'primereact/dropdown';
import { Fieldset } from 'primereact/fieldset';
import React, { useEffect, useState } from 'react';

export const Revenue = () => {
  const [yearData, setYearData] = useState({});
  const [yearOptions, setYearOptions] = useState({});
  const [monthData2023, setMonthData2023] = useState({});
  const [monthData2024, setMonthData2024] = useState({});
  const [monthOptions, setMonthOptions] = useState({});
  const [selectedYear, setSelectedYear] = useState<number>(2023);

  useEffect(() => {
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');
    const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
    const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

    setYearData({
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
          title: {
            display: true,
            text: `Chiffre d'affaires [CHF]`,
          },
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

    setMonthData2023({
      labels: ['Jan', 'Fév', 'Mar', 'Avr', 'Mai', 'Jun', 'Jui', 'Aoû', 'Sep', 'Oct', 'Nov', 'Déc'],
      datasets: [
        {
          label: `Chiffre d'affaires`,
          data: [2969, 3822, 2875, 2595, 2796, 3675, 3366, 3036, 3043, 2810, 3803, 3026],
          fill: false,
          borderColor: documentStyle.getPropertyValue('--blue-600'),
          tension: 0.4,
        },
      ],
    });
    setMonthData2024({
      labels: ['Jan', 'Fév', 'Mar', 'Avr', 'Mai', 'Jun', 'Jui', 'Aoû', 'Sep', 'Oct', 'Nov', 'Déc'],
      datasets: [
        {
          label: `Chiffre d'affaires`,
          data: [3727, 2534, 3869, 2980, 3706, 3642, 3920, 2351, 2336, 2823, 3271, 2348],
          fill: false,
          borderColor: documentStyle.getPropertyValue('--blue-600'),
          tension: 0.4,
        },
      ],
    });
    setMonthOptions({
      plugins: {
        legend: {
          display: false,
        },
      },
      scales: {
        x: {
          ticks: {
            color: textColorSecondary,
          },
          grid: {
            color: surfaceBorder,
          },
        },
        y: {
          ticks: {
            color: textColorSecondary,
          },
          grid: {
            color: surfaceBorder,
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
        <Fieldset legend="Chiffre d'affaires" pt={{ legend: { className: 'bg-blue-800' } }}>
          <Text>{`Calcule le chiffre d'affaires total en multipliant la quantité vendue par le prix de vente.`}</Text>
        </Fieldset>
      </div>
      <div className="col-6">
        <Card title="Annuel">
          <Chart type="bar" data={yearData} options={yearOptions}></Chart>
        </Card>
      </div>
      <div className="col-6">
        <Card title={monthCardTitle}>
          <Chart type="line" data={selectedYear === 2023 ? monthData2023 : monthData2024} options={monthOptions}></Chart>
        </Card>
      </div>
    </div>
  );
};
