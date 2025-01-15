import { Toast } from 'primereact/toast';
import React, { createContext, useRef } from 'react';

const ToastContext = createContext(null);

const ToastProvider = ({ children }) => {
  const toast = useRef<Toast>(null);

  return (
    <ToastContext.Provider value={toast}>
      <Toast ref={toast} />
      {children}
    </ToastContext.Provider>
  );
};

export { ToastContext, ToastProvider };
