import { useMountEffect } from 'primereact/hooks';
import { Messages } from 'primereact/messages';
import React, { useRef } from 'react';

export const Dashboard = () => {
  const msgs = useRef<Messages>(null);

  useMountEffect(() => {
    msgs.current?.clear();
    msgs.current?.show({
      id: '1',
      sticky: true,
      severity: 'info',
      summary: 'Info',
      detail: 'Fonctionnalité bientôt disponible.',
      closable: false,
    });
  });

  return <Messages ref={msgs} />;
};
