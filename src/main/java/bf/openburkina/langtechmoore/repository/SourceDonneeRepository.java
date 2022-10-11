package bf.openburkina.langtechmoore.repository;

import bf.openburkina.langtechmoore.domain.SourceDonnee;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SourceDonnee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SourceDonneeRepository extends JpaRepository<SourceDonnee, Long> {}
