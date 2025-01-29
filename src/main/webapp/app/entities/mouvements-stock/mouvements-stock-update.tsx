import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { createEntity, getEntity, reset, updateEntity } from './mouvements-stock.reducer';

export const MouvementsStockUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const mouvementsStockEntity = useAppSelector(state => state.mouvementsStock.entity);
  const loading = useAppSelector(state => state.mouvementsStock.loading);
  const updating = useAppSelector(state => state.mouvementsStock.updating);
  const updateSuccess = useAppSelector(state => state.mouvementsStock.updateSuccess);

  const handleClose = () => {
    navigate(`/mouvements-stock${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.epicerioId !== undefined && typeof values.epicerioId !== 'number') {
      values.epicerioId = Number(values.epicerioId);
    }
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.lastUpdatedDate = convertDateTimeToServer(values.lastUpdatedDate);
    values.importedDate = convertDateTimeToServer(values.importedDate);
    values.date = convertDateTimeToServer(values.date);
    if (values.epicerioMouvement !== undefined && typeof values.epicerioMouvement !== 'number') {
      values.epicerioMouvement = Number(values.epicerioMouvement);
    }
    if (values.mouvement !== undefined && typeof values.mouvement !== 'number') {
      values.mouvement = Number(values.mouvement);
    }
    if (values.solde !== undefined && typeof values.solde !== 'number') {
      values.solde = Number(values.solde);
    }
    if (values.reduction !== undefined && typeof values.reduction !== 'number') {
      values.reduction = Number(values.reduction);
    }
    if (values.ponderation !== undefined && typeof values.ponderation !== 'number') {
      values.ponderation = Number(values.ponderation);
    }
    if (values.venteChf !== undefined && typeof values.venteChf !== 'number') {
      values.venteChf = Number(values.venteChf);
    }
    if (values.valeurChf !== undefined && typeof values.valeurChf !== 'number') {
      values.valeurChf = Number(values.valeurChf);
    }

    const entity = {
      ...mouvementsStockEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          createdDate: displayDefaultDateTime(),
          lastUpdatedDate: displayDefaultDateTime(),
          importedDate: displayDefaultDateTime(),
          date: displayDefaultDateTime(),
        }
      : {
          ...mouvementsStockEntity,
          createdDate: convertDateTimeFromServer(mouvementsStockEntity.createdDate),
          lastUpdatedDate: convertDateTimeFromServer(mouvementsStockEntity.lastUpdatedDate),
          importedDate: convertDateTimeFromServer(mouvementsStockEntity.importedDate),
          date: convertDateTimeFromServer(mouvementsStockEntity.date),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="statisticsApp.mouvementsStock.home.createOrEditLabel" data-cy="MouvementsStockCreateUpdateHeading">
            Create or edit a Mouvements Stock
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="mouvements-stock-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Epicerio Id" id="mouvements-stock-epicerioId" name="epicerioId" data-cy="epicerioId" type="text" />
              <ValidatedField
                label="Created Date"
                id="mouvements-stock-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Last Updated Date"
                id="mouvements-stock-lastUpdatedDate"
                name="lastUpdatedDate"
                data-cy="lastUpdatedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Imported Date"
                id="mouvements-stock-importedDate"
                name="importedDate"
                data-cy="importedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Date"
                id="mouvements-stock-date"
                name="date"
                data-cy="date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Utilisateur" id="mouvements-stock-utilisateur" name="utilisateur" data-cy="utilisateur" type="text" />
              <ValidatedField label="Type" id="mouvements-stock-type" name="type" data-cy="type" type="text" />
              <ValidatedField
                label="Epicerio Mouvement"
                id="mouvements-stock-epicerioMouvement"
                name="epicerioMouvement"
                data-cy="epicerioMouvement"
                type="text"
              />
              <ValidatedField label="Mouvement" id="mouvements-stock-mouvement" name="mouvement" data-cy="mouvement" type="text" />
              <ValidatedField label="Solde" id="mouvements-stock-solde" name="solde" data-cy="solde" type="text" />
              <ValidatedField label="Vente" id="mouvements-stock-vente" name="vente" data-cy="vente" type="text" />
              <ValidatedField label="Code Produit" id="mouvements-stock-codeProduit" name="codeProduit" data-cy="codeProduit" type="text" />
              <ValidatedField label="Produit" id="mouvements-stock-produit" name="produit" data-cy="produit" type="text" />
              <ValidatedField
                label="Responsable Produit"
                id="mouvements-stock-responsableProduit"
                name="responsableProduit"
                data-cy="responsableProduit"
                type="text"
              />
              <ValidatedField
                label="Fournisseur Produit"
                id="mouvements-stock-fournisseurProduit"
                name="fournisseurProduit"
                data-cy="fournisseurProduit"
                type="text"
              />
              <ValidatedField
                label="Code Fournisseur"
                id="mouvements-stock-codeFournisseur"
                name="codeFournisseur"
                data-cy="codeFournisseur"
                type="text"
              />
              <ValidatedField label="Reduction" id="mouvements-stock-reduction" name="reduction" data-cy="reduction" type="text" />
              <ValidatedField label="Ponderation" id="mouvements-stock-ponderation" name="ponderation" data-cy="ponderation" type="text" />
              <ValidatedField label="Vente Chf" id="mouvements-stock-venteChf" name="venteChf" data-cy="venteChf" type="text" />
              <ValidatedField label="Valeur Chf" id="mouvements-stock-valeurChf" name="valeurChf" data-cy="valeurChf" type="text" />
              <ValidatedField label="Remarques" id="mouvements-stock-remarques" name="remarques" data-cy="remarques" type="textarea" />
              <ValidatedField label="Active" id="mouvements-stock-active" name="active" data-cy="active" check type="checkbox" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/mouvements-stock" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default MouvementsStockUpdate;
