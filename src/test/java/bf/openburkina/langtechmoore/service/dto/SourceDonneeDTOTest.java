package bf.openburkina.langtechmoore.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import bf.openburkina.langtechmoore.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SourceDonneeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SourceDonneeDTO.class);
        SourceDonneeDTO sourceDonneeDTO1 = new SourceDonneeDTO();
        sourceDonneeDTO1.setId(1L);
        SourceDonneeDTO sourceDonneeDTO2 = new SourceDonneeDTO();
        assertThat(sourceDonneeDTO1).isNotEqualTo(sourceDonneeDTO2);
        sourceDonneeDTO2.setId(sourceDonneeDTO1.getId());
        assertThat(sourceDonneeDTO1).isEqualTo(sourceDonneeDTO2);
        sourceDonneeDTO2.setId(2L);
        assertThat(sourceDonneeDTO1).isNotEqualTo(sourceDonneeDTO2);
        sourceDonneeDTO1.setId(null);
        assertThat(sourceDonneeDTO1).isNotEqualTo(sourceDonneeDTO2);
    }
}
