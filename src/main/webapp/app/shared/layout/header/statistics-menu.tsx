import { Icon } from 'app/shared/component/Icon';
import { StatisticsColor } from 'app/shared/model/enumeration/StatisticsColor';
import { Menu } from 'primereact/menu';
import { MenuItem } from 'primereact/menuitem';
import React from 'react';
import { useLocation } from 'react-router-dom';

export const StatisticsMenu = () => {
  const location = useLocation();

  const dashboardColor: string = `var(--${StatisticsColor.DASHBOARD}-600)`;
  const ventesColor: string = `var(--${StatisticsColor.SALES}-600)`;
  const achatsColor: string = `var(--${StatisticsColor.PURCHASES}-600)`;
  const stocksColor: string = `var(--${StatisticsColor.INVENTORY}-600)`;
  const rentabiliteColor: string = `var(--${StatisticsColor.PROFITABILITY}-600)`;
  const membersColor: string = `var(--${StatisticsColor.MEMBERS}-600)`;
  const tendancesColor: string = `var(--${StatisticsColor.TRENDS}-600)`;
  const items: MenuItem[] = [
    {
      label: 'Tableau de bord',
      icon: <Icon icon="gauge" color={dashboardColor} marginRight />,
      url: '/statistics/dashboard',
      data: { color: StatisticsColor.DASHBOARD },
    },
    { separator: true },
    {
      label: 'Ventes',
      items: [
        {
          label: `Chiffre d'affaires`,
          icon: <Icon icon="sack-dollar" color={ventesColor} marginRight />,
          url: '/statistics/revenue',
          data: { color: StatisticsColor.SALES },
        },
        {
          label: 'Volume',
          icon: <Icon icon="cube" color={ventesColor} marginRight />,
          url: '/statistics/sales-volume',
          data: { color: StatisticsColor.SALES },
        },
        {
          label: 'Tendances',
          icon: <Icon icon="arrow-trend-up" color={ventesColor} marginRight />,
          url: '/statistics/sales-trends',
          data: { color: StatisticsColor.SALES },
        },
      ],
    },
    { separator: true },
    {
      label: 'Achats',
      items: [
        {
          label: 'Coût',
          icon: <Icon icon="file-invoice-dollar" color={achatsColor} marginRight />,
          data: { color: StatisticsColor.PURCHASES },
        },
        {
          label: 'Fournisseurs',
          icon: <Icon icon="truck" color={achatsColor} marginRight />,
          data: { color: StatisticsColor.PURCHASES },
        },
      ],
    },
    { separator: true },
    {
      label: 'Stocks',
      items: [
        {
          label: 'Rotation',
          icon: <Icon icon="arrows-rotate" color={stocksColor} marginRight />,
          data: { color: StatisticsColor.INVENTORY },
        },
        {
          label: 'Stock moyen',
          icon: <Icon icon="warehouse" color={stocksColor} marginRight />,
          data: { color: StatisticsColor.INVENTORY },
        },
        {
          label: 'Prévisions',
          icon: <Icon icon="boxes-stacked" color={stocksColor} marginRight />,
          data: { color: StatisticsColor.INVENTORY },
        },
      ],
    },
    { separator: true },
    {
      label: 'Rentabilité',
      items: [
        {
          label: 'Marge brute',
          icon: <Icon icon="hand-holding-dollar" color={rentabiliteColor} marginRight />,
          data: { color: StatisticsColor.PROFITABILITY },
        },
        {
          label: 'Produits les plus rentables',
          icon: <Icon icon="medal" color={rentabiliteColor} marginRight />,
          data: { color: StatisticsColor.PROFITABILITY },
        },
      ],
    },
    { separator: true },
    {
      label: 'Membres',
      items: [
        {
          label: 'Nombre de membres',
          icon: <Icon icon="users" color={membersColor} marginRight />,
          data: { color: StatisticsColor.MEMBERS },
        },
        {
          label: 'Panier moyen',
          icon: <Icon icon="cart-shopping" color={membersColor} marginRight />,
          data: { color: StatisticsColor.MEMBERS },
        },
        {
          label: 'Adhésions',
          icon: <Icon icon="address-card" color={membersColor} marginRight />,
          data: { color: StatisticsColor.MEMBERS },
        },
      ],
    },
    { separator: true },
    {
      label: 'Tendances',
      items: [
        {
          label: 'Évolution des prix',
          icon: <Icon icon="arrow-trend-up" color={tendancesColor} marginRight />,
          data: { color: StatisticsColor.TRENDS },
        },
      ],
    },
  ];

  const getMenuItemClass = (item: MenuItem): string => {
    return item.url === location.pathname ? `bg-${item.data.color}-900` : '';
  };

  return (
    <Menu
      model={items?.map(group => ({
        ...group,
        className: getMenuItemClass(group),
        items: group.items?.map((item: MenuItem) => ({ ...item, className: getMenuItemClass(item) })),
      }))}
      className="fixed border-none surface-card overflow-auto"
      style={{ top: '4rem', bottom: '1rem', left: '1rem' }}
    />
  );
};
