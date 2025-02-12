import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Icon } from 'app/shared/component/Icon';
import { StatisticsCard } from 'app/shared/component/StatisticsCard';
import React from 'react';

export const Dashboard = () => {
  const dispatch = useAppDispatch();
  const account = useAppSelector(state => state.authentication.account);

  return (
    <div className="flex flex-column">
      <div className="flex">
        <StatisticsCard title="CA annuel" icon={<Icon icon="sack-dollar" marginRight={false} />} value="CHF 35'700" tag="+ 1712" />
      </div>
    </div>
  );
};
