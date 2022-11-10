package bf.openburkina.langtechmoore.service.mapper;

import bf.openburkina.langtechmoore.domain.Langue;
import bf.openburkina.langtechmoore.domain.SourceDonnee;
import bf.openburkina.langtechmoore.domain.Traduction;
import bf.openburkina.langtechmoore.domain.Utilisateur;
import bf.openburkina.langtechmoore.service.dto.LangueDTO;
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
    @Mapping(target = "langue", source = "langue", qualifiedByName = "langueId")
    TraductionDTO toDto(Traduction s);

    @Named("utilisateurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    @Mapping(target = "prenom", source = "prenom")
    UtilisateurDTO toDtoUtilisateurId(Utilisateur utilisateur);

    @Named("sourceDonneeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "libelle", source = "libelle")
    SourceDonneeDTO toDtoSourceDonneeId(SourceDonnee sourceDonnee);

    @Named("langueId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "libelle", source = "libelle")
    LangueDTO toDtoLangueId(Langue langue);
}
