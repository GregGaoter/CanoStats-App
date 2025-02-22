import { Calendar } from 'primereact/calendar';
import { InputNumber } from 'primereact/inputnumber';
import { TabPanel, TabView } from 'primereact/tabview';
import { Toolbar } from 'primereact/toolbar';
import { Nullable } from 'primereact/ts-helpers';
import React, { useState } from 'react';

export const Inventory = () => {
  const [dates, setDates] = useState<Nullable<(Date | null)[]>>(null);

  const startContent = (
    <div className="flex gap-3">
      <div className="flex flex-column gap-2">
        <label>Mouvement</label>
        <InputNumber prefix="≥ " suffix="g" />
      </div>
      <div className="flex flex-column gap-2">
        <label>Période</label>
        <Calendar
          value={dates}
          onChange={e => setDates(e.value)}
          selectionMode="range"
          dateFormat="dd/mm/yy"
          readOnlyInput
          hideOnRangeSelection
        />
      </div>
    </div>
  );

  return (
    <TabView>
      <TabPanel header="Au poids">
        <div className="grid align-items-center">
          <div className="col-12">
            <Toolbar start={startContent} />
          </div>
        </div>
      </TabPanel>
      <TabPanel header="A la pièce">
        <p className="m-0">
          Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa
          quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas
          sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Consectetur,
          adipisci velit, sed quia non numquam eius modi.
        </p>
      </TabPanel>
    </TabView>
  );
};
