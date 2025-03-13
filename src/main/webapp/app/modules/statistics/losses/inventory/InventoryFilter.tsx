import { Icon } from 'app/shared/component/Icon';
import { Button } from 'primereact/button';
import { Calendar } from 'primereact/calendar';
import { InputNumber } from 'primereact/inputnumber';
import { SelectButton, SelectButtonChangeEvent } from 'primereact/selectbutton';
import { SelectItem } from 'primereact/selectitem';
import { Toolbar } from 'primereact/toolbar';
import React from 'react';

interface InventoryFilterProps {
  mouvement: number;
  dates: Date[];
  loadingData: boolean;
  display: Display;
  onMouvementChange: (mouvement: number) => void;
  onDatesChange: (dates: Date[]) => void;
  onApplyFilter: () => void;
  onDisplayChange: (display: Display) => void;
}

export enum Display {
  CHART = 'CHART',
  TABLE = 'TABLE',
}

export const InventoryFilter = (props: InventoryFilterProps) => {
  const startContent = (
    <div className="flex gap-3 align-items-end">
      <div className="flex flex-column gap-2">
        <label>Mouvement</label>
        <InputNumber prefix="≥ " suffix="g" value={props.mouvement} onChange={e => props.onMouvementChange(e.value)} />
      </div>
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

  const selectDisplayOptions: SelectItem[] = [
    { label: 'Graphique', value: Display.CHART },
    { label: 'Tableau', value: Display.TABLE },
  ];

  const endContent = (
    <div className="flex flex-column gap-2">
      <label>Affichage</label>
      <SelectButton
        value={props.display}
        onChange={(e: SelectButtonChangeEvent) => props.onDisplayChange(e.value)}
        options={selectDisplayOptions}
      />
    </div>
  );

  return <Toolbar start={startContent} end={endContent} />;
};
