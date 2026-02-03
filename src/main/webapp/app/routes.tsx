import { AUTHORITIES } from 'app/config/constants';
import EntitiesRoutes from 'app/entities/routes';
import Activate from 'app/modules/account/activate/activate';
import PasswordResetFinish from 'app/modules/account/password-reset/finish/password-reset-finish';
import PasswordResetInit from 'app/modules/account/password-reset/init/password-reset-init';
import Register from 'app/modules/account/register/register';
import { Dashboard } from 'app/modules/dashboard/dashboard';
import Login from 'app/modules/login/login';
import PrivateRoute from 'app/shared/auth/private-route';
import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';
import PageNotFound from 'app/shared/error/page-not-found';
import React from 'react';
import Loadable from 'react-loadable';
import { Route } from 'react-router-dom';
import { Inventory } from './modules/statistics/stock/inventory-anomalies/Inventory';
import { MonthlyAnalysis } from './modules/statistics/stock/movements-monthly/MonthlyAnalysis';

interface AppRoutesProps {
  isAuthenticated: boolean;
}

const loading = <div>loading ...</div>;

const Account = Loadable({
  loader: () => import(/* webpackChunkName: "account" */ 'app/modules/account'),
  loading: () => loading,
});

const Admin = Loadable({
  loader: () => import(/* webpackChunkName: "administration" */ 'app/modules/administration'),
  loading: () => loading,
});
const AppRoutes = (props: AppRoutesProps) => {
  return (
    <ErrorBoundaryRoutes>
      <Route index element={props.isAuthenticated ? <Dashboard /> : <Login />} />
      <Route path="login" element={<Login />} />
      <Route path="dashboard" element={<Dashboard />} />
      <Route path="account">
        <Route
          path="*"
          element={
            <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.USER]}>
              <Account />
            </PrivateRoute>
          }
        />
        <Route path="register" element={<Register />} />
        <Route path="activate" element={<Activate />} />
        <Route path="reset">
          <Route path="request" element={<PasswordResetInit />} />
          <Route path="finish" element={<PasswordResetFinish />} />
        </Route>
      </Route>
      <Route
        path="admin/*"
        element={
          <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN]}>
            <Admin />
          </PrivateRoute>
        }
      />
      <Route path="statistics">
        <Route path="dashboard" element={<Dashboard />} />
        <Route path="stock">
          <Route path="movements-monthly" element={<MonthlyAnalysis />} />
          <Route path="inventory-anomalies" element={<Inventory />} />
        </Route>
      </Route>
      <Route
        path="*"
        element={
          <PrivateRoute hasAnyAuthorities={[AUTHORITIES.USER]}>
            <EntitiesRoutes />
          </PrivateRoute>
        }
      />
      <Route path="*" element={<PageNotFound />} />
    </ErrorBoundaryRoutes>
  );
};

export default AppRoutes;
