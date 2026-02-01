import { useAppDispatch } from 'app/config/store';
import { Icon } from 'app/shared/component/Icon';
import { StatisticsColor } from 'app/shared/model/enumeration/StatisticsColor';
import { logout } from 'app/shared/reducers/authentication';
import { Button } from 'primereact/button';
import { Menubar } from 'primereact/menubar';
import { MenuItem } from 'primereact/menuitem';
import React from 'react';
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
      command: () => navigate('/', { replace: true }),
    },
    {
      label: 'Statistiques',
      icon: <Icon icon="chart-simple" colorSecondary marginRight />,
      items: [
        {
          label: 'Mouvements de stock',
          items: [
            {
              label: 'Mouvements mensuels',
              icon: <Icon icon="chart-line" colorSecondary marginRight />,
              command: () => navigate('/statistics/stock/movements-monthly', { replace: true }),
            },
            {
              label: 'Inventaires et anomalies',
              icon: <Icon icon="arrow-trend-down" colorSecondary marginRight />,
              command: () => navigate('/statistics/stock/inventory-anomalies', { replace: true }),
            },
          ],
        },
      ],
    },
    {
      label: 'Tables',
      icon: <Icon icon="table-list" colorSecondary marginRight />,
      items: [
        {
          label: 'Mouvements stock',
          icon: <Icon icon="chart-line" colorSecondary marginRight />,
          command: () => navigate('/table/mouvements-stock', { replace: true }),
        },
        {
          label: 'Produits',
          icon: <Icon icon="carrot" colorSecondary marginRight />,
          command: () => navigate('/table/produit', { replace: true }),
        },
      ],
    },
    {
      label: 'Administration',
      icon: <Icon icon="users-cog" colorSecondary marginRight />,
      items: [
        {
          label: 'Importation',
          icon: <Icon icon="file-import" colorSecondary marginRight />,
          command: () => navigate('/admin/file-import', { replace: true }),
        },
      ],
    },
  ];

  const start = (
    <div className="flex align-items-center gap-2">
      <img src="content/images/logo-canopee.svg" height="50" className="my-0"></img>
      <div className="font-medium text-xl">CanoStats</div>
    </div>
  );

  const end = (
    <Button
      icon={<Icon icon="sign-out-alt" color="var(--red-600)" marginRight />}
      label="Se déconnecter"
      text
      pt={{ label: { className: 'text-red-600' } }}
      onClick={() => callLogout()}
    />
  );

  return (
    <Menubar
      model={items}
      start={start}
      end={end}
      className="fixed top-0 left-0 w-full border-noround border-none py-0 surface-card text-color-secondary"
    />
  );
};

export default Header;
