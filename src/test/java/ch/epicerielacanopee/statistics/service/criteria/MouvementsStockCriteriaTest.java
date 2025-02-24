package ch.epicerielacanopee.statistics.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class MouvementsStockCriteriaTest {

    @Test
    void newMouvementsStockCriteriaHasAllFiltersNullTest() {
        var mouvementsStockCriteria = new MouvementsStockCriteria();
        assertThat(mouvementsStockCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void mouvementsStockCriteriaFluentMethodsCreatesFiltersTest() {
        var mouvementsStockCriteria = new MouvementsStockCriteria();

        setAllFilters(mouvementsStockCriteria);

        assertThat(mouvementsStockCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void mouvementsStockCriteriaCopyCreatesNullFilterTest() {
        var mouvementsStockCriteria = new MouvementsStockCriteria();
        var copy = mouvementsStockCriteria.copy();

        assertThat(mouvementsStockCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(mouvementsStockCriteria)
        );
    }

    @Test
    void mouvementsStockCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var mouvementsStockCriteria = new MouvementsStockCriteria();
        setAllFilters(mouvementsStockCriteria);

        var copy = mouvementsStockCriteria.copy();

        assertThat(mouvementsStockCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(mouvementsStockCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var mouvementsStockCriteria = new MouvementsStockCriteria();

        assertThat(mouvementsStockCriteria).hasToString("MouvementsStockCriteria{}");
    }

    private static void setAllFilters(MouvementsStockCriteria mouvementsStockCriteria) {
        mouvementsStockCriteria.id();
        mouvementsStockCriteria.epicerioId();
        mouvementsStockCriteria.createdDate();
        mouvementsStockCriteria.lastUpdatedDate();
        mouvementsStockCriteria.importedDate();
        mouvementsStockCriteria.date();
        mouvementsStockCriteria.utilisateur();
        mouvementsStockCriteria.type();
        mouvementsStockCriteria.epicerioMouvement();
        mouvementsStockCriteria.mouvement();
        mouvementsStockCriteria.solde();
        mouvementsStockCriteria.vente();
        mouvementsStockCriteria.codeProduit();
        mouvementsStockCriteria.produit();
        mouvementsStockCriteria.responsableProduit();
        mouvementsStockCriteria.fournisseurProduit();
        mouvementsStockCriteria.codeFournisseur();
        mouvementsStockCriteria.reduction();
        mouvementsStockCriteria.ponderation();
        mouvementsStockCriteria.venteChf();
        mouvementsStockCriteria.valeurChf();
        mouvementsStockCriteria.active();
        mouvementsStockCriteria.distinct();
    }

    private static Condition<MouvementsStockCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getEpicerioId()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastUpdatedDate()) &&
                condition.apply(criteria.getImportedDate()) &&
                condition.apply(criteria.getDate()) &&
                condition.apply(criteria.getUtilisateur()) &&
                condition.apply(criteria.getType()) &&
                condition.apply(criteria.getEpicerioMouvement()) &&
                condition.apply(criteria.getMouvement()) &&
                condition.apply(criteria.getSolde()) &&
                condition.apply(criteria.getVente()) &&
                condition.apply(criteria.getCodeProduit()) &&
                condition.apply(criteria.getProduit()) &&
                condition.apply(criteria.getResponsableProduit()) &&
                condition.apply(criteria.getFournisseurProduit()) &&
                condition.apply(criteria.getCodeFournisseur()) &&
                condition.apply(criteria.getReduction()) &&
                condition.apply(criteria.getPonderation()) &&
                condition.apply(criteria.getVenteChf()) &&
                condition.apply(criteria.getValeurChf()) &&
                condition.apply(criteria.getActive()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<MouvementsStockCriteria> copyFiltersAre(
        MouvementsStockCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getEpicerioId(), copy.getEpicerioId()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getLastUpdatedDate(), copy.getLastUpdatedDate()) &&
                condition.apply(criteria.getImportedDate(), copy.getImportedDate()) &&
                condition.apply(criteria.getDate(), copy.getDate()) &&
                condition.apply(criteria.getUtilisateur(), copy.getUtilisateur()) &&
                condition.apply(criteria.getType(), copy.getType()) &&
                condition.apply(criteria.getEpicerioMouvement(), copy.getEpicerioMouvement()) &&
                condition.apply(criteria.getMouvement(), copy.getMouvement()) &&
                condition.apply(criteria.getSolde(), copy.getSolde()) &&
                condition.apply(criteria.getVente(), copy.getVente()) &&
                condition.apply(criteria.getCodeProduit(), copy.getCodeProduit()) &&
                condition.apply(criteria.getProduit(), copy.getProduit()) &&
                condition.apply(criteria.getResponsableProduit(), copy.getResponsableProduit()) &&
                condition.apply(criteria.getFournisseurProduit(), copy.getFournisseurProduit()) &&
                condition.apply(criteria.getCodeFournisseur(), copy.getCodeFournisseur()) &&
                condition.apply(criteria.getReduction(), copy.getReduction()) &&
                condition.apply(criteria.getPonderation(), copy.getPonderation()) &&
                condition.apply(criteria.getVenteChf(), copy.getVenteChf()) &&
                condition.apply(criteria.getValeurChf(), copy.getValeurChf()) &&
                condition.apply(criteria.getActive(), copy.getActive()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
