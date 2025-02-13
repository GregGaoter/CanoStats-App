import { IconProp } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { CSSProperties } from 'react';

interface IconProps {
  icon: IconProp;
  color?: string;
  colorSecondary?: boolean;
  marginRight?: boolean;
}

export const Icon = ({ icon, color, colorSecondary, marginRight }: IconProps) => {
  let style: CSSProperties = {};
  if (color) style = { color };
  const classNames: string[] = [];
  if (colorSecondary) classNames.push('text-color-secondary');
  if (marginRight) classNames.push('mr-2');
  return <FontAwesomeIcon icon={icon} className={classNames.join(' ')} style={style} />;
};
