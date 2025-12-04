import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import { Produit } from './produit';
import ProduitDeleteDialog from './produit-delete-dialog';
import ProduitDetail from './produit-detail';
import ProduitUpdate from './produit-update';

const ProduitRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Produit />} />
    <Route path="new" element={<ProduitUpdate />} />
    <Route path=":id">
      <Route index element={<ProduitDetail />} />
      <Route path="edit" element={<ProduitUpdate />} />
      <Route path="delete" element={<ProduitDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProduitRoutes;
