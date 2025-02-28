import { StatisticsCard } from 'app/shared/component/StatisticsCard';
import { StatisticsColor } from 'app/shared/model/enumeration/StatisticsColor';
import { Accordion, AccordionTab } from 'primereact/accordion';
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
          borderColor: documentStyle.getPropertyValue('--blue-600'),
          tension: 0.4,
        },
        {
          label: 'Légumes',
          data: [28, 48, 40, 19, 86, 27, 90],
          fill: false,
          borderColor: documentStyle.getPropertyValue('--yellow-600'),
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
      labels: ['Jan', 'Fév', 'Mar', 'Avr', 'Mai', 'Jun', 'Jui', 'Aoû', 'Sep', 'Oct', 'Nov', 'Déc'],
      datasets: [
        {
          label: '2024',
          backgroundColor: documentStyle.getPropertyValue('--blue-600'),
          borderColor: documentStyle.getPropertyValue('--blue-600'),
          data: [140, 138, 118, 128, 140, 124, 123, 136, 136, 140, 133, 124],
        },
        {
          label: '2025',
          backgroundColor: documentStyle.getPropertyValue('--yellow-600'),
          borderColor: documentStyle.getPropertyValue('--yellow-600'),
          data: [123, 120, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
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
            title={`Chiffre d'affaires`}
            icon="sack-dollar"
            color={StatisticsColor.SALES}
            value="CHF 35'700"
            tagValue="1'712"
            tagSeverity="success"
          />
        </div>
        <div className="col">
          <StatisticsCard
            title="Marge brute"
            icon="hand-holding-dollar"
            color={StatisticsColor.PROFITABILITY}
            value="CHF 14'994"
            tagValue="719"
            tagSeverity="success"
          />
        </div>
        <div className="col">
          <StatisticsCard title="Membres" icon="users" color={StatisticsColor.MEMBERS} value="196" tagValue="12" tagSeverity="danger" />
        </div>
        <div className="col">
          <StatisticsCard
            title="Adhésions"
            icon="address-card"
            color={StatisticsColor.MEMBERS}
            value="24"
            tagValue="10"
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
      <div className="grid">
        <div className="col">
          <Accordion>
            <AccordionTab header="Ventes" contentClassName="p-0">
              <ul className="list-disc">
                <li>{`Chiffre d'affaires quotidien/hebdomadaire/mensuel`}</li>
                <li>{`Quantité de produits vendus`}</li>
                <li>{`Produits les plus vendus`}</li>
                <li>{`Tendances des ventes (par période, par produit)`}</li>
              </ul>
            </AccordionTab>
            <AccordionTab header="Achats">
              <ul className="list-disc">
                <li>{`Coût total des achats`}</li>
                <li>{`Quantité de produits achetés`}</li>
                <li>{`Fournisseurs principaux`}</li>
                <li>{`Délai moyen de livraison`}</li>
              </ul>
            </AccordionTab>
            <AccordionTab header="Stocks">
              <ul className="list-disc">
                <li>{`Niveau de stock actuel pour chaque produit`}</li>
                <li>{`Produits en rupture de stock`}</li>
                <li>{`Produits en surstock`}</li>
                <li>{`Rotation des stocks`}</li>
              </ul>
            </AccordionTab>
            <AccordionTab header="Rentabilité">
              <ul className="list-disc">
                <li>{`Marge brute par produit`}</li>
                <li>{`Produits les plus rentables`}</li>
              </ul>
            </AccordionTab>
            <AccordionTab header="Clients">
              <ul className="list-disc">
                <li>{`Nombre de clients (quotidien, hebdomadaire, mensuel)`}</li>
                <li>{`Panier moyen`}</li>
                <li>{`Fidélité des clients (nombre de visites, achats récurrents)`}</li>
              </ul>
            </AccordionTab>
            <AccordionTab header="Tendances et prévisions">
              <ul className="list-disc">
                <li>{`Évolution des prix (achat et vente)`}</li>
                <li>{`Prévisions de ventes`}</li>
                <li>{`Prévisions de stock`}</li>
              </ul>
            </AccordionTab>
          </Accordion>
        </div>
      </div>
    </div>
  );
};
