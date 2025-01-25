import React from 'react';
import { useAppDispatch } from 'app/config/store';
import { Icon } from 'app/shared/component/Icon';
import { Menubar } from 'primereact/menubar';
import { MenuItem } from 'primereact/menuitem';
import { useNavigate } from 'react-router-dom';
import { logout } from 'app/shared/reducers/authentication';

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
      icon: <Icon icon="table-list" colorSecondary />,
      items: [
        {
          label: 'Mouvements stock',
          icon: <Icon icon="chart-line" colorSecondary />,
        },
      ],
    },
    {
      label: 'Administration',
      icon: <Icon icon="users-cog" colorSecondary />,
      items: [
        {
          label: 'Utilisateurs',
          icon: <Icon icon="users" colorSecondary />,
        },
        {
          label: 'Importation',
          icon: <Icon icon="file-import" colorSecondary />,
          url: '/file-import',
        },
        {
          label: 'Métriques',
          icon: <Icon icon="tachometer-alt" colorSecondary />,
        },
        {
          label: 'Santé',
          icon: <Icon icon="heart" colorSecondary />,
        },
        {
          label: 'Configuration',
          icon: <Icon icon="cogs" colorSecondary />,
        },
        {
          label: 'Logs',
          icon: <Icon icon="tasks" colorSecondary />,
        },
      ],
    },
    {
      label: 'Compte',
      icon: <Icon icon="user" colorSecondary />,
      items: [
        {
          label: 'Paramètres',
          icon: <Icon icon="wrench" colorSecondary />,
        },
        {
          label: 'Mot de passe',
          icon: <Icon icon="lock" colorSecondary />,
        },
      ],
    },
    {
      label: 'Se déconnecter',
      icon: <Icon icon="sign-out-alt" colorSecondary />,
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
      pt={{
        root: { className: 'flex justify-content-between' },
        submenu: { className: 'surface-card border-none' },
        label: { className: 'text-color-secondary' },
      }}
    />
  );
};

export default Header;
