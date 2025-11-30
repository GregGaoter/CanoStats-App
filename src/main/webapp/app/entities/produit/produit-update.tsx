import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { createEntity, getEntity, reset, updateEntity } from './produit.reducer';

export const ProduitUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const produitEntity = useAppSelector(state => state.produit.entity);
  const loading = useAppSelector(state => state.produit.loading);
  const updating = useAppSelector(state => state.produit.updating);
  const updateSuccess = useAppSelector(state => state.produit.updateSuccess);

  const handleClose = () => {
    navigate(`/produit${location.search}`);
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
    if (values.prixFournisseur !== undefined && typeof values.prixFournisseur !== 'number') {
      values.prixFournisseur = Number(values.prixFournisseur);
    }
    if (values.tauxTva !== undefined && typeof values.tauxTva !== 'number') {
      values.tauxTva = Number(values.tauxTva);
    }
    if (values.margeProfit !== undefined && typeof values.margeProfit !== 'number') {
      values.margeProfit = Number(values.margeProfit);
    }
    if (values.prixVente !== undefined && typeof values.prixVente !== 'number') {
      values.prixVente = Number(values.prixVente);
    }
    if (values.quantiteParPiece !== undefined && typeof values.quantiteParPiece !== 'number') {
      values.quantiteParPiece = Number(values.quantiteParPiece);
    }
    if (values.stock !== undefined && typeof values.stock !== 'number') {
      values.stock = Number(values.stock);
    }
    if (values.commandesClients !== undefined && typeof values.commandesClients !== 'number') {
      values.commandesClients = Number(values.commandesClients);
    }
    if (values.dernierAchatQuantite !== undefined && typeof values.dernierAchatQuantite !== 'number') {
      values.dernierAchatQuantite = Number(values.dernierAchatQuantite);
    }
    if (values.statsLivraison !== undefined && typeof values.statsLivraison !== 'number') {
      values.statsLivraison = Number(values.statsLivraison);
    }
    if (values.statsPerte !== undefined && typeof values.statsPerte !== 'number') {
      values.statsPerte = Number(values.statsPerte);
    }
    if (values.statsVente !== undefined && typeof values.statsVente !== 'number') {
      values.statsVente = Number(values.statsVente);
    }
    if (values.statsVenteSpeciale !== undefined && typeof values.statsVenteSpeciale !== 'number') {
      values.statsVenteSpeciale = Number(values.statsVenteSpeciale);
    }

    const entity = {
      ...produitEntity,
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
        }
      : {
          ...produitEntity,
          createdDate: convertDateTimeFromServer(produitEntity.createdDate),
          lastUpdatedDate: convertDateTimeFromServer(produitEntity.lastUpdatedDate),
          importedDate: convertDateTimeFromServer(produitEntity.importedDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="statisticsApp.produit.home.createOrEditLabel" data-cy="ProduitCreateUpdateHeading">
            Create or edit a Produit
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="produit-id" label="Id" validate={{ required: true }} /> : null}
              <ValidatedField label="Epicerio Id" id="produit-epicerioId" name="epicerioId" data-cy="epicerioId" type="text" />
              <ValidatedField
                label="Created Date"
                id="produit-createdDate"
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
                id="produit-lastUpdatedDate"
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
                id="produit-importedDate"
                name="importedDate"
                data-cy="importedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField label="Nom" id="produit-nom" name="nom" data-cy="nom" type="text" />
              <ValidatedField label="Code" id="produit-code" name="code" data-cy="code" type="text" />
              <ValidatedField label="Disponible" id="produit-disponible" name="disponible" data-cy="disponible" type="text" />
              <ValidatedField
                label="Prix Fournisseur"
                id="produit-prixFournisseur"
                name="prixFournisseur"
                data-cy="prixFournisseur"
                type="text"
              />
              <ValidatedField label="Ht Ttc" id="produit-htTtc" name="htTtc" data-cy="htTtc" type="text" />
              <ValidatedField label="Taux Tva" id="produit-tauxTva" name="tauxTva" data-cy="tauxTva" type="text" />
              <ValidatedField label="Marge Profit" id="produit-margeProfit" name="margeProfit" data-cy="margeProfit" type="text" />
              <ValidatedField label="Prix Vente" id="produit-prixVente" name="prixVente" data-cy="prixVente" type="text" />
              <ValidatedField label="Vendu" id="produit-vendu" name="vendu" data-cy="vendu" type="text" />
              <ValidatedField
                label="Quantite Par Piece"
                id="produit-quantiteParPiece"
                name="quantiteParPiece"
                data-cy="quantiteParPiece"
                type="text"
              />
              <ValidatedField label="Unite" id="produit-unite" name="unite" data-cy="unite" type="text" />
              <ValidatedField label="Prix Par Unite" id="produit-prixParUnite" name="prixParUnite" data-cy="prixParUnite" type="text" />
              <ValidatedField label="Description" id="produit-description" name="description" data-cy="description" type="textarea" />
              <ValidatedField
                label="Remarques Internes"
                id="produit-remarquesInternes"
                name="remarquesInternes"
                data-cy="remarquesInternes"
                type="textarea"
              />
              <ValidatedField label="Fournisseur" id="produit-fournisseur" name="fournisseur" data-cy="fournisseur" type="text" />
              <ValidatedField
                label="Ref Fournisseur"
                id="produit-refFournisseur"
                name="refFournisseur"
                data-cy="refFournisseur"
                type="text"
              />
              <ValidatedField label="Stock" id="produit-stock" name="stock" data-cy="stock" type="text" />
              <ValidatedField
                label="Commandes Clients"
                id="produit-commandesClients"
                name="commandesClients"
                data-cy="commandesClients"
                type="text"
              />
              <ValidatedField
                label="Derniere Verification Date"
                id="produit-derniereVerificationDate"
                name="derniereVerificationDate"
                data-cy="derniereVerificationDate"
                type="date"
              />
              <ValidatedField
                label="Derniere Livraison Date"
                id="produit-derniereLivraisonDate"
                name="derniereLivraisonDate"
                data-cy="derniereLivraisonDate"
                type="date"
              />
              <ValidatedField
                label="Achat Fournisseur"
                id="produit-achatFournisseur"
                name="achatFournisseur"
                data-cy="achatFournisseur"
                type="text"
              />
              <ValidatedField
                label="Dernier Achat Date"
                id="produit-dernierAchatDate"
                name="dernierAchatDate"
                data-cy="dernierAchatDate"
                type="date"
              />
              <ValidatedField
                label="Dernier Achat Quantite"
                id="produit-dernierAchatQuantite"
                name="dernierAchatQuantite"
                data-cy="dernierAchatQuantite"
                type="text"
              />
              <ValidatedField
                label="Stats Livraison"
                id="produit-statsLivraison"
                name="statsLivraison"
                data-cy="statsLivraison"
                type="text"
              />
              <ValidatedField label="Stats Perte" id="produit-statsPerte" name="statsPerte" data-cy="statsPerte" type="text" />
              <ValidatedField label="Stats Vente" id="produit-statsVente" name="statsVente" data-cy="statsVente" type="text" />
              <ValidatedField
                label="Stats Vente Speciale"
                id="produit-statsVenteSpeciale"
                name="statsVenteSpeciale"
                data-cy="statsVenteSpeciale"
                type="text"
              />
              <ValidatedField label="Tags" id="produit-tags" name="tags" data-cy="tags" type="textarea" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/produit" replace color="info">
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

export default ProduitUpdate;
