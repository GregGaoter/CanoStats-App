import { StatisticsCard } from 'app/shared/component/StatisticsCard';
import { MainColor } from 'app/shared/model/enumeration/MainColor';
import { Card } from 'primereact/card';
import { Chart } from 'primereact/chart';
import React, { useEffect, useState } from 'react';

export const Dashboard = () => {
  const [ventesData, setVentesData] = useState({});
  const [ventesOptions, setVentesOptions] = useState({});
  const [panierData, setPanierData] = useState({});
  const [panierOptions, setPanierOptions] = useState({});

  useEffect(() => {
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');
    const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
    const surfaceBorder = documentStyle.getPropertyValue('--surface-border');
    setVentesData({
      labels: ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet'],
      datasets: [
        {
          label: 'Fruits',
          data: [65, 59, 80, 81, 56, 55, 40],
          fill: false,
          borderColor: documentStyle.getPropertyValue('--yellow-600'),
          tension: 0.4,
        },
        {
          label: 'Légumes',
          data: [28, 48, 40, 19, 86, 27, 90],
          fill: false,
          borderColor: documentStyle.getPropertyValue('--green-600'),
          tension: 0.4,
        },
      ],
    });
    setVentesOptions({
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
    setPanierData({
      labels: ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet'],
      datasets: [
        {
          label: '2024',
          backgroundColor: documentStyle.getPropertyValue('--teal-400'),
          borderColor: documentStyle.getPropertyValue('--teal-400'),
          data: [65, 59, 80, 81, 56, 55, 40],
        },
        {
          label: '2025',
          backgroundColor: documentStyle.getPropertyValue('--teal-700'),
          borderColor: documentStyle.getPropertyValue('--teal-700'),
          data: [28, 48, 40, 19, 86, 27, 90],
        },
      ],
    });

    setPanierOptions({
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
    <div className="flex flex-column gap-2">
      <div className="grid">
        <div className="col">
          <StatisticsCard
            title="CA annuel"
            icon="sack-dollar"
            color={MainColor.BLUE}
            value="CHF 35'700"
            tagValue="1'712"
            tagSeverity="success"
          />
        </div>
        <div className="col">
          <StatisticsCard
            title="CA mensuel"
            icon="sack-dollar"
            color={MainColor.BLUE}
            value="CHF 2'975"
            tagValue="99"
            tagSeverity="danger"
          />
        </div>
        <div className="col">
          <StatisticsCard title="CA hebdo" icon="sack-dollar" color={MainColor.BLUE} value="CHF 743" tagValue="0" tagSeverity="info" />
        </div>
        <div className="col">
          <StatisticsCard
            title="CA quotidien"
            icon="sack-dollar"
            color={MainColor.BLUE}
            value="CHF 106"
            tagValue="17"
            tagSeverity="success"
          />
        </div>
      </div>
      <div className="grid">
        <div className="col">
          <StatisticsCard
            title="Marge brute annuel"
            icon="hand-holding-dollar"
            color={MainColor.PURPLE}
            value="CHF 14'994"
            tagValue="719"
            tagSeverity="success"
          />
        </div>
        <div className="col">
          <StatisticsCard
            title="Marge brute mensuel"
            icon="hand-holding-dollar"
            color={MainColor.PURPLE}
            value="CHF 1'249"
            tagValue="0"
            tagSeverity="info"
          />
        </div>
        <div className="col">
          <StatisticsCard
            title="Marge brute hebdo"
            icon="hand-holding-dollar"
            color={MainColor.PURPLE}
            value="CHF 312"
            tagValue="131"
            tagSeverity="danger"
          />
        </div>
        <div className="col">
          <StatisticsCard
            title="Marge brute quotidien"
            icon="hand-holding-dollar"
            color={MainColor.PURPLE}
            value="CHF 44"
            tagValue="18"
            tagSeverity="success"
          />
        </div>
      </div>
      <div className="grid">
        <div className="col">
          <Card title="Ventes">
            <Chart type="line" data={ventesData} options={ventesOptions}></Chart>
          </Card>
        </div>
        <div className="col">
          <Card title="Panier moyen">
            <Chart type="bar" data={panierData} options={panierOptions}></Chart>
          </Card>
        </div>
      </div>
    </div>
  );
};
