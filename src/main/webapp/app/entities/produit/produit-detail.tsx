import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './produit.reducer';

export const ProduitDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const produitEntity = useAppSelector(state => state.produit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="produitDetailsHeading">Produit</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{produitEntity.id}</dd>
          <dt>
            <span id="epicerioId">Epicerio Id</span>
          </dt>
          <dd>{produitEntity.epicerioId}</dd>
          <dt>
            <span id="createdDate">Created Date</span>
          </dt>
          <dd>
            {produitEntity.createdDate ? <TextFormat value={produitEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="lastUpdatedDate">Last Updated Date</span>
          </dt>
          <dd>
            {produitEntity.lastUpdatedDate ? (
              <TextFormat value={produitEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="importedDate">Imported Date</span>
          </dt>
          <dd>
            {produitEntity.importedDate ? <TextFormat value={produitEntity.importedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="nom">Nom</span>
          </dt>
          <dd>{produitEntity.nom}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{produitEntity.code}</dd>
          <dt>
            <span id="disponible">Disponible</span>
          </dt>
          <dd>{produitEntity.disponible}</dd>
          <dt>
            <span id="prixFournisseur">Prix Fournisseur</span>
          </dt>
          <dd>{produitEntity.prixFournisseur}</dd>
          <dt>
            <span id="htTtc">Ht Ttc</span>
          </dt>
          <dd>{produitEntity.htTtc}</dd>
          <dt>
            <span id="tauxTva">Taux Tva</span>
          </dt>
          <dd>{produitEntity.tauxTva}</dd>
          <dt>
            <span id="margeProfit">Marge Profit</span>
          </dt>
          <dd>{produitEntity.margeProfit}</dd>
          <dt>
            <span id="prixVente">Prix Vente</span>
          </dt>
          <dd>{produitEntity.prixVente}</dd>
          <dt>
            <span id="vendu">Vendu</span>
          </dt>
          <dd>{produitEntity.vendu}</dd>
          <dt>
            <span id="quantiteParPiece">Quantite Par Piece</span>
          </dt>
          <dd>{produitEntity.quantiteParPiece}</dd>
          <dt>
            <span id="unite">Unite</span>
          </dt>
          <dd>{produitEntity.unite}</dd>
          <dt>
            <span id="prixParUnite">Prix Par Unite</span>
          </dt>
          <dd>{produitEntity.prixParUnite}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{produitEntity.description}</dd>
          <dt>
            <span id="remarquesInternes">Remarques Internes</span>
          </dt>
          <dd>{produitEntity.remarquesInternes}</dd>
          <dt>
            <span id="fournisseur">Fournisseur</span>
          </dt>
          <dd>{produitEntity.fournisseur}</dd>
          <dt>
            <span id="refFournisseur">Ref Fournisseur</span>
          </dt>
          <dd>{produitEntity.refFournisseur}</dd>
          <dt>
            <span id="stock">Stock</span>
          </dt>
          <dd>{produitEntity.stock}</dd>
          <dt>
            <span id="commandesClients">Commandes Clients</span>
          </dt>
          <dd>{produitEntity.commandesClients}</dd>
          <dt>
            <span id="derniereVerificationDate">Derniere Verification Date</span>
          </dt>
          <dd>
            {produitEntity.derniereVerificationDate ? (
              <TextFormat value={produitEntity.derniereVerificationDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="derniereLivraisonDate">Derniere Livraison Date</span>
          </dt>
          <dd>
            {produitEntity.derniereLivraisonDate ? (
              <TextFormat value={produitEntity.derniereLivraisonDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="achatFournisseur">Achat Fournisseur</span>
          </dt>
          <dd>{produitEntity.achatFournisseur}</dd>
          <dt>
            <span id="dernierAchatDate">Dernier Achat Date</span>
          </dt>
          <dd>
            {produitEntity.dernierAchatDate ? (
              <TextFormat value={produitEntity.dernierAchatDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="dernierAchatQuantite">Dernier Achat Quantite</span>
          </dt>
          <dd>{produitEntity.dernierAchatQuantite}</dd>
          <dt>
            <span id="statsLivraison">Stats Livraison</span>
          </dt>
          <dd>{produitEntity.statsLivraison}</dd>
          <dt>
            <span id="statsPerte">Stats Perte</span>
          </dt>
          <dd>{produitEntity.statsPerte}</dd>
          <dt>
            <span id="statsVente">Stats Vente</span>
          </dt>
          <dd>{produitEntity.statsVente}</dd>
          <dt>
            <span id="statsVenteSpeciale">Stats Vente Speciale</span>
          </dt>
          <dd>{produitEntity.statsVenteSpeciale}</dd>
          <dt>
            <span id="tags">Tags</span>
          </dt>
          <dd>{produitEntity.tags}</dd>
        </dl>
        <Button tag={Link} to="/produit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/produit/${produitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProduitDetail;
