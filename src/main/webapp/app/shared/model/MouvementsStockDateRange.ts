import dayjs from 'dayjs';

export interface MouvementsStockDateRange {
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
}
