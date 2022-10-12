package bf.openburkina.langtechmoore.service.mapper;

import bf.openburkina.langtechmoore.domain.SourceDonnee;
import bf.openburkina.langtechmoore.domain.Traduction;
import bf.openburkina.langtechmoore.domain.Utilisateur;
import bf.openburkina.langtechmoore.service.dto.SourceDonneeDTO;
import bf.openburkina.langtechmoore.service.dto.TraductionDTO;
import bf.openburkina.langtechmoore.service.dto.UtilisateurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Traduction} and its DTO {@link TraductionDTO}.
 */
@Mapper(componentModel = "spring")
public interface TraductionMapper extends EntityMapper<TraductionDTO, Traduction> {
    @Mapping(target = "utilisateur", source = "utilisateur", qualifiedByName = "utilisateurId")
    @Mapping(target = "sourceDonnee", source = "sourceDonnee", qualifiedByName = "sourceDonneeId")
    TraductionDTO toDto(Traduction s);

    @Named("utilisateurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UtilisateurDTO toDtoUtilisateurId(Utilisateur utilisateur);

    @Named("sourceDonneeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SourceDonneeDTO toDtoSourceDonneeId(SourceDonnee sourceDonnee);
}
