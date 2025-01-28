import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';
import React from 'react';
import { Route } from 'react-router';
import MouvementsStock from './mouvements-stock/mouvements-stock';
import MouvementsStockDeleteDialog from './mouvements-stock/mouvements-stock-delete-dialog';
import MouvementsStockDetail from './mouvements-stock/mouvements-stock-detail';
import MouvementsStockUpdate from './mouvements-stock/mouvements-stock-update';

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        <Route path="table">
          <Route path="mouvements-stock">
            <Route index element={<MouvementsStock />} />
            <Route path="new" element={<MouvementsStockUpdate />} />
            <Route path=":id">
              <Route index element={<MouvementsStockDetail />} />
              <Route path="edit" element={<MouvementsStockUpdate />} />
              <Route path="delete" element={<MouvementsStockDeleteDialog />} />
            </Route>
          </Route>
        </Route>
      </ErrorBoundaryRoutes>
    </div>
  );
};
