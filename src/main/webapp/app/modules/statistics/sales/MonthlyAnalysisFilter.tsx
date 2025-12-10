import { Icon } from 'app/shared/component/Icon';
import { LabelledControl } from 'app/shared/component/LabelledControl';
import { Button } from 'primereact/button';
import { Calendar } from 'primereact/calendar';
import { Toolbar } from 'primereact/toolbar';
import React from 'react';

interface MonthlyAnalysisFilterProps {
  dates: Date[];
  loadingData: boolean;
  onDatesChange: (dates: Date[]) => void;
  onApplyFilter: () => void;
}

export const MonthlyAnalysisFilter = (props: MonthlyAnalysisFilterProps) => {
  const startContent = (
    <div className="flex gap-3 align-items-end">
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
        <Button label="Appliquer" icon={<Icon icon="filter" marginRight />} loading={props.loadingData} onClick={props.onApplyFilter} />
      </div>
    </div>
  );

  return <Toolbar start={startContent} />;
};
