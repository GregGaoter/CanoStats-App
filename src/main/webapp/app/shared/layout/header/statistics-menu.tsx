import { Icon } from 'app/shared/component/Icon';
import { Menu } from 'primereact/menu';
import { MenuItem } from 'primereact/menuitem';
import React from 'react';

export const StatisticsMenu = () => {
  const items: MenuItem[] = [
    { label: 'Tableau de bord', icon: <Icon icon="gauge" /> },
    { separator: true },
    {
      label: 'Ventes',
      items: [
        { label: `Chiffre d'affaires`, icon: <Icon icon="sack-dollar" /> },
        { label: 'Volume', icon: <Icon icon="cube" /> },
        { label: 'Tendances', icon: <Icon icon="arrow-trend-up" /> },
      ],
    },
    { separator: true },
    {
      label: 'Achats',
      items: [
        { label: 'Coût', icon: <Icon icon="file-invoice-dollar" /> },
        { label: 'Fournisseurs', icon: <Icon icon="truck" /> },
      ],
    },
    { separator: true },
    {
      label: 'Stocks',
      items: [
        { label: 'Rotation', icon: <Icon icon="arrows-rotate" /> },
        { label: 'Stock moyen', icon: <Icon icon="warehouse" /> },
        { label: 'Prévisions', icon: <Icon icon="boxes-stacked" /> },
      ],
    },
    { separator: true },
    {
      label: 'Rentabilité',
      items: [
        { label: 'Marge brute', icon: <Icon icon="hand-holding-dollar" /> },
        { label: 'Produits les plus rentables', icon: <Icon icon="medal" /> },
      ],
    },
    { separator: true },
    {
      label: 'Tendances',
      items: [{ label: 'Évolution des prix', icon: <Icon icon="arrow-trend-up" /> }],
    },
  ];

  return (
    <Menu
      model={items}
      className="fixed border-none surface-card overflow-auto"
      style={{ top: '4rem', bottom: '1rem', left: '1rem' }}
      pt={{ submenuHeader: { className: 'surface-card text-color-secondary' }, label: { className: 'text-color-secondary' } }}
    />
  );
};
