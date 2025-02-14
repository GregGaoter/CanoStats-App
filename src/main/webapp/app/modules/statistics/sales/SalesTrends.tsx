import { Text } from 'app/shared/component/Text';
import { Card } from 'primereact/card';
import { Chart } from 'primereact/chart';
import { Fieldset } from 'primereact/fieldset';
import { SelectButton, SelectButtonChangeEvent } from 'primereact/selectbutton';
import { SelectItem } from 'primereact/selectitem';
import { Toolbar } from 'primereact/toolbar';
import React, { useEffect, useState } from 'react';

export const SalesTrends = () => {
  const [dayData2022, setDayData2022] = useState({});
  const [weekData2022, setWeekData2022] = useState({});
  const [monthData2022, setMonthData2022] = useState({});
  const [dayData2023, setDayData2023] = useState({});
  const [weekData2023, setWeekData2023] = useState({});
  const [monthData2023, setMonthData2023] = useState({});
  const [dayData2024, setDayData2024] = useState({});
  const [weekData2024, setWeekData2024] = useState({});
  const [monthData2024, setMonthData2024] = useState({});
  const [lineOptions, setLineOptions] = useState({});
  const [selectedYears, setSelectedYears] = useState<number[]>([2024]);

  useEffect(() => {
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');
    const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
    const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

    setDayData2022({
      labels: ['Lun', 'Mar', 'Mer', 'Jeu', 'Ven', 'Sam', 'Dim'],
      datasets: [
        {
          label: `Ventes quotidiennes`,
          data: [70, 92, 60, 87, 78, 70, 64],
          fill: false,
          borderColor: documentStyle.getPropertyValue('--blue-600'),
          tension: 0.4,
        },
      ],
    });
    setWeekData2022({
      labels: ['Semaine 1', 'Semaine 2', 'Semaine 3', 'Semaine 4'],
      datasets: [
        {
          label: `Ventes hebdomadaires`,
          data: [393, 682, 527, 474],
          fill: false,
          borderColor: documentStyle.getPropertyValue('--blue-600'),
          tension: 0.4,
        },
      ],
    });
    setMonthData2022({
      labels: ['Jan', 'Fév', 'Mar', 'Avr', 'Mai', 'Jun', 'Jui', 'Aoû', 'Sep', 'Oct', 'Nov', 'Déc'],
      datasets: [
        {
          label: `Ventes mensuelles`,
          data: [2595, 2550, 2352, 1478, 1571, 1853, 1903, 2724, 2717, 2198, 1403, 2209],
          fill: false,
          borderColor: documentStyle.getPropertyValue('--blue-600'),
          tension: 0.4,
        },
      ],
    });
    setDayData2023({
      labels: ['Lun', 'Mar', 'Mer', 'Jeu', 'Ven', 'Sam', 'Dim'],
      datasets: [
        {
          label: `Ventes quotidiennes`,
          data: [71, 78, 52, 88, 94, 65, 91],
          fill: false,
          borderColor: documentStyle.getPropertyValue('--blue-600'),
          tension: 0.4,
        },
      ],
    });
    setWeekData2023({
      labels: ['Semaine 1', 'Semaine 2', 'Semaine 3', 'Semaine 4'],
      datasets: [
        {
          label: `Ventes hebdomadaires`,
          data: [367, 379, 587, 574],
          fill: false,
          borderColor: documentStyle.getPropertyValue('--blue-600'),
          tension: 0.4,
        },
      ],
    });
    setMonthData2023({
      labels: ['Jan', 'Fév', 'Mar', 'Avr', 'Mai', 'Jun', 'Jui', 'Aoû', 'Sep', 'Oct', 'Nov', 'Déc'],
      datasets: [
        {
          label: `Ventes mensuelles`,
          data: [1843, 1426, 1769, 1990, 1443, 2799, 1537, 2024, 2341, 2476, 1763, 2299],
          fill: false,
          borderColor: documentStyle.getPropertyValue('--blue-600'),
          tension: 0.4,
        },
      ],
    });
    setDayData2024({
      labels: ['Lun', 'Mar', 'Mer', 'Jeu', 'Ven', 'Sam', 'Dim'],
      datasets: [
        {
          label: `Ventes quotidiennes`,
          data: [58, 74, 79, 57, 51, 78, 74],
          fill: false,
          borderColor: documentStyle.getPropertyValue('--blue-600'),
          tension: 0.4,
        },
      ],
    });
    setWeekData2024({
      labels: ['Semaine 1', 'Semaine 2', 'Semaine 3', 'Semaine 4'],
      datasets: [
        {
          label: `Ventes hebdomadaires`,
          data: [585, 431, 449, 582],
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
          label: `Ventes mensuelles`,
          data: [2301, 1421, 1715, 2502, 2583, 2715, 2789, 1932, 2462, 1637, 1445, 1788],
          fill: false,
          borderColor: documentStyle.getPropertyValue('--blue-600'),
          tension: 0.4,
        },
      ],
    });
    setLineOptions({
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

  const selectYearOptions: SelectItem[] = [
    { label: '2022', value: 2022 },
    { label: '2023', value: 2023 },
    { label: '2024', value: 2024 },
  ];

  const handleSelectedYearsChange = (years: number[]) => {
    setSelectedYears(years);
  };

  const centerContent = (
    <SelectButton
      value={selectedYears}
      onChange={(e: SelectButtonChangeEvent) => handleSelectedYearsChange(e.value)}
      options={selectYearOptions}
      multiple
    />
  );

  return (
    <div className="grid">
      <div className="col-12">
        <Fieldset legend="Tendances des ventes" pt={{ legend: { className: 'bg-blue-800' } }}>
          <Text>{`Étudie les ventes sur différentes périodes (quotidienne, hebdomadaire, mensuelle) pour identifier les tendances saisonnières.`}</Text>
        </Fieldset>
      </div>
      <div className="col-12">
        <Toolbar center={centerContent} />
      </div>
      <div className="col-6">
        <Card title="Ventes quotidiennes">
          <Chart type="line" data={dayData2022} options={lineOptions}></Chart>
        </Card>
      </div>
      <div className="col-6">
        <Card title="Ventes hebdomadaires">
          <Chart type="line" data={weekData2022} options={lineOptions}></Chart>
        </Card>
      </div>
      <div className="col-6">
        <Card title="Ventes mensuelles">
          <Chart type="line" data={monthData2022} options={lineOptions}></Chart>
        </Card>
      </div>
    </div>
  );
};
