import { useAppDispatch, useAppSelector } from 'app/config/store';
import { useToast } from 'app/shared/component/ToastContext';
import { logout } from 'app/shared/reducers/authentication';
import React, { useLayoutEffect } from 'react';

export const Logout = () => {
  const authentication = useAppSelector(state => state.authentication);
  const dispatch = useAppDispatch();
  const toast = useToast();

  useLayoutEffect(() => {
    dispatch(logout());
    if (!authentication.isAuthenticated) {
      toast.show({ severity: 'success', summary: 'Déconnexion', detail: 'Déconnexion réussie !', life: 3000 });
    } else {
      toast.show({ severity: 'error', summary: 'Déconnexion', detail: 'Déconnexion échouée !', life: 3000 });
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
