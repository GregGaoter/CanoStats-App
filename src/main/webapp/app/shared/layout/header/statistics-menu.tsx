import { Icon } from 'app/shared/component/Icon';
import { Menu } from 'primereact/menu';
import { MenuItem } from 'primereact/menuitem';
import React from 'react';

export const StatisticsMenu = () => {
  const items: MenuItem[] = [
    { label: 'Tableau de bord', icon: <Icon icon="gauge" /> },
    {
      label: 'Ventes',
      items: [
        { label: `Chiffre d'affaires`, icon: <Icon icon="gauge" /> },
        { label: 'Volume', icon: <Icon icon="gauge" /> },
        { label: 'Tendances', icon: <Icon icon="gauge" /> },
      ],
    },
    {
      label: 'Achats',
      items: [
        { label: 'Coût', icon: <Icon icon="gauge" /> },
        { label: 'Fournisseurs', icon: <Icon icon="gauge" /> },
      ],
    },
    {
      label: 'Stocks',
      items: [
        { label: 'Rotation', icon: <Icon icon="gauge" /> },
        { label: 'Stock moyen', icon: <Icon icon="gauge" /> },
        { label: 'Prévisions', icon: <Icon icon="gauge" /> },
      ],
    },
    {
      label: 'Rentabilité',
      items: [
        { label: 'Marge brute', icon: <Icon icon="hand-holding-dollar" /> },
        { label: 'Produits les plus rentables', icon: <Icon icon="gauge" /> },
      ],
    },
    {
      label: 'Tendances',
      items: [{ label: 'Évolution des prix', icon: <Icon icon="hand-holding-dollar" /> }],
    },
  ];

  return <Menu model={items} className="surface-card" />;
};
