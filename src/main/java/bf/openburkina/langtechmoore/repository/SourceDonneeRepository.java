package bf.openburkina.langtechmoore.repository;

import bf.openburkina.langtechmoore.domain.SourceDonnee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SourceDonnee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SourceDonneeRepository extends JpaRepository<SourceDonnee, Long> {
    @Query("select s from SourceDonnee s where :libelle is null or upper(s.libelle) like upper('%'||:libelle||'%') ")
    Page<SourceDonnee>findAllWithCriteria(@Param("libelle") String libelle, Pageable pageable);
}
