import React from 'react';

import { Icon } from 'app/shared/component/Icon';
import { Menu } from 'primereact/menu';
import { MenuItem } from 'primereact/menuitem';

export interface IHeaderProps {
  isAuthenticated: boolean;
  isAdmin: boolean;
  ribbonEnv: string;
  isInProduction: boolean;
  isOpenAPIEnabled: boolean;
}

const Header = (props: IHeaderProps) => {
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

  const menuItems: MenuItem[] = [
    {
      template: () => (
        <div className="flex align-items-center gap-2 ml-2">
          <img src="content/images/logo-canopee.svg" height="49"></img>
          <div className="font-medium text-xl">STATISTIQUES</div>
        </div>
      ),
      url: '/',
    },
    {
      separator: true,
    },
    { label: 'Accueil', icon: <Icon icon="home" />, url: '/' },
    {
      label: 'Tables',
      items: [
        {
          label: 'Mouvements stock',
          icon: <Icon icon="chart-line" />,
        },
      ],
    },
    {
      label: 'Administration',
      items: [
        {
          label: 'Utilisateurs',
          icon: <Icon icon="users" />,
        },
        {
          label: 'Importation',
          icon: <Icon icon="file-import" />,
          url: '/file-import',
        },
        {
          label: 'Métriques',
          icon: <Icon icon="tachometer-alt" />,
        },
        {
          label: 'Santé',
          icon: <Icon icon="heart" />,
        },
        {
          label: 'Configuration',
          icon: <Icon icon="cogs" />,
        },
        {
          label: 'Logs',
          icon: <Icon icon="tasks" />,
        },
      ],
    },
    {
      label: 'Compte',
      items: [
        {
          label: 'Paramètres',
          icon: <Icon icon="wrench" />,
        },
        {
          label: 'Mot de passe',
          icon: <Icon icon="lock" />,
        },
        {
          label: 'Se déconnecter',
          icon: <Icon icon="sign-out-alt" />,
        },
      ],
    },
  ];

  return <Menu model={menuItems} />;
};

export default Header;
