package ch.epicerielacanopee.statistics.repository.projection;

import java.time.Instant;

public interface MouvementsStockProjection {
  Instant getDate();

  String getCodeProduit();

  String getProduit();

  String getVente();

  Float getSolde();

  String getType();

  Float getMouvement();
}
