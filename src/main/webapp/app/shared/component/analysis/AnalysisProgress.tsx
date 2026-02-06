import { Text } from 'app/shared/component/Text';
import { ProgressBar } from 'primereact/progressbar';
import React from 'react';

interface AnalysisProgressProps {
  message: string;
  percentage: number;
}

export const AnalysisProgress = ({ message, percentage }: AnalysisProgressProps) => (
  <div className="col-6 col-offset-3 text-center mt-4">
    <div className="mb-2">
      <Text>{message}</Text>
    </div>
    {percentage === 0 ? <ProgressBar mode="indeterminate"></ProgressBar> : <ProgressBar value={percentage}></ProgressBar>}
  </div>
);
