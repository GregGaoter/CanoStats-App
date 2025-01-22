import { IconProp } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React from 'react';

interface IconProps {
  icon: IconProp;
  marginRight?: boolean;
}

export const Icon = ({ icon, marginRight = true }: IconProps) =>
  marginRight ? (
    <FontAwesomeIcon icon={icon} className="mr-2 text-color-secondary" />
  ) : (
    <FontAwesomeIcon icon={icon} className="text-color-secondary" />
  );
