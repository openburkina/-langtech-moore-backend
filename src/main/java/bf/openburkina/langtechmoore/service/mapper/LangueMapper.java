package bf.openburkina.langtechmoore.service.mapper;

import bf.openburkina.langtechmoore.domain.Langue;
import bf.openburkina.langtechmoore.service.dto.LangueDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Langue} and its DTO {@link LangueDTO}.
 */
@Mapper(componentModel = "spring")
public interface LangueMapper extends EntityMapper<LangueDTO, Langue> {}
