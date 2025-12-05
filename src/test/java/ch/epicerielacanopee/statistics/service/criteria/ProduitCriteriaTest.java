package ch.epicerielacanopee.statistics.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ProduitCriteriaTest {

    @Test
    void newProduitCriteriaHasAllFiltersNullTest() {
        var produitCriteria = new ProduitCriteria();
        assertThat(produitCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void produitCriteriaFluentMethodsCreatesFiltersTest() {
        var produitCriteria = new ProduitCriteria();

        setAllFilters(produitCriteria);

        assertThat(produitCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void produitCriteriaCopyCreatesNullFilterTest() {
        var produitCriteria = new ProduitCriteria();
        var copy = produitCriteria.copy();

        assertThat(produitCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(produitCriteria)
        );
    }

    @Test
    void produitCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var produitCriteria = new ProduitCriteria();
        setAllFilters(produitCriteria);

        var copy = produitCriteria.copy();

        assertThat(produitCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(produitCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var produitCriteria = new ProduitCriteria();

        assertThat(produitCriteria).hasToString("ProduitCriteria{}");
    }

    private static void setAllFilters(ProduitCriteria produitCriteria) {
        produitCriteria.id();
        produitCriteria.epicerioId();
        produitCriteria.createdDate();
        produitCriteria.lastUpdatedDate();
        produitCriteria.importedDate();
        produitCriteria.nom();
        produitCriteria.code();
        produitCriteria.disponible();
        produitCriteria.prixFournisseur();
        produitCriteria.htTtc();
        produitCriteria.tauxTva();
        produitCriteria.margeProfit();
        produitCriteria.prixVente();
        produitCriteria.vendu();
        produitCriteria.quantiteParPiece();
        produitCriteria.unite();
        produitCriteria.prixParUnite();
        produitCriteria.fournisseur();
        produitCriteria.refFournisseur();
        produitCriteria.stock();
        produitCriteria.commandesClients();
        produitCriteria.derniereVerificationDate();
        produitCriteria.derniereLivraisonDate();
        produitCriteria.achatFournisseur();
        produitCriteria.dernierAchatDate();
        produitCriteria.dernierAchatQuantite();
        produitCriteria.statsLivraison();
        produitCriteria.statsPerte();
        produitCriteria.statsVente();
        produitCriteria.statsVenteSpeciale();
        produitCriteria.distinct();
    }

    private static Condition<ProduitCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getEpicerioId()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastUpdatedDate()) &&
                condition.apply(criteria.getImportedDate()) &&
                condition.apply(criteria.getNom()) &&
                condition.apply(criteria.getCode()) &&
                condition.apply(criteria.getDisponible()) &&
                condition.apply(criteria.getPrixFournisseur()) &&
                condition.apply(criteria.getHtTtc()) &&
                condition.apply(criteria.getTauxTva()) &&
                condition.apply(criteria.getMargeProfit()) &&
                condition.apply(criteria.getPrixVente()) &&
                condition.apply(criteria.getVendu()) &&
                condition.apply(criteria.getQuantiteParPiece()) &&
                condition.apply(criteria.getUnite()) &&
                condition.apply(criteria.getPrixParUnite()) &&
                condition.apply(criteria.getFournisseur()) &&
                condition.apply(criteria.getRefFournisseur()) &&
                condition.apply(criteria.getStock()) &&
                condition.apply(criteria.getCommandesClients()) &&
                condition.apply(criteria.getDerniereVerificationDate()) &&
                condition.apply(criteria.getDerniereLivraisonDate()) &&
                condition.apply(criteria.getAchatFournisseur()) &&
                condition.apply(criteria.getDernierAchatDate()) &&
                condition.apply(criteria.getDernierAchatQuantite()) &&
                condition.apply(criteria.getStatsLivraison()) &&
                condition.apply(criteria.getStatsPerte()) &&
                condition.apply(criteria.getStatsVente()) &&
                condition.apply(criteria.getStatsVenteSpeciale()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ProduitCriteria> copyFiltersAre(ProduitCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getEpicerioId(), copy.getEpicerioId()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getLastUpdatedDate(), copy.getLastUpdatedDate()) &&
                condition.apply(criteria.getImportedDate(), copy.getImportedDate()) &&
                condition.apply(criteria.getNom(), copy.getNom()) &&
                condition.apply(criteria.getCode(), copy.getCode()) &&
                condition.apply(criteria.getDisponible(), copy.getDisponible()) &&
                condition.apply(criteria.getPrixFournisseur(), copy.getPrixFournisseur()) &&
                condition.apply(criteria.getHtTtc(), copy.getHtTtc()) &&
                condition.apply(criteria.getTauxTva(), copy.getTauxTva()) &&
                condition.apply(criteria.getMargeProfit(), copy.getMargeProfit()) &&
                condition.apply(criteria.getPrixVente(), copy.getPrixVente()) &&
                condition.apply(criteria.getVendu(), copy.getVendu()) &&
                condition.apply(criteria.getQuantiteParPiece(), copy.getQuantiteParPiece()) &&
                condition.apply(criteria.getUnite(), copy.getUnite()) &&
                condition.apply(criteria.getPrixParUnite(), copy.getPrixParUnite()) &&
                condition.apply(criteria.getFournisseur(), copy.getFournisseur()) &&
                condition.apply(criteria.getRefFournisseur(), copy.getRefFournisseur()) &&
                condition.apply(criteria.getStock(), copy.getStock()) &&
                condition.apply(criteria.getCommandesClients(), copy.getCommandesClients()) &&
                condition.apply(criteria.getDerniereVerificationDate(), copy.getDerniereVerificationDate()) &&
                condition.apply(criteria.getDerniereLivraisonDate(), copy.getDerniereLivraisonDate()) &&
                condition.apply(criteria.getAchatFournisseur(), copy.getAchatFournisseur()) &&
                condition.apply(criteria.getDernierAchatDate(), copy.getDernierAchatDate()) &&
                condition.apply(criteria.getDernierAchatQuantite(), copy.getDernierAchatQuantite()) &&
                condition.apply(criteria.getStatsLivraison(), copy.getStatsLivraison()) &&
                condition.apply(criteria.getStatsPerte(), copy.getStatsPerte()) &&
                condition.apply(criteria.getStatsVente(), copy.getStatsVente()) &&
                condition.apply(criteria.getStatsVenteSpeciale(), copy.getStatsVenteSpeciale()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
