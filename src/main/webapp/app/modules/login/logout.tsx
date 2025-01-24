import { useAppDispatch, useAppSelector } from 'app/config/store';
import { logout } from 'app/shared/reducers/authentication';
import React, { useLayoutEffect } from 'react';

export const Logout = () => {
  const authentication = useAppSelector(state => state.authentication);
  const dispatch = useAppDispatch();

  useLayoutEffect(() => {
    dispatch(logout());
    if (!authentication.isAuthenticated) {
      window.showToast('success', 'Succès', 'Déconnexion réussie !');
    } else {
      window.showToast('error', 'Erreur', 'Déconnexion échouée !');
    }
    window.location.href = '/';
  });

  return (
    <div className="p-5">
      <h4>Logged out successfully!</h4>
    </div>
  );
};

export default Logout;
