package bf.openburkina.langtechmoore.domain;

import static org.assertj.core.api.Assertions.assertThat;

import bf.openburkina.langtechmoore.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SourceDonneeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SourceDonnee.class);
        SourceDonnee sourceDonnee1 = new SourceDonnee();
        sourceDonnee1.setId(1L);
        SourceDonnee sourceDonnee2 = new SourceDonnee();
        sourceDonnee2.setId(sourceDonnee1.getId());
        assertThat(sourceDonnee1).isEqualTo(sourceDonnee2);
        sourceDonnee2.setId(2L);
        assertThat(sourceDonnee1).isNotEqualTo(sourceDonnee2);
        sourceDonnee1.setId(null);
        assertThat(sourceDonnee1).isNotEqualTo(sourceDonnee2);
    }
}
