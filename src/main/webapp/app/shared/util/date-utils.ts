import dayjs from 'dayjs';

import { APP_LOCAL_DATETIME_FORMAT, FILENAME_DATETIME_FORMAT } from 'app/config/constants';

export const convertDateTimeFromServer = date => (date ? dayjs(date).format(APP_LOCAL_DATETIME_FORMAT) : null);

export const convertDateTimeToServer = (date?: string): dayjs.Dayjs | null => (date ? dayjs(date) : null);

export const displayDefaultDateTime = () => dayjs().startOf('day').format(APP_LOCAL_DATETIME_FORMAT);

export const prefixWithDateTime = (fileName: string): string => `${dayjs().format(FILENAME_DATETIME_FORMAT)}_${fileName}`;
