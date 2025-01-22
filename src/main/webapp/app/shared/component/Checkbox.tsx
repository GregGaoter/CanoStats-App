import { Text } from 'app/shared/component/Text';
import { Checkbox as PrimeReactCheckbox } from 'primereact/checkbox';
import React from 'react';

interface CheckboxProps {
  label: string;
  checked: boolean;
  onChange: (checked: boolean) => void;
}

export const Checkbox = ({ label, checked, onChange }: CheckboxProps) => (
  <div className="flex align-items-center gap-2">
    <PrimeReactCheckbox checked={checked} onChange={e => onChange(e.checked)} />
    <Text>{label}</Text>
  </div>
);
