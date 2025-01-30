import { useAppDispatch, useAppSelector } from 'app/config/store';
import { UnauthenticatedContainer } from 'app/shared/component/UnauthenticatedContainer';
import { login } from 'app/shared/reducers/authentication';
import React from 'react';
import { Navigate } from 'react-router-dom';
import LoginForm from './login-form';

export const Login = () => {
  const dispatch = useAppDispatch();
  const isAuthenticated = useAppSelector(state => state.authentication.isAuthenticated);
  const loginError = useAppSelector(state => state.authentication.loginError);

  const handleLogin = (username, password, rememberMe = false) => dispatch(login(username, password, rememberMe));

  if (isAuthenticated) {
    return <Navigate to={'/dashboard'} replace />;
  }

  return (
    <UnauthenticatedContainer>
      <LoginForm handleLogin={handleLogin} loginError={loginError} />
    </UnauthenticatedContainer>
  );
};

export default Login;
