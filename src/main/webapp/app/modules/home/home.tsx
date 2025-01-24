import React from 'react';
import { useAppSelector } from 'app/config/store';
import { Button } from 'primereact/button';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return <Button label="Test toast" onClick={() => window.showToast('success', 'Toast', 'Test utilisation Toast réussi !')}></Button>;
};

export default Home;
