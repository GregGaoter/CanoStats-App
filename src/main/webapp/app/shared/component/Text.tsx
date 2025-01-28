import React, { ReactNode } from 'react';

interface TextProps {
  className?: string;
  children: ReactNode;
}

export const Text = ({ className, children }: TextProps) => {
  const clazz: string = 'text-color-secondary';
  if (className) clazz.concat(` ${className}`);
  return <div className={clazz}>{children}</div>;
};
