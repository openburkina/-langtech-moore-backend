package bf.openburkina.langtechmoore.service.mapper;

import bf.openburkina.langtechmoore.domain.Categorie;
import bf.openburkina.langtechmoore.service.dto.CategorieDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Categorie} and its DTO {@link CategorieDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategorieMapper extends EntityMapper<CategorieDTO, Categorie> {}
