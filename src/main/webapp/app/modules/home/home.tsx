import React from 'react';

import { Alert } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <div className="grid">
      <div className="col-3">
        <img src="content/images/logo-canopee.svg" alt="Logo" />
      </div>
      <div className="col flex flex-column align-items-center">
        <div className="text-5xl mb-4">Bienvenue, statistiques de La Canopée !</div>
        {account?.login && (
          <div>
            <Alert color="success">{`Tu es connecté en tant qu'utilisateur "${account.login}".`}</Alert>
          </div>
        )}
        <img src="content/images/statistics-3d-icon.webp" style={{ width: '500px' }} />
        {/* {account?.login ? (
          <div>
            <Alert color="success">{`Tu es connecté en tant qu'utilisateur "${account.login}".`}</Alert>
          </div>
        ) : (
          <div>
            <Alert color="warning">
              If you want to
              <span>&nbsp;</span>
              <Link to="/login" className="alert-link">
                sign in
              </Link>
              , you can try the default accounts:
              <br />- Administrator (login=&quot;admin&quot; and password=&quot;admin&quot;) <br />- User (login=&quot;user&quot; and
              password=&quot;user&quot;).
            </Alert>

            <Alert color="warning">
              You don&apos;t have an account yet?&nbsp;
              <Link to="/account/register" className="alert-link">
                Register a new account
              </Link>
            </Alert>
          </div>
        )} */}
      </div>
    </div>
  );
};

export default Home;
