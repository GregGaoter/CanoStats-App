import 'app/config/dayjs';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import Header from 'app/shared/layout/header/header';
import { getProfile } from 'app/shared/reducers/application-profile';
import { getSession } from 'app/shared/reducers/authentication';
import 'primeflex/primeflex.css';
import { PrimeReactProvider, PrimeReactPTOptions } from 'primereact/api';
import 'primereact/resources/themes/lara-dark-blue/theme.css';
import React, { CSSProperties, useEffect } from 'react';
import { BrowserRouter } from 'react-router-dom';
import { AUTHORITIES } from './config/constants';
import AppRoutes from './routes';
import { hasAnyAuthority } from './shared/auth/private-route';
import { ToastProvider } from './shared/component/ToastContext';
import ErrorBoundary from './shared/error/error-boundary';
import Footer from './shared/layout/footer/footer';

const baseHref = document.querySelector('base').getAttribute('href').replace(/\/$/, '');

export const App = () => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getSession());
    dispatch(getProfile());
  }, []);

  const isAuthenticated = useAppSelector(state => state.authentication.isAuthenticated);
  const isAdmin = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.ADMIN]));

  const appRoutesClass: string = isAuthenticated ? 'surface-ground min-h-screen' : '';
  const appRoutesStyle: CSSProperties = isAuthenticated
    ? { marginTop: '0rem', marginLeft: '0rem', marginRight: '0rem', paddingTop: '3.6rem', paddingRight: '0rem' }
    : {};

  /**
   * Defines the shared global pass through properties per component type.
   */
  const pt: PrimeReactPTOptions = {
    megamenu: {
      root: { className: 'flex justify-content-between shadow-4' },
      submenu: { className: 'surface-card border-none' },
      label: { className: 'text-color-secondary' },
      panel: { className: 'surface-card border-none' },
      submenuHeader: { className: 'surface-card' },
    },
    menu: {
      root: { className: 'surface-card text-color-secondary shadow-4' },
      submenuHeader: { className: 'surface-card text-color-secondary' },
      label: { className: 'text-color-secondary' },
    },
    inputtext: {
      root: { className: 'text-color-secondary' },
    },
    card: {
      root: { className: 'text-color-secondary shadow-4 border-round-xl border-1 border-200' },
    },
    column: {
      root: { className: 'text-color-secondary' },
    },
    paginator: {
      root: { className: 'border-none' },
    },
    accordiontab: {
      headerIcon: { className: 'text-color-secondary' },
      headerTitle: { className: 'text-color-secondary' },
      content: { className: 'text-color-secondary p-1' },
    },
    fieldset: {
      root: { className: 'border-round-xl shadow-4' },
      legend: { className: 'text-color-secondary' },
    },
    toolbar: {
      root: { className: 'border-round-xl shadow-4' },
    },
    tabview: {
      panelContainer: { className: 'surface-ground px-0 pb-0' },
    },
    tabpanel: {
      root: { className: 'text-color-secondary' },
    },
  };

  return (
    <PrimeReactProvider value={{ pt }}>
      <ToastProvider>
        <BrowserRouter basename={baseHref}>
          <ErrorBoundary>
            <div className={appRoutesClass} style={appRoutesStyle}>
              <AppRoutes isAuthenticated={isAuthenticated} />
              {isAuthenticated && <Footer />}
            </div>
            {isAuthenticated && <Header isAdmin={isAdmin} />}
          </ErrorBoundary>
        </BrowserRouter>
      </ToastProvider>
    </PrimeReactProvider>
  );
};

export default App;
