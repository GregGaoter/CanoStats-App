import { AUTHORITIES } from 'app/config/constants';
import 'app/config/dayjs';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import ErrorBoundary from 'app/shared/error/error-boundary';
import Header from 'app/shared/layout/header/header';
import { getProfile } from 'app/shared/reducers/application-profile';
import { getSession } from 'app/shared/reducers/authentication';
import 'primeflex/primeflex.css';
import { PrimeReactProvider } from 'primereact/api';
import 'primereact/resources/themes/lara-dark-blue/theme.css';
import React, { CSSProperties, useEffect } from 'react';
import { BrowserRouter } from 'react-router-dom';
import AppRoutes from './routes';
import { ToastProvider } from './shared/component/ToastContext';
import Footer from './shared/layout/footer/footer';
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

  const appRoutesClass: string = 'flex-grow-1';
  if (isAuthenticated) appRoutesClass.concat(' flex flex-column justify-content-between surface-ground min-h-screen');

  const appRoutesStyle: CSSProperties = isAuthenticated ? { marginTop: '4rem', marginLeft: '14.35rem', marginRight: '0rem' } : {};

  return (
    <PrimeReactProvider>
      <ToastProvider>
        <BrowserRouter basename={baseHref}>
          <ErrorBoundary>
            {isAuthenticated && <Header isAdmin={isAdmin} />}
            <div className="flex surface-ground">
              {isAuthenticated && <StatisticsMenu />}
              <div className={appRoutesClass} style={appRoutesStyle}>
                <AppRoutes isAuthenticated={isAuthenticated} />
                {isAuthenticated && <Footer />}
              </div>
            </div>
          </ErrorBoundary>
        </BrowserRouter>
      </ToastProvider>
    </PrimeReactProvider>
  );

  // return (
  //   <PrimeReactProvider>
  //     <ToastProvider>
  //       <BrowserRouter basename={baseHref}>
  //         <ErrorBoundary>
  //           {isAuthenticated ? (
  //             <div className="absolute top-0 left-0 w-full surface-ground">
  //               <Header
  //                 isAuthenticated={isAuthenticated}
  //                 isAdmin={isAdmin}
  //                 ribbonEnv={ribbonEnv}
  //                 isInProduction={isInProduction}
  //                 isOpenAPIEnabled={isOpenAPIEnabled}
  //               />
  //               <StatisticsMenu />
  //               <div
  //                 className="flex flex-column justify-content-between surface-ground min-h-screen"
  //                 style={{ marginTop: '4rem', marginLeft: '14.35rem', marginRight: '0rem' }}
  //               >
  //                 <AppRoutes />
  //                 <Footer />
  //               </div>
  //             </div>
  //           ) : (
  //             <Login />
  //           )}
  //         </ErrorBoundary>
  //       </BrowserRouter>
  //     </ToastProvider>
  //   </PrimeReactProvider>
  // );
};

export default App;
