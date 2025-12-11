import { Icon } from 'app/shared/component/Icon';
import { LabelledControl } from 'app/shared/component/LabelledControl';
import { Button } from 'primereact/button';
import { Calendar } from 'primereact/calendar';
import { Dropdown, DropdownChangeEvent } from 'primereact/dropdown';
import { MultiSelect, MultiSelectChangeEvent } from 'primereact/multiselect';
import { Toolbar } from 'primereact/toolbar';
import React from 'react';
import { MovementTypeOption, ProductTypeOption } from './MonthlyAnalysis';

interface MonthlyAnalysisFilterProps {
  dates: Date[];
  movementType: string;
  productTypes: string[];
  movementTypeOptions: MovementTypeOption[];
  productTypeOptions: ProductTypeOption[];
  loadingData: boolean;
  onMovementTypeChange: (movementType: string) => void;
  onProductTypesChange: (productTypes: string[]) => void;
  onDatesChange: (dates: Date[]) => void;
  onApplyFilter: () => void;
}

export const MonthlyAnalysisFilter = (props: MonthlyAnalysisFilterProps) => {
  const startContent = (
    <div className="flex gap-3 align-items-end">
      <LabelledControl
        label="Type de mouvement"
        control={
          <Dropdown
            value={props.movementType}
            onChange={(e: DropdownChangeEvent) => props.onMovementTypeChange(e.value)}
            options={props.movementTypeOptions}
            placeholder="Sélectionner..."
            className="w-full w-12rem"
            required
          />
        }
      />
      <LabelledControl
        label="Types de produit"
        control={
          <MultiSelect
            value={props.productTypes}
            onChange={(e: MultiSelectChangeEvent) => props.onProductTypesChange(e.value)}
            options={props.productTypeOptions}
            display="chip"
            placeholder="Sélectionner..."
            maxSelectedLabels={3}
            className="w-full w-24rem"
            required
          />
        }
      />
      <LabelledControl
        label="Période"
        control={
          <Calendar
            value={props.dates}
            onChange={e => props.onDatesChange(e.value)}
            view="month"
            selectionMode="range"
            dateFormat="mm.yy"
            readOnlyInput
            hideOnRangeSelection
            showIcon
            required
          />
        }
      />
      <div className="flex flex-column gap-2">
        <Button
          label="Appliquer"
          icon={<Icon icon="filter" marginRight />}
          loading={props.loadingData}
          onClick={props.onApplyFilter}
          disabled={!props.movementType || !props.productTypes.length}
        />
      </div>
    </div>
  );

  return <Toolbar start={startContent} />;
};
