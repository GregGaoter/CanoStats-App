import { Icon } from 'app/shared/component/Icon';
import { Menu } from 'primereact/menu';
import { MenuItem } from 'primereact/menuitem';
import React from 'react';

export const StatisticsMenu = () => {
  const items: MenuItem[] = [
    { label: 'Tableau de bord', icon: <Icon icon="gauge" colorSecondary /> },
    { separator: true },
    {
      label: 'Ventes',
      items: [
        { label: `Chiffre d'affaires`, icon: <Icon icon="sack-dollar" colorSecondary /> },
        { label: 'Volume', icon: <Icon icon="cube" colorSecondary /> },
        { label: 'Tendances', icon: <Icon icon="arrow-trend-up" colorSecondary /> },
      ],
    },
    { separator: true },
    {
      label: 'Achats',
      items: [
        { label: 'Coût', icon: <Icon icon="file-invoice-dollar" colorSecondary /> },
        { label: 'Fournisseurs', icon: <Icon icon="truck" colorSecondary /> },
      ],
    },
    { separator: true },
    {
      label: 'Stocks',
      items: [
        { label: 'Rotation', icon: <Icon icon="arrows-rotate" colorSecondary /> },
        { label: 'Stock moyen', icon: <Icon icon="warehouse" colorSecondary /> },
        { label: 'Prévisions', icon: <Icon icon="boxes-stacked" colorSecondary /> },
      ],
    },
    { separator: true },
    {
      label: 'Rentabilité',
      items: [
        { label: 'Marge brute', icon: <Icon icon="hand-holding-dollar" colorSecondary /> },
        { label: 'Produits les plus rentables', icon: <Icon icon="medal" colorSecondary /> },
      ],
    },
    { separator: true },
    {
      label: 'Tendances',
      items: [{ label: 'Évolution des prix', icon: <Icon icon="arrow-trend-up" colorSecondary /> }],
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
