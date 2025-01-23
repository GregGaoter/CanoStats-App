import { IconProp } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React from 'react';

interface IconProps {
  icon: IconProp;
  colorSecondary?: boolean;
  marginRight?: boolean;
}

export const Icon = ({ icon, colorSecondary, marginRight = true }: IconProps) => {
  const classNames: string[] = [];
  if (colorSecondary) classNames.push('text-color-secondary');
  if (marginRight) classNames.push('mr-2');
  return <FontAwesomeIcon icon={icon} className={classNames.join(' ')} />;
};
