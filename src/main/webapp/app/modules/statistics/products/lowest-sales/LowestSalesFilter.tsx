import { AnalysisProgress } from 'app/shared/component/analysis/AnalysisProgress';
import { Icon } from 'app/shared/component/Icon';
import { LabelledControl } from 'app/shared/component/LabelledControl';
import { Button } from 'primereact/button';
import { Calendar } from 'primereact/calendar';
import React from 'react';

interface LowestSalesFilterProps {
  dates: Date[];
  minDate: Date;
  maxDate: Date;
  loadingData: boolean;
  progressMessage: string;
  progressPercentage: number;
  onDatesChange: (dates: Date[]) => void;
  onApplyFilter: () => void;
}

export const LowestSalesFilter = (props: LowestSalesFilterProps) => (
  <>
    {props.loadingData ? (
      <AnalysisProgress message={props.progressMessage} percentage={props.progressPercentage} />
    ) : (
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
              minDate={props.minDate}
              maxDate={props.maxDate}
              readOnlyInput
              hideOnRangeSelection
              showIcon
              required
              disabled={props.loadingData}
            />
          }
        />
        <div className="flex flex-column gap-2">
          <Button
            label="Appliquer"
            icon={<Icon icon="filter" marginRight />}
            loading={props.loadingData}
            onClick={props.onApplyFilter}
            disabled={props.loadingData}
          />
        </div>
      </div>
    )}
  </>
);
