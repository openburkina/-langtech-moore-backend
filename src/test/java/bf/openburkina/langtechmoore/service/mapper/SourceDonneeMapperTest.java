package bf.openburkina.langtechmoore.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SourceDonneeMapperTest {

    private SourceDonneeMapper sourceDonneeMapper;

    @BeforeEach
    public void setUp() {
        sourceDonneeMapper = new SourceDonneeMapperImpl();
    }
}
