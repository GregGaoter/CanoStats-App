import { useAppDispatch, useAppSelector } from 'app/config/store';
import { logout } from 'app/shared/reducers/authentication';
import React, { useLayoutEffect } from 'react';
import { useNavigate } from 'react-router-dom';

export const Logout = () => {
  const authentication = useAppSelector(state => state.authentication);
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  useLayoutEffect(() => {
    dispatch(logout());
    // if (!authentication.isAuthenticated) {
    //   window.showToast('success', 'Succès', 'Déconnexion réussie !');
    //   navigate('/login');
    // }
  });

  return (
    <div className="p-5">
      <h4>Logged out successfully!</h4>
    </div>
  );
};

export default Logout;
