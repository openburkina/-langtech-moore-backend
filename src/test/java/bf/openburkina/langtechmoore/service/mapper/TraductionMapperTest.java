package bf.openburkina.langtechmoore.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TraductionMapperTest {

    private TraductionMapper traductionMapper;

    @BeforeEach
    public void setUp() {
        traductionMapper = new TraductionMapperImpl();
    }
}
