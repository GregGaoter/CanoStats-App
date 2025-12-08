package ch.epicerielacanopee.statistics.repository.projection;

import java.time.Instant;

public interface MouvementsStockDateRangeProjection {
  
  Instant getMinDate();
  Instant getMaxDate();
}
