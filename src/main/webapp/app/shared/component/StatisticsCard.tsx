import { IconProp } from '@fortawesome/fontawesome-svg-core';
import { Divider } from 'primereact/divider';
import React, { ReactNode } from 'react';
import { MainColor } from '../model/enumeration/MainColor';
import { StatisticsIcon } from './StatisticsIcon';
import { StatisticsTag } from './StatisticsTag';
import { Text } from './Text';

interface StatisticsCardProps {
  title: string;
  icon: IconProp;
  color: MainColor;
  value: number | string | JSX.Element;
  tagValue?: ReactNode;
  tagSeverity?: 'success' | 'info' | 'danger';
}

export const StatisticsCard = ({ title, icon, color, value, tagValue, tagSeverity }: StatisticsCardProps) => (
  <div
    className="surface-card shadow-4 p-4"
    style={{
      border: 'solid',
      borderColor: `var(--surface-200) var(--surface-200) var(--surface-200) var(--${color}-500)`,
      borderWidth: '1px 1px 1px 6px',
      borderRadius: '0.75rem',
    }}
  >
    <div className="flex flex-column gap-3">
      <div className="flex align-items-center justify-content-between gap-3">
        <Text color={`text-${color}-500`} className="text-lg">
          {title}
        </Text>
        <StatisticsIcon icon={icon} color={color} />
      </div>
      <div className="flex align-items-center">
        <Text className="text-2xl font-bold">{value}</Text>
        {!!tagValue && !!tagSeverity && (
          <>
            <Divider layout="vertical" />
            <StatisticsTag value={tagValue} severity={tagSeverity} />
          </>
        )}
      </div>
    </div>
  </div>
);
