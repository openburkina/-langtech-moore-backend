package bf.openburkina.langtechmoore.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import bf.openburkina.langtechmoore.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TraductionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TraductionDTO.class);
        TraductionDTO traductionDTO1 = new TraductionDTO();
        traductionDTO1.setId(1L);
        TraductionDTO traductionDTO2 = new TraductionDTO();
        assertThat(traductionDTO1).isNotEqualTo(traductionDTO2);
        traductionDTO2.setId(traductionDTO1.getId());
        assertThat(traductionDTO1).isEqualTo(traductionDTO2);
        traductionDTO2.setId(2L);
        assertThat(traductionDTO1).isNotEqualTo(traductionDTO2);
        traductionDTO1.setId(null);
        assertThat(traductionDTO1).isNotEqualTo(traductionDTO2);
    }
}
