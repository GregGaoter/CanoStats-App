import React from 'react';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';
import { Route } from 'react-router-dom';
import Configuration from './configuration/configuration';
import Docs from './docs/docs';
import { FileImport } from './file-import/FileImport';
import Health from './health/health';
import Logs from './logs/logs';
import Metrics from './metrics/metrics';
import UserManagement from './user-management';

const AdministrationRoutes = () => (
  <div>
    <ErrorBoundaryRoutes>
      <Route path="user-management/*" element={<UserManagement />} />
      <Route path="file-import" element={<FileImport />} />
      <Route path="health" element={<Health />} />
      <Route path="metrics" element={<Metrics />} />
      <Route path="configuration" element={<Configuration />} />
      <Route path="logs" element={<Logs />} />
      <Route path="docs" element={<Docs />} />
    </ErrorBoundaryRoutes>
  </div>
);

export default AdministrationRoutes;
