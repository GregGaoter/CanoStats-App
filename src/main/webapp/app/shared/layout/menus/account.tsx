import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';

import { NavDropdown } from './menu-components';

const accountMenuItemsAuthenticated = () => (
  <>
    <MenuItem icon="wrench" to="/account/settings" data-cy="settings">
      Paramètres
    </MenuItem>
    <MenuItem icon="lock" to="/account/password" data-cy="passwordItem">
      Mot de passe
    </MenuItem>
    <MenuItem icon="sign-out-alt" to="/logout" data-cy="logout">
      Se déconnecter
    </MenuItem>
  </>
);

const accountMenuItems = () => (
  <>
    <MenuItem id="login-item" icon="sign-in-alt" to="/login" data-cy="login">
      Se connecter
    </MenuItem>
    <MenuItem icon="user-plus" to="/account/register" data-cy="register">
      {`S'enregister`}
    </MenuItem>
  </>
);

export const AccountMenu = ({ isAuthenticated = false }) => (
  <NavDropdown icon="user" name="Compte" id="account-menu" data-cy="accountMenu">
    {isAuthenticated && accountMenuItemsAuthenticated()}
    {!isAuthenticated && accountMenuItems()}
  </NavDropdown>
);

export default AccountMenu;
