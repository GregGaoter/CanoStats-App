import { IconProp } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React from 'react';
import { StatisticsColor } from '../model/enumeration/StatisticsColor';

interface StatisticsIconProps {
  icon: IconProp;
  color: StatisticsColor;
}

export const StatisticsIcon = ({ icon, color }: StatisticsIconProps) => (
  <div
    className={`flex align-items-center justify-content-center border-round bg-${color}-800`}
    style={{ width: '2.5rem', height: '2.5rem' }}
  >
    <FontAwesomeIcon icon={icon} style={{ fontSize: '1.5rem', color: `var(--${color}-400)` }} />
  </div>
);
