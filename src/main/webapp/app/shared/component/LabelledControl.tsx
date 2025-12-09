import { Text } from 'app/shared/component/Text';
import React from 'react';

interface LabelledControlProps {
  label: string;
  control: JSX.Element;
}

export const LabelledControl = ({ label, control }: LabelledControlProps) => (
  <div className="flex flex-column gap-2">
    <Text>{label}</Text>
    <div className="flex-none">{control}</div>
  </div>
);
