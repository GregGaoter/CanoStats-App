import 'app/config/dayjs';
import 'primeflex/primeflex.css';
import 'primereact/resources/themes/lara-dark-blue/theme.css';

import React, { useEffect } from 'react';
import { BrowserRouter } from 'react-router-dom';

import { AUTHORITIES } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import ErrorBoundary from 'app/shared/error/error-boundary';
import Header from 'app/shared/layout/header/header';
import { getProfile } from 'app/shared/reducers/application-profile';
import { getSession } from 'app/shared/reducers/authentication';
import { PrimeReactProvider } from 'primereact/api';
import { Card } from 'primereact/card';
import Login from './modules/login/login';
import AppRoutes from './routes';
import { ToastProvider } from './shared/component/ToastContext';
import { StatisticsMenu } from './shared/layout/header/statistics-menu';

const baseHref = document.querySelector('base').getAttribute('href').replace(/\/$/, '');

export const App = () => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getSession());
    dispatch(getProfile());
  }, []);

  const isAuthenticated = useAppSelector(state => state.authentication.isAuthenticated);
  const isAdmin = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.ADMIN]));
  const ribbonEnv = useAppSelector(state => state.applicationProfile.ribbonEnv);
  const isInProduction = useAppSelector(state => state.applicationProfile.inProduction);
  const isOpenAPIEnabled = useAppSelector(state => state.applicationProfile.isOpenAPIEnabled);

  return (
    <PrimeReactProvider>
      <ToastProvider>
        <BrowserRouter basename={baseHref}>
          <ErrorBoundary>
            {isAuthenticated ? (
              <div className="flex gap-0 absolute top-0 left-0 w-full">
                <Header
                  isAuthenticated={isAuthenticated}
                  isAdmin={isAdmin}
                  ribbonEnv={ribbonEnv}
                  isInProduction={isInProduction}
                  isOpenAPIEnabled={isOpenAPIEnabled}
                />
                <div className="flex flex-column flex-grow-1">
                  <StatisticsMenu />
                  <Card className="mx-2 mt-2 mb-0">
                    <AppRoutes />
                  </Card>
                </div>
              </div>
            ) : (
              <Login />
            )}
          </ErrorBoundary>
        </BrowserRouter>
      </ToastProvider>
    </PrimeReactProvider>
  );

  // return (
  //   <PrimeReactProvider>
  //     <BrowserRouter basename={baseHref}>
  //       <div className="absolute top-0 left-0">
  //         <ToastContainer position="top-right" className="toastify-container" toastClassName="toastify-toast" />
  //         <ErrorBoundary>
  //           <Header
  //             isAuthenticated={isAuthenticated}
  //             isAdmin={isAdmin}
  //             ribbonEnv={ribbonEnv}
  //             isInProduction={isInProduction}
  //             isOpenAPIEnabled={isOpenAPIEnabled}
  //           />
  //         </ErrorBoundary>
  //         <div className="container-fluid view-container" id="app-view-container">
  //           <Card className="jh-card">
  //             <ErrorBoundary>
  //               <AppRoutes />
  //             </ErrorBoundary>
  //           </Card>
  //         </div>
  //       </div>
  //     </BrowserRouter>
  //   </PrimeReactProvider>
  // );
};

export default App;
