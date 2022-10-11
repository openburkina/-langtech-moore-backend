package bf.openburkina.langtechmoore.service.mapper;

import bf.openburkina.langtechmoore.domain.Categorie;
import bf.openburkina.langtechmoore.domain.SourceDonnee;
import bf.openburkina.langtechmoore.domain.Utilisateur;
import bf.openburkina.langtechmoore.service.dto.CategorieDTO;
import bf.openburkina.langtechmoore.service.dto.SourceDonneeDTO;
import bf.openburkina.langtechmoore.service.dto.UtilisateurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SourceDonnee} and its DTO {@link SourceDonneeDTO}.
 */

@Mapper(componentModel = "spring", uses = {CategorieMapper.class, UtilisateurMapper.class})
public interface SourceDonneeMapper extends EntityMapper<SourceDonneeDTO, SourceDonnee> {
    @Mapping(target = "categorie", source = "categorie", qualifiedByName = "categorieId")
    @Mapping(target = "utilisateur", source = "utilisateur", qualifiedByName = "utilisateurId")
    SourceDonneeDTO toDto(SourceDonnee s);

    @Mapping(source = "categorieId", target = "categorie.id")
    @Mapping(source = "utilisateurId", target = "utilisateur.id")
    SourceDonnee toEntity(SourceDonneeDTO s);

    @Named("categorieId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategorieDTO toDtoCategorieId(Categorie categorie);

    @Named("utilisateurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UtilisateurDTO toDtoUtilisateurId(Utilisateur utilisateur);
}
