package bf.openburkina.langtechmoore.service.mapper;

import bf.openburkina.langtechmoore.domain.Profil;
import bf.openburkina.langtechmoore.service.dto.ProfilDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Profil} and its DTO {@link ProfilDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProfilMapper extends EntityMapper<ProfilDTO, Profil> {}
