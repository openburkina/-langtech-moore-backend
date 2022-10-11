package bf.openburkina.langtechmoore.repository;

import bf.openburkina.langtechmoore.domain.Profil;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Profil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfilRepository extends JpaRepository<Profil, Long> {}
