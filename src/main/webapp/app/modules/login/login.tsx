import { useAppDispatch, useAppSelector } from 'app/config/store';
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
    <div
      className="absolute top-0 left-0 w-full h-full bg-center bg-no-repeat bg-cover"
      style={{ backgroundImage: 'url("content/images/login-background.jpg")' }}
    >
      <LoginForm handleLogin={handleLogin} loginError={loginError} />
    </div>
  );
};

export default Login;
