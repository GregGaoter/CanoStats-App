import React, { ReactNode } from 'react';

interface UnauthenticatedContainerProps {
  children: ReactNode;
}

export const UnauthenticatedContainer = ({ children }: UnauthenticatedContainerProps) => (
  <div
    className="absolute top-0 left-0 w-full h-full bg-center bg-no-repeat bg-cover"
    style={{ backgroundImage: 'url("content/images/background-threat-report.webp")' }}
  >
    {children}
  </div>
);
