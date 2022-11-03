package bf.openburkina.langtechmoore.repository;

import bf.openburkina.langtechmoore.domain.Traduction;
import bf.openburkina.langtechmoore.domain.enumeration.Etat;
import bf.openburkina.langtechmoore.domain.enumeration.TypeTraduction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("select tr from Traduction tr where (" +
            "(:libelle is null or :libelle='' or tr.libelle like ('%'||:libelle||'%') )" +
            " and (:etat is null or :etat='' or tr.etat like ('%'||:etat||'%'))" +
            " and (:typeTraduction is null or :typeTraduction='' or tr.type like('%'||:typeTraduction||'%'))" +
            " and(:contenuAudioContentType is null or :contenuAudioContentType='' or tr.contenuAudioContentType like('%'||:contenuAudioContentType||'%'))" +
            " and(:sourceDonneeId is null or tr.sourceDonnee.id=:sourceDonneeId)" +
            " and(:utilisateurId is null or tr.utilisateur.id=:utilisateurId)" +
            " and(:langueId is null or tr.langue.id=:langueId))")
    Page<Traduction> findAllWithCriteria(
        @Param("libelle") String libelle,
        @Param("etat") Etat etat,
        @Param("typeTraduction") TypeTraduction typeTraduction,
        @Param("contenuAudioContentType") String contenuAudioContentType,
        @Param("sourceDonneeId") Long sourceDonneeId,
        @Param("utilisateurId") Long utilisateurId,
        @Param("langueId") Long langueId, Pageable pageable);
}
