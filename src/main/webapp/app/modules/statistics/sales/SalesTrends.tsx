import { Text } from 'app/shared/component/Text';
import { remove } from 'lodash';
import { Card } from 'primereact/card';
import { Chart } from 'primereact/chart';
import { Fieldset } from 'primereact/fieldset';
import { SelectButton, SelectButtonChangeEvent } from 'primereact/selectbutton';
import { SelectItem } from 'primereact/selectitem';
import { Toolbar } from 'primereact/toolbar';
import React, { useEffect, useState } from 'react';

export const SalesTrends = () => {
  const documentStyle = getComputedStyle(document.documentElement);
  const textColor = documentStyle.getPropertyValue('--text-color');
  const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
  const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

  const [dayData, setDayData] = useState<{ labels: string[]; datasets: any[] }>();
  const [weekData, setWeekData] = useState<{ labels: string[]; datasets: any[] }>();
  const [monthData, setMonthData] = useState<{ labels: string[]; datasets: any[] }>();
  const [dayData2022, setDayData2022] = useState({
    label: 2022,
    data: [70, 92, 60, 87, 78, 70, 64],
    fill: false,
    borderColor: documentStyle.getPropertyValue('--blue-400'),
    tension: 0.4,
  });
  const [weekData2022, setWeekData2022] = useState({
    label: 2022,
    data: [393, 682, 527, 474],
    fill: false,
    borderColor: documentStyle.getPropertyValue('--blue-400'),
    tension: 0.4,
  });
  const [monthData2022, setMonthData2022] = useState({
    label: 2022,
    data: [2595, 2550, 2352, 1478, 1571, 1853, 1903, 2724, 2717, 2198, 1403, 2209],
    fill: false,
    borderColor: documentStyle.getPropertyValue('--blue-400'),
    tension: 0.4,
  });
  const [dayData2023, setDayData2023] = useState({
    label: 2023,
    data: [71, 78, 52, 88, 94, 65, 91],
    fill: false,
    borderColor: documentStyle.getPropertyValue('--blue-600'),
    tension: 0.4,
  });
  const [weekData2023, setWeekData2023] = useState({
    label: 2023,
    data: [367, 379, 587, 574],
    fill: false,
    borderColor: documentStyle.getPropertyValue('--blue-600'),
    tension: 0.4,
  });
  const [monthData2023, setMonthData2023] = useState({
    label: 2023,
    data: [1843, 1426, 1769, 1990, 1443, 2799, 1537, 2024, 2341, 2476, 1763, 2299],
    fill: false,
    borderColor: documentStyle.getPropertyValue('--blue-600'),
    tension: 0.4,
  });
  const [dayData2024, setDayData2024] = useState({
    label: 2024,
    data: [58, 74, 79, 57, 51, 78, 74],
    fill: false,
    borderColor: documentStyle.getPropertyValue('--blue-800'),
    tension: 0.4,
  });
  const [weekData2024, setWeekData2024] = useState({
    label: 2024,
    data: [585, 431, 449, 582],
    fill: false,
    borderColor: documentStyle.getPropertyValue('--blue-800'),
    tension: 0.4,
  });
  const [monthData2024, setMonthData2024] = useState({
    label: 2024,
    data: [2301, 1421, 1715, 2502, 2583, 2715, 2789, 1932, 2462, 1637, 1445, 1788],
    fill: false,
    borderColor: documentStyle.getPropertyValue('--blue-800'),
    tension: 0.4,
  });
  const [lineOptions, setLineOptions] = useState({});
  const [selectedYears, setSelectedYears] = useState<number[]>([2024]);

  useEffect(() => {
    setDayData({
      labels: ['Lun', 'Mar', 'Mer', 'Jeu', 'Ven', 'Sam', 'Dim'],
      datasets: [dayData2024],
    });
    setWeekData({
      labels: ['Semaine 1', 'Semaine 2', 'Semaine 3', 'Semaine 4'],
      datasets: [weekData2024],
    });
    setMonthData({
      labels: ['Jan', 'Fév', 'Mar', 'Avr', 'Mai', 'Jun', 'Jui', 'Aoû', 'Sep', 'Oct', 'Nov', 'Déc'],
      datasets: [monthData2024],
    });
    setLineOptions({
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

    if (years.length === 0) {
      setDayData({ ...dayData, datasets: [] });
      setWeekData({ ...weekData, datasets: [] });
      setMonthData({ ...monthData, datasets: [] });
      return;
    }

    const dayDatasets = dayData.datasets;
    const weekDatasets = weekData.datasets;
    const monthDatasets = monthData.datasets;

    const dayYears: number[] = dayDatasets.map(dataset => dataset.label);
    const weekYears: number[] = weekDatasets.map(dataset => dataset.label);
    const monthYears: number[] = monthDatasets.map(dataset => dataset.label);

    if (years.includes(2022)) {
      if (!dayYears.includes(2022)) dayDatasets.push(dayData2022);
      if (!weekYears.includes(2022)) weekDatasets.push(weekData2022);
      if (!monthYears.includes(2022)) monthDatasets.push(monthData2022);
    } else {
      if (dayYears.includes(2022)) remove(dayDatasets, dataset => dataset.label === 2022);
      if (weekYears.includes(2022)) remove(weekDatasets, dataset => dataset.label === 2022);
      if (monthYears.includes(2022)) remove(monthDatasets, dataset => dataset.label === 2022);
    }
    if (years.includes(2023)) {
      if (!dayYears.includes(2023)) dayDatasets.push(dayData2023);
      if (!weekYears.includes(2023)) weekDatasets.push(weekData2023);
      if (!monthYears.includes(2023)) monthDatasets.push(monthData2023);
    } else {
      if (dayYears.includes(2023)) remove(dayDatasets, dataset => dataset.label === 2023);
      if (weekYears.includes(2023)) remove(weekDatasets, dataset => dataset.label === 2023);
      if (monthYears.includes(2023)) remove(monthDatasets, dataset => dataset.label === 2023);
    }
    if (years.includes(2024)) {
      if (!dayYears.includes(2024)) dayDatasets.push(dayData2024);
      if (!weekYears.includes(2024)) weekDatasets.push(weekData2024);
      if (!monthYears.includes(2024)) monthDatasets.push(monthData2024);
    } else {
      if (dayYears.includes(2024)) remove(dayDatasets, dataset => dataset.label === 2024);
      if (weekYears.includes(2024)) remove(weekDatasets, dataset => dataset.label === 2024);
      if (monthYears.includes(2024)) remove(monthDatasets, dataset => dataset.label === 2024);
    }

    setDayData({ ...dayData, datasets: dayDatasets });
    setWeekData({ ...weekData, datasets: weekDatasets });
    setMonthData({ ...monthData, datasets: monthDatasets });
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
    <div className="grid align-items-center">
      <div className="col-12">
        <Fieldset legend="Tendances des ventes" pt={{ legend: { className: 'bg-blue-800' } }}>
          <Text>{`Étudie les ventes sur différentes périodes (quotidienne, hebdomadaire, mensuelle) pour identifier les tendances saisonnières.`}</Text>
        </Fieldset>
      </div>
      <div className="col-6">
        <Toolbar center={centerContent} />
      </div>
      <div className="col-6">
        <Card title="Ventes quotidiennes">
          <Chart type="line" data={dayData} options={lineOptions}></Chart>
        </Card>
      </div>
      <div className="col-6">
        <Card title="Ventes hebdomadaires">
          <Chart type="line" data={weekData} options={lineOptions}></Chart>
        </Card>
      </div>
      <div className="col-6">
        <Card title="Ventes mensuelles">
          <Chart type="line" data={monthData} options={lineOptions}></Chart>
        </Card>
      </div>
    </div>
  );
};
