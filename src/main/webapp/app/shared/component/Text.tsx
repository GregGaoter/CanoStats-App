import React, { ReactNode } from 'react';

interface TextProps {
  color?: string;
  className?: string;
  children: ReactNode;
}

export const Text = ({ color = 'text-color-secondary', className = '', children }: TextProps) => (
  <div className={color.concat(` ${className}`)}>{children}</div>
);
