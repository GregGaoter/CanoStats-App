import { useAppDispatch } from 'app/config/store';
import { Icon } from 'app/shared/component/Icon';
import { StatisticsColor } from 'app/shared/model/enumeration/StatisticsColor';
import { logout } from 'app/shared/reducers/authentication';
import { MegaMenu } from 'primereact/megamenu';
import { MenuItem } from 'primereact/menuitem';
import React, { memo, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';

export interface IHeaderProps {
  isAdmin: boolean;
}

const dashboardColor: string = `var(--${StatisticsColor.DASHBOARD}-600)`;
const ventesColor: string = `var(--${StatisticsColor.SALES}-600)`;
const achatsColor: string = `var(--${StatisticsColor.PURCHASES}-600)`;
const pertesColor: string = `var(--${StatisticsColor.LOSSES}-600)`;
const stocksColor: string = `var(--${StatisticsColor.STOCK}-600)`;
const rentabiliteColor: string = `var(--${StatisticsColor.PROFITABILITY}-600)`;
const membersColor: string = `var(--${StatisticsColor.MEMBERS}-600)`;
const tendancesColor: string = `var(--${StatisticsColor.TRENDS}-600)`;

const MemoizedMegaMenu = memo(MegaMenu);

const Header = (props: IHeaderProps) => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const callLogout = () => {
    dispatch(logout());
    navigate('/login', { replace: true });
  };
  // const [menuOpen, setMenuOpen] = useState(false);

  // const toggleMenu = () => setMenuOpen(!menuOpen);

  /* jhipster-needle-add-element-to-menu - JHipster will add new menu items here */

  // return (
  //   <div id="app-header">
  //     <LoadingBar className="loading-bar" />
  //     <Navbar data-cy="navbar" dark expand="md" fixed="top" className="jh-navbar">
  //       <NavbarToggler aria-label="Menu" onClick={toggleMenu} />
  //       <Brand />
  //       <Collapse isOpen={menuOpen} navbar>
  //         <Nav id="header-tabs" className="ms-auto" navbar>
  //           <Home />
  //           {props.isAuthenticated && <EntitiesMenu />}
  //           {props.isAuthenticated && props.isAdmin && <AdminMenu showOpenAPI={props.isOpenAPIEnabled} />}
  //           <AccountMenu isAuthenticated={props.isAuthenticated} />
  //         </Nav>
  //       </Collapse>
  //     </Navbar>
  //   </div>
  // );

  const items: MenuItem[] = [
    {
      label: 'Tableau de bord',
      icon: <Icon icon="gauge" color={dashboardColor} marginRight />,
      url: '/statistics/dashboard',
    },
    {
      label: 'Statistiques',
      icon: <Icon icon="chart-simple" colorSecondary marginRight />,
      items: [
        [
          {
            label: 'Ventes',
            items: [
              {
                label: `Chiffre d'affaires`,
                icon: <Icon icon="sack-dollar" color={ventesColor} marginRight />,
                url: '/statistics/revenue',
              },
              {
                label: 'Volume',
                icon: <Icon icon="cube" color={ventesColor} marginRight />,
                url: '/statistics/sales-volume',
              },
              {
                label: 'Tendances',
                icon: <Icon icon="arrow-trend-up" color={ventesColor} marginRight />,
                url: '/statistics/sales-trends',
              },
            ],
          },
          {
            label: 'Achats',
            items: [
              {
                label: 'Coût',
                icon: <Icon icon="file-invoice-dollar" color={achatsColor} marginRight />,
              },
              {
                label: 'Fournisseurs',
                icon: <Icon icon="truck" color={achatsColor} marginRight />,
              },
            ],
          },
          {
            label: 'Pertes',
            items: [
              {
                label: 'Pertes',
                icon: <Icon icon="trash-can" color={pertesColor} marginRight />,
              },
              {
                label: 'Inventaire',
                icon: <Icon icon="arrow-trend-down" color={pertesColor} marginRight />,
                url: '/statistics/inventory',
              },
            ],
          },
        ],
        [
          {
            label: 'Stocks',
            items: [
              {
                label: 'Rotation',
                icon: <Icon icon="arrows-rotate" color={stocksColor} marginRight />,
              },
              {
                label: 'Stock moyen',
                icon: <Icon icon="warehouse" color={stocksColor} marginRight />,
              },
              {
                label: 'Prévisions',
                icon: <Icon icon="boxes-stacked" color={stocksColor} marginRight />,
              },
            ],
          },
          {
            label: 'Rentabilité',
            items: [
              {
                label: 'Marge brute',
                icon: <Icon icon="hand-holding-dollar" color={rentabiliteColor} marginRight />,
              },
              {
                label: 'Produits les plus rentables',
                icon: <Icon icon="medal" color={rentabiliteColor} marginRight />,
              },
            ],
          },
        ],
        [
          {
            label: 'Membres',
            items: [
              {
                label: 'Nombre de membres',
                icon: <Icon icon="users" color={membersColor} marginRight />,
              },
              {
                label: 'Panier moyen',
                icon: <Icon icon="cart-shopping" color={membersColor} marginRight />,
              },
              {
                label: 'Adhésions',
                icon: <Icon icon="address-card" color={membersColor} marginRight />,
              },
            ],
          },
          {
            label: 'Tendances',
            items: [
              {
                label: 'Évolution des prix',
                icon: <Icon icon="arrow-trend-up" color={tendancesColor} marginRight />,
              },
            ],
          },
        ],
      ],
    },
    {
      label: 'Tables',
      icon: <Icon icon="table-list" colorSecondary marginRight />,
      items: [
        [
          {
            items: [
              {
                label: 'Mouvements stock',
                icon: <Icon icon="chart-line" colorSecondary marginRight />,
                url: 'table/mouvements-stock',
              },
            ],
          },
        ],
      ],
    },
    {
      label: 'Administration',
      icon: <Icon icon="users-cog" colorSecondary marginRight />,
      items: [
        [
          {
            items: [
              {
                label: 'Utilisateurs',
                icon: <Icon icon="users" colorSecondary marginRight />,
              },
              {
                label: 'Importation',
                icon: <Icon icon="file-import" colorSecondary marginRight />,
                url: 'admin/file-import',
              },
              {
                label: 'Métriques',
                icon: <Icon icon="tachometer-alt" colorSecondary marginRight />,
              },
              {
                label: 'Santé',
                icon: <Icon icon="heart" colorSecondary marginRight />,
              },
              {
                label: 'Configuration',
                icon: <Icon icon="cogs" colorSecondary marginRight />,
              },
              {
                label: 'Logs',
                icon: <Icon icon="tasks" colorSecondary marginRight />,
              },
            ],
          },
        ],
      ],
    },
    {
      label: 'Compte',
      icon: <Icon icon="user" colorSecondary marginRight />,
      items: [
        [
          {
            items: [
              {
                label: 'Paramètres',
                icon: <Icon icon="wrench" colorSecondary marginRight />,
              },
              {
                label: 'Mot de passe',
                icon: <Icon icon="lock" colorSecondary marginRight />,
              },
            ],
          },
        ],
      ],
    },
    {
      label: 'Se déconnecter',
      icon: <Icon icon="sign-out-alt" colorSecondary marginRight />,
      command: () => callLogout(),
    },
  ];

  const start = (
    <div className="flex align-items-center gap-2">
      <img src="content/images/logo-canopee.svg" height="50" className="my-0"></img>
      <div className="font-medium text-xl">CanoStats</div>
    </div>
  );

  return (
    <MemoizedMegaMenu
      model={items}
      start={start}
      className="fixed top-0 left-0 w-full border-noround border-none py-0 surface-card text-color-secondary"
    />
  );
};

export default Header;
