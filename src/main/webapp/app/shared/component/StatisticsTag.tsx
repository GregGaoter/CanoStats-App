import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { ReactNode } from 'react';

interface StatisticsTagProps {
  value: ReactNode;
  severity: 'success' | 'info' | 'danger';
}

const SuccessTag = ({ value }: Pick<StatisticsTagProps, 'value'>) => (
  <div className="flex align-items-center gap-2" style={{ color: 'var(--green-500)' }}>
    <div className="flex align-items-center">
      {'+'}
      {value}
    </div>
    <FontAwesomeIcon icon="arrow-up" />
  </div>
);

const InfoTag = ({ value }: Pick<StatisticsTagProps, 'value'>) => (
  <div className="flex align-items-center gap-2" style={{ color: 'var(--blue-500)' }}>
    {value}
    <FontAwesomeIcon icon="arrow-right" />
  </div>
);

const DangerTag = ({ value }: Pick<StatisticsTagProps, 'value'>) => (
  <div className="flex align-items-center gap-2" style={{ color: 'var(--red-500)' }}>
    <div className="flex align-items-center">
      {'-'}
      {value}
    </div>
    <FontAwesomeIcon icon="arrow-down" />
  </div>
);

export const StatisticsTag = ({ value, severity }: StatisticsTagProps) => {
  let tagValue: ReactNode;
  switch (severity) {
    case 'success':
      tagValue = <SuccessTag value={value} />;
      break;
    case 'info':
      tagValue = <InfoTag value={value} />;
      break;
    case 'danger':
      tagValue = <DangerTag value={value} />;
      break;
    default:
      tagValue = undefined;
  }
  return <>{tagValue ?? value}</>;
};
