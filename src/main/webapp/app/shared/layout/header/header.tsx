import { useAppDispatch } from 'app/config/store';
import { Icon } from 'app/shared/component/Icon';
import { logout } from 'app/shared/reducers/authentication';
import { Menubar } from 'primereact/menubar';
import { MenuItem } from 'primereact/menuitem';
import React from 'react';
import { useNavigate } from 'react-router-dom';

export interface IHeaderProps {
  isAdmin: boolean;
}

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
      label: 'Tables',
      icon: <Icon icon="table-list" colorSecondary marginRight />,
      items: [
        {
          label: 'Mouvements stock',
          icon: <Icon icon="chart-line" colorSecondary marginRight />,
          url: 'table/mouvements-stock',
        },
      ],
    },
    {
      label: 'Administration',
      icon: <Icon icon="users-cog" colorSecondary marginRight />,
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
    {
      label: 'Compte',
      icon: <Icon icon="user" colorSecondary marginRight />,
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
    <Menubar
      model={items}
      start={start}
      className="fixed top-0 left-0 w-full border-noround border-none py-0 surface-card text-color-secondary"
    />
  );
};

export default Header;
