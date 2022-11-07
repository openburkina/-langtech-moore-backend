package bf.openburkina.langtechmoore.repository;

import bf.openburkina.langtechmoore.domain.Utilisateur;
import bf.openburkina.langtechmoore.domain.enumeration.TypeUtilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the Utilisateur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByUserId(Long userId);

    @Query("select u from Utilisateur u where (" +
        "(:nom is null or :nom='' or u.nom like ('%'||:nom||'%') )" +
        " and (:prenom is null or :prenom='' or u.prenom like ('%'||:prenom||'%'))" +
        " and (:email is null or :email='' or u.email like('%'||:email||'%'))" +
        " and(:telephone is null or :telephone='' or u.telephone like('%'||:telephone||'%'))" +
        " and(:typeUtilisateur is null or u.typeUtilisateur=:typeUtilisateur))")
    Page<Utilisateur> findAllWithCriteria(
        @Param("nom") String nom,
        @Param("prenom") String prenom,
        @Param("email") String email,
        @Param("telephone") String telephone,
        @Param("typeUtilisateur") TypeUtilisateur typeUtilisateur, Pageable pageable);
}
