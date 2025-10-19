import { Icon } from 'app/shared/component/Icon';
import { Button } from 'primereact/button';
import { Calendar } from 'primereact/calendar';
import { Toolbar } from 'primereact/toolbar';
import React from 'react';

interface WeeklyBestSellersFilterProps {
  dates: Date[];
  loadingData: boolean;
  onDatesChange: (dates: Date[]) => void;
  onApplyFilter: () => void;
}

export const WeeklyBestSellersFilter = (props: WeeklyBestSellersFilterProps) => {
  const startContent = (
    <div className="flex gap-3 align-items-end">
      <div className="flex flex-column gap-2">
        <label>Période</label>
        <Calendar
          value={props.dates}
          onChange={e => props.onDatesChange(e.value)}
          selectionMode="range"
          dateFormat="dd.mm.yy"
          readOnlyInput
          hideOnRangeSelection
          showIcon
          required
        />
      </div>
      <div className="flex flex-column gap-2">
        <Button label="Appliquer" icon={<Icon icon="filter" marginRight />} loading={props.loadingData} onClick={props.onApplyFilter} />
      </div>
    </div>
  );

  return <Toolbar start={startContent} />;
};
