import { Toast } from 'primereact/toast';
import React, { createContext, useContext, useRef } from 'react';

const ToastContext = createContext(null);

export const ToastProvider = ({ children }) => {
  const toast = useRef<Toast>(null);

  return (
    <ToastContext.Provider value={toast}>
      <Toast ref={toast} />
      {children}
    </ToastContext.Provider>
  );
};

export const useToast = () => useContext(ToastContext).current;
