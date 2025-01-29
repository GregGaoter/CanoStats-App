import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './mouvements-stock.reducer';

export const MouvementsStockDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const mouvementsStockEntity = useAppSelector(state => state.mouvementsStock.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="mouvementsStockDetailsHeading">Mouvements Stock</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{mouvementsStockEntity.id}</dd>
          <dt>
            <span id="epicerioId">Epicerio Id</span>
          </dt>
          <dd>{mouvementsStockEntity.epicerioId}</dd>
          <dt>
            <span id="createdDate">Created Date</span>
          </dt>
          <dd>
            {mouvementsStockEntity.createdDate ? (
              <TextFormat value={mouvementsStockEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lastUpdatedDate">Last Updated Date</span>
          </dt>
          <dd>
            {mouvementsStockEntity.lastUpdatedDate ? (
              <TextFormat value={mouvementsStockEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="importedDate">Imported Date</span>
          </dt>
          <dd>
            {mouvementsStockEntity.importedDate ? (
              <TextFormat value={mouvementsStockEntity.importedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>
            {mouvementsStockEntity.date ? <TextFormat value={mouvementsStockEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="utilisateur">Utilisateur</span>
          </dt>
          <dd>{mouvementsStockEntity.utilisateur}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{mouvementsStockEntity.type}</dd>
          <dt>
            <span id="epicerioMouvement">Epicerio Mouvement</span>
          </dt>
          <dd>{mouvementsStockEntity.epicerioMouvement}</dd>
          <dt>
            <span id="mouvement">Mouvement</span>
          </dt>
          <dd>{mouvementsStockEntity.mouvement}</dd>
          <dt>
            <span id="solde">Solde</span>
          </dt>
          <dd>{mouvementsStockEntity.solde}</dd>
          <dt>
            <span id="vente">Vente</span>
          </dt>
          <dd>{mouvementsStockEntity.vente}</dd>
          <dt>
            <span id="codeProduit">Code Produit</span>
          </dt>
          <dd>{mouvementsStockEntity.codeProduit}</dd>
          <dt>
            <span id="produit">Produit</span>
          </dt>
          <dd>{mouvementsStockEntity.produit}</dd>
          <dt>
            <span id="responsableProduit">Responsable Produit</span>
          </dt>
          <dd>{mouvementsStockEntity.responsableProduit}</dd>
          <dt>
            <span id="fournisseurProduit">Fournisseur Produit</span>
          </dt>
          <dd>{mouvementsStockEntity.fournisseurProduit}</dd>
          <dt>
            <span id="codeFournisseur">Code Fournisseur</span>
          </dt>
          <dd>{mouvementsStockEntity.codeFournisseur}</dd>
          <dt>
            <span id="reduction">Reduction</span>
          </dt>
          <dd>{mouvementsStockEntity.reduction}</dd>
          <dt>
            <span id="ponderation">Ponderation</span>
          </dt>
          <dd>{mouvementsStockEntity.ponderation}</dd>
          <dt>
            <span id="venteChf">Vente Chf</span>
          </dt>
          <dd>{mouvementsStockEntity.venteChf}</dd>
          <dt>
            <span id="valeurChf">Valeur Chf</span>
          </dt>
          <dd>{mouvementsStockEntity.valeurChf}</dd>
          <dt>
            <span id="remarques">Remarques</span>
          </dt>
          <dd>{mouvementsStockEntity.remarques}</dd>
          <dt>
            <span id="active">Active</span>
          </dt>
          <dd>{mouvementsStockEntity.active ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/mouvements-stock" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/mouvements-stock/${mouvementsStockEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default MouvementsStockDetail;
