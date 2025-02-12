import { Tag } from 'primereact/tag';
import React from 'react';
import { StatisticsIcon } from './StatisticsIcon';
import { Text } from './Text';

interface StatisticsCardProps {
  title: string;
  icon: JSX.Element;
  value: number | string | JSX.Element;
  tag: number | string | JSX.Element;
}

export const StatisticsCard = ({ title, icon, value, tag }: StatisticsCardProps) => (
  <div className="surface-card shadow-2 p-4 border-round-xl border-1 border-200">
    <div className="flex gap-3">
      <div>
        <div className="flex align-items-center gap-3">
          <StatisticsIcon icon="sack-dollar" iconColor="text-blue-500" backgroundColor="bg-blue-800" />
          <Text className="text-lg mb-3">{title}</Text>
        </div>
        <div className="flex align-items-center gap-3">
          <Text className="text-2xl font-bold">{value}</Text>
          <Tag value={tag} severity="success"></Tag>
        </div>
      </div>
    </div>
  </div>
);
