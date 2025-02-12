import { IconProp } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React from 'react';

interface StatisticsIconProps {
  icon: IconProp;
  iconColor: string;
  backgroundColor: string;
}

export const StatisticsIcon = ({ icon, iconColor, backgroundColor }: StatisticsIconProps) => (
  <div
    className={`flex align-items-center justify-content-center border-round ${backgroundColor}`}
    style={{ width: '2.5rem', height: '2.5rem' }}
  >
    <FontAwesomeIcon icon={icon} style={{ fontSize: '1.5rem', color: 'blue' }} />
  </div>
);
