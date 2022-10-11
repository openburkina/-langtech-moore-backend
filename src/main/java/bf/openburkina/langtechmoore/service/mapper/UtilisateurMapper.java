package bf.openburkina.langtechmoore.service.mapper;

import bf.openburkina.langtechmoore.domain.Profil;
import bf.openburkina.langtechmoore.domain.Utilisateur;
import bf.openburkina.langtechmoore.service.dto.ProfilDTO;
import bf.openburkina.langtechmoore.service.dto.UtilisateurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Utilisateur} and its DTO {@link UtilisateurDTO}.
 */
@Mapper(componentModel = "spring")
public interface UtilisateurMapper extends EntityMapper<UtilisateurDTO, Utilisateur> {
    @Mapping(target = "profil", source = "profil", qualifiedByName = "profilId")
    UtilisateurDTO toDto(Utilisateur s);

    @Named("profilId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProfilDTO toDtoProfilId(Profil profil);
}
