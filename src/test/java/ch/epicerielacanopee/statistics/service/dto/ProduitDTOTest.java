package ch.epicerielacanopee.statistics.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ch.epicerielacanopee.statistics.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ProduitDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProduitDTO.class);
        ProduitDTO produitDTO1 = new ProduitDTO();
        produitDTO1.setId(UUID.randomUUID());
        ProduitDTO produitDTO2 = new ProduitDTO();
        assertThat(produitDTO1).isNotEqualTo(produitDTO2);
        produitDTO2.setId(produitDTO1.getId());
        assertThat(produitDTO1).isEqualTo(produitDTO2);
        produitDTO2.setId(UUID.randomUUID());
        assertThat(produitDTO1).isNotEqualTo(produitDTO2);
        produitDTO1.setId(null);
        assertThat(produitDTO1).isNotEqualTo(produitDTO2);
    }
}
