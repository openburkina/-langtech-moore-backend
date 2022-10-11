package bf.openburkina.langtechmoore.domain;

import static org.assertj.core.api.Assertions.assertThat;

import bf.openburkina.langtechmoore.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TraductionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Traduction.class);
        Traduction traduction1 = new Traduction();
        traduction1.setId(1L);
        Traduction traduction2 = new Traduction();
        traduction2.setId(traduction1.getId());
        assertThat(traduction1).isEqualTo(traduction2);
        traduction2.setId(2L);
        assertThat(traduction1).isNotEqualTo(traduction2);
        traduction1.setId(null);
        assertThat(traduction1).isNotEqualTo(traduction2);
    }
}
