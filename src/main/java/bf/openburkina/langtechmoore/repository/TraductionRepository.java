package bf.openburkina.langtechmoore.repository;

import bf.openburkina.langtechmoore.domain.Traduction;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Traduction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TraductionRepository extends JpaRepository<Traduction, Long> {

    @Query("select t from Traduction t where t.id=:traductionId ")
    Traduction findByTraductionId(@Param("traductionId") Long traductionId);
}
