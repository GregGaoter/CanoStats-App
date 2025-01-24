import { Toast } from 'primereact/toast';
import React, { createContext, useContext, useEffect, useRef } from 'react';

const ToastContext = createContext(null);

export const ToastProvider = ({ children }) => {
  const toast = useRef<Toast>(null);

  // const showToast = (severity, summary, detail) => {
  //   toast.current.show({ severity, summary, detail });
  // };

  useEffect(() => {
    window.showToast = (severity, summary, detail) => {
      toast.current.show({ severity, summary, detail });
    };
  }, []);

  return (
    <ToastContext.Provider value={showToast}>
      <Toast ref={toast} />
      {children}
    </ToastContext.Provider>
  );
};

export const useToast = () => useContext(ToastContext).current;

export const showToast = (toast, severity, summary, detail) => {
  toast.show({ severity, summary, detail });
};
