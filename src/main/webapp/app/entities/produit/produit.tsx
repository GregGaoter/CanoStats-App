import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { JhiItemCount, JhiPagination, TextFormat, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './produit.reducer';

export const Produit = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const produitList = useAppSelector(state => state.produit.entities);
  const loading = useAppSelector(state => state.produit.loading);
  const totalItems = useAppSelector(state => state.produit.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    }
    return order === ASC ? faSortUp : faSortDown;
  };

  return (
    <div>
      <h2 id="produit-heading" data-cy="ProduitHeading">
        Produits
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/produit/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Produit
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {produitList && produitList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  Id <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('epicerioId')}>
                  Epicerio Id <FontAwesomeIcon icon={getSortIconByFieldName('epicerioId')} />
                </th>
                <th className="hand" onClick={sort('createdDate')}>
                  Created Date <FontAwesomeIcon icon={getSortIconByFieldName('createdDate')} />
                </th>
                <th className="hand" onClick={sort('lastUpdatedDate')}>
                  Last Updated Date <FontAwesomeIcon icon={getSortIconByFieldName('lastUpdatedDate')} />
                </th>
                <th className="hand" onClick={sort('importedDate')}>
                  Imported Date <FontAwesomeIcon icon={getSortIconByFieldName('importedDate')} />
                </th>
                <th className="hand" onClick={sort('nom')}>
                  Nom <FontAwesomeIcon icon={getSortIconByFieldName('nom')} />
                </th>
                <th className="hand" onClick={sort('code')}>
                  Code <FontAwesomeIcon icon={getSortIconByFieldName('code')} />
                </th>
                <th className="hand" onClick={sort('disponible')}>
                  Disponible <FontAwesomeIcon icon={getSortIconByFieldName('disponible')} />
                </th>
                <th className="hand" onClick={sort('prixFournisseur')}>
                  Prix Fournisseur <FontAwesomeIcon icon={getSortIconByFieldName('prixFournisseur')} />
                </th>
                <th className="hand" onClick={sort('htTtc')}>
                  Ht Ttc <FontAwesomeIcon icon={getSortIconByFieldName('htTtc')} />
                </th>
                <th className="hand" onClick={sort('tauxTva')}>
                  Taux Tva <FontAwesomeIcon icon={getSortIconByFieldName('tauxTva')} />
                </th>
                <th className="hand" onClick={sort('margeProfit')}>
                  Marge Profit <FontAwesomeIcon icon={getSortIconByFieldName('margeProfit')} />
                </th>
                <th className="hand" onClick={sort('prixVente')}>
                  Prix Vente <FontAwesomeIcon icon={getSortIconByFieldName('prixVente')} />
                </th>
                <th className="hand" onClick={sort('vendu')}>
                  Vendu <FontAwesomeIcon icon={getSortIconByFieldName('vendu')} />
                </th>
                <th className="hand" onClick={sort('quantiteParPiece')}>
                  Quantite Par Piece <FontAwesomeIcon icon={getSortIconByFieldName('quantiteParPiece')} />
                </th>
                <th className="hand" onClick={sort('unite')}>
                  Unite <FontAwesomeIcon icon={getSortIconByFieldName('unite')} />
                </th>
                <th className="hand" onClick={sort('prixParUnite')}>
                  Prix Par Unite <FontAwesomeIcon icon={getSortIconByFieldName('prixParUnite')} />
                </th>
                <th className="hand" onClick={sort('description')}>
                  Description <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                </th>
                <th className="hand" onClick={sort('remarquesInternes')}>
                  Remarques Internes <FontAwesomeIcon icon={getSortIconByFieldName('remarquesInternes')} />
                </th>
                <th className="hand" onClick={sort('fournisseur')}>
                  Fournisseur <FontAwesomeIcon icon={getSortIconByFieldName('fournisseur')} />
                </th>
                <th className="hand" onClick={sort('refFournisseur')}>
                  Ref Fournisseur <FontAwesomeIcon icon={getSortIconByFieldName('refFournisseur')} />
                </th>
                <th className="hand" onClick={sort('stock')}>
                  Stock <FontAwesomeIcon icon={getSortIconByFieldName('stock')} />
                </th>
                <th className="hand" onClick={sort('commandesClients')}>
                  Commandes Clients <FontAwesomeIcon icon={getSortIconByFieldName('commandesClients')} />
                </th>
                <th className="hand" onClick={sort('derniereVerificationDate')}>
                  Derniere Verification Date <FontAwesomeIcon icon={getSortIconByFieldName('derniereVerificationDate')} />
                </th>
                <th className="hand" onClick={sort('derniereLivraisonDate')}>
                  Derniere Livraison Date <FontAwesomeIcon icon={getSortIconByFieldName('derniereLivraisonDate')} />
                </th>
                <th className="hand" onClick={sort('achatFournisseur')}>
                  Achat Fournisseur <FontAwesomeIcon icon={getSortIconByFieldName('achatFournisseur')} />
                </th>
                <th className="hand" onClick={sort('dernierAchatDate')}>
                  Dernier Achat Date <FontAwesomeIcon icon={getSortIconByFieldName('dernierAchatDate')} />
                </th>
                <th className="hand" onClick={sort('dernierAchatQuantite')}>
                  Dernier Achat Quantite <FontAwesomeIcon icon={getSortIconByFieldName('dernierAchatQuantite')} />
                </th>
                <th className="hand" onClick={sort('statsLivraison')}>
                  Stats Livraison <FontAwesomeIcon icon={getSortIconByFieldName('statsLivraison')} />
                </th>
                <th className="hand" onClick={sort('statsPerte')}>
                  Stats Perte <FontAwesomeIcon icon={getSortIconByFieldName('statsPerte')} />
                </th>
                <th className="hand" onClick={sort('statsVente')}>
                  Stats Vente <FontAwesomeIcon icon={getSortIconByFieldName('statsVente')} />
                </th>
                <th className="hand" onClick={sort('statsVenteSpeciale')}>
                  Stats Vente Speciale <FontAwesomeIcon icon={getSortIconByFieldName('statsVenteSpeciale')} />
                </th>
                <th className="hand" onClick={sort('tags')}>
                  Tags <FontAwesomeIcon icon={getSortIconByFieldName('tags')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {produitList.map((produit, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/produit/${produit.id}`} color="link" size="sm">
                      {produit.id}
                    </Button>
                  </td>
                  <td>{produit.epicerioId}</td>
                  <td>{produit.createdDate ? <TextFormat type="date" value={produit.createdDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>
                    {produit.lastUpdatedDate ? <TextFormat type="date" value={produit.lastUpdatedDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{produit.importedDate ? <TextFormat type="date" value={produit.importedDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{produit.nom}</td>
                  <td>{produit.code}</td>
                  <td>{produit.disponible}</td>
                  <td>{produit.prixFournisseur}</td>
                  <td>{produit.htTtc}</td>
                  <td>{produit.tauxTva}</td>
                  <td>{produit.margeProfit}</td>
                  <td>{produit.prixVente}</td>
                  <td>{produit.vendu}</td>
                  <td>{produit.quantiteParPiece}</td>
                  <td>{produit.unite}</td>
                  <td>{produit.prixParUnite}</td>
                  <td>{produit.description}</td>
                  <td>{produit.remarquesInternes}</td>
                  <td>{produit.fournisseur}</td>
                  <td>{produit.refFournisseur}</td>
                  <td>{produit.stock}</td>
                  <td>{produit.commandesClients}</td>
                  <td>
                    {produit.derniereVerificationDate ? (
                      <TextFormat type="date" value={produit.derniereVerificationDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {produit.derniereLivraisonDate ? (
                      <TextFormat type="date" value={produit.derniereLivraisonDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{produit.achatFournisseur}</td>
                  <td>
                    {produit.dernierAchatDate ? (
                      <TextFormat type="date" value={produit.dernierAchatDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{produit.dernierAchatQuantite}</td>
                  <td>{produit.statsLivraison}</td>
                  <td>{produit.statsPerte}</td>
                  <td>{produit.statsVente}</td>
                  <td>{produit.statsVenteSpeciale}</td>
                  <td>{produit.tags}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/produit/${produit.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/produit/${produit.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() =>
                          (window.location.href = `/produit/${produit.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                        }
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Produits found</div>
        )}
      </div>
      {totalItems ? (
        <div className={produitList && produitList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Produit;
