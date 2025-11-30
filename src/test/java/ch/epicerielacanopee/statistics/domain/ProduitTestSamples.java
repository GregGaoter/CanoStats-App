package ch.epicerielacanopee.statistics.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ProduitTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Produit getProduitSample1() {
        return new Produit()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .epicerioId(1)
            .nom("nom1")
            .code("code1")
            .disponible("disponible1")
            .htTtc("htTtc1")
            .vendu("vendu1")
            .unite("unite1")
            .prixParUnite("prixParUnite1")
            .fournisseur("fournisseur1")
            .refFournisseur("refFournisseur1")
            .achatFournisseur("achatFournisseur1");
    }

    public static Produit getProduitSample2() {
        return new Produit()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .epicerioId(2)
            .nom("nom2")
            .code("code2")
            .disponible("disponible2")
            .htTtc("htTtc2")
            .vendu("vendu2")
            .unite("unite2")
            .prixParUnite("prixParUnite2")
            .fournisseur("fournisseur2")
            .refFournisseur("refFournisseur2")
            .achatFournisseur("achatFournisseur2");
    }

    public static Produit getProduitRandomSampleGenerator() {
        return new Produit()
            .id(UUID.randomUUID())
            .epicerioId(intCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .code(UUID.randomUUID().toString())
            .disponible(UUID.randomUUID().toString())
            .htTtc(UUID.randomUUID().toString())
            .vendu(UUID.randomUUID().toString())
            .unite(UUID.randomUUID().toString())
            .prixParUnite(UUID.randomUUID().toString())
            .fournisseur(UUID.randomUUID().toString())
            .refFournisseur(UUID.randomUUID().toString())
            .achatFournisseur(UUID.randomUUID().toString());
    }
}
