import { Icon } from 'app/shared/component/Icon';
import { Menubar } from 'primereact/menubar';
import { MenuItem } from 'primereact/menuitem';
import React from 'react';

export const StatisticsMenu = () => {
  const items: MenuItem[] = [
    { label: 'Tableau de bord', icon: <Icon icon="gauge" /> },
    { label: 'Pertes', icon: <Icon icon="arrow-trend-down" /> },
    { label: 'Marges', icon: <Icon icon="hand-holding-dollar" /> },
  ];

  return <Menubar model={items} />;
};
