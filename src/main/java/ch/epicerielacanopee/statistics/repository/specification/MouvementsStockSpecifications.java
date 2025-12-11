package ch.epicerielacanopee.statistics.repository.specification;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import ch.epicerielacanopee.statistics.domain.MouvementsStock;
import ch.epicerielacanopee.statistics.domain.MouvementsStock_;
import jakarta.persistence.criteria.Predicate;

public final class MouvementsStockSpecifications {

  private MouvementsStockSpecifications() {
  }

  public static Specification<MouvementsStock> codeProduitStartsWithAnyAndDateBetween(
      List<String> prefixes, Instant startDate, Instant endDate) {
    return Specification
        .where(codeProduitStartsWithAny(prefixes))
        .and(dateBetween(startDate, endDate));
  }

  public static Specification<MouvementsStock> codeProduitStartsWithAny(List<String> prefixes) {
    return (root, query, cb) -> {
      if (prefixes == null || prefixes.isEmpty()) {
        return cb.conjunction();
      }
      List<Predicate> predicateList = prefixes.stream()
          .map(p -> cb.like(root.get(MouvementsStock_.codeProduit), p + "%"))
          .collect(java.util.stream.Collectors.toList());
      return cb.or(predicateList.toArray(new Predicate[0]));
    };
  }

  public static Specification<MouvementsStock> dateBetween(Instant startDate, Instant endDate) {
    return (root, query, cb) -> cb.between(root.get(MouvementsStock_.date), startDate, endDate);
  }
}
