package ch.epicerielacanopee.statistics.repository.projection;

import java.time.Instant;

public interface MouvementsStockProjection {
  Instant getDate();

  Integer getEpicerioId();

  String getCodeProduit();

  String getProduit();

  String getVente();

  Float getSolde();

  String getType();

  Float getMouvement();

  // String getUtilisateur();

  // String getRemarques();
}
