import { Icon } from 'app/shared/component/Icon';
import { Menu } from 'primereact/menu';
import { MenuItem } from 'primereact/menuitem';
import React from 'react';

export const StatisticsMenu = () => {
  const dashboardColor: string = 'var(--bluegray-600)';
  const ventesColor: string = 'var(--blue-600)';
  const achatsColor: string = 'var(--green-600)';
  const stocksColor: string = 'var(--yellow-600)';
  const rentabiliteColor: string = 'var(--purple-600)';
  const tendancesColor: string = 'var(--red-600)';
  const items: MenuItem[] = [
    { label: 'Tableau de bord', icon: <Icon icon="gauge" color={dashboardColor} marginRight />, url: 'statistics/dashboard' },
    { separator: true },
    {
      label: 'Ventes',
      items: [
        { label: `Chiffre d'affaires`, icon: <Icon icon="sack-dollar" color={ventesColor} marginRight />, url: 'statistics/revenue' },
        { label: 'Volume', icon: <Icon icon="cube" color={ventesColor} marginRight />, url: 'statistics/sales-volume' },
        { label: 'Tendances', icon: <Icon icon="arrow-trend-up" color={ventesColor} marginRight />, url: 'statistics/sales-trends' },
      ],
    },
    { separator: true },
    {
      label: 'Achats',
      items: [
        { label: 'Coût', icon: <Icon icon="file-invoice-dollar" color={achatsColor} marginRight /> },
        { label: 'Fournisseurs', icon: <Icon icon="truck" color={achatsColor} marginRight /> },
      ],
    },
    { separator: true },
    {
      label: 'Stocks',
      items: [
        { label: 'Rotation', icon: <Icon icon="arrows-rotate" color={stocksColor} marginRight /> },
        { label: 'Stock moyen', icon: <Icon icon="warehouse" color={stocksColor} marginRight /> },
        { label: 'Prévisions', icon: <Icon icon="boxes-stacked" color={stocksColor} marginRight /> },
      ],
    },
    { separator: true },
    {
      label: 'Rentabilité',
      items: [
        { label: 'Marge brute', icon: <Icon icon="hand-holding-dollar" color={rentabiliteColor} marginRight /> },
        { label: 'Produits les plus rentables', icon: <Icon icon="medal" color={rentabiliteColor} marginRight /> },
      ],
    },
    { separator: true },
    {
      label: 'Tendances',
      items: [{ label: 'Évolution des prix', icon: <Icon icon="arrow-trend-up" color={tendancesColor} marginRight /> }],
    },
  ];

  return (
    <Menu model={items} className="fixed border-none surface-card overflow-auto" style={{ top: '4rem', bottom: '1rem', left: '1rem' }} />
  );
};
