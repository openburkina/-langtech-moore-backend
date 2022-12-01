package bf.openburkina.langtechmoore.service.dto;

import bf.openburkina.langtechmoore.domain.AbstractAuditingEntity;
import bf.openburkina.langtechmoore.domain.enumeration.Etat;
import bf.openburkina.langtechmoore.domain.enumeration.TypeTraduction;
import io.swagger.v3.oas.annotations.media.Schema;
import net.bytebuddy.implementation.bind.annotation.Super;

import java.io.Serializable;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link bf.openburkina.langtechmoore.domain.Traduction} entity.
 */
@Schema(description = "Traduction entity.\n@author The LonkoTech team.")
public class TraductionDTO extends AbstractAuditingEntity implements Serializable {

    private Long id;

    @NotNull
    private String libelle;

    private String contenuTexte;

    private String motif;

    @Lob
    private byte[] contenuAudio;

    private String contenuAudioContentType;

    @NotNull
    private TypeTraduction type;

    private Integer note;

    private String cheminDocument;

    /**
     * A enlever
     */
    @Schema(description = "A enlever")
    private Etat etat;

    private UtilisateurDTO utilisateur;

    private SourceDonneeDTO sourceDonnee;

    private LangueDTO langue;

    private String mois;

    public String getMois() {
        return mois;
    }

    public void setMois(String mois) {
        this.mois = mois;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public LangueDTO getLangue() {
        return langue;
    }

    public void setLangue(LangueDTO langue) {
        this.langue = langue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getContenuTexte() {
        return contenuTexte;
    }

    public void setContenuTexte(String contenuTexte) {
        this.contenuTexte = contenuTexte;
    }

    public byte[] getContenuAudio() {
        return contenuAudio;
    }

    public void setContenuAudio(byte[] contenuAudio) {
        this.contenuAudio = contenuAudio;
    }

    public String getContenuAudioContentType() {
        return contenuAudioContentType;
    }

    public void setContenuAudioContentType(String contenuAudioContentType) {
        this.contenuAudioContentType = contenuAudioContentType;
    }

    public TypeTraduction getType() {
        return type;
    }

    public void setType(TypeTraduction type) {
        this.type = type;
    }

    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public String getCheminDocument() {
        return cheminDocument;
    }

    public void setCheminDocument(String cheminDocument) {
        this.cheminDocument = cheminDocument;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public UtilisateurDTO getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(UtilisateurDTO utilisateur) {
        this.utilisateur = utilisateur;
    }

    public SourceDonneeDTO getSourceDonnee() {
        return sourceDonnee;
    }

    public void setSourceDonnee(SourceDonneeDTO sourceDonnee) {
        this.sourceDonnee = sourceDonnee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TraductionDTO)) {
            return false;
        }

        TraductionDTO traductionDTO = (TraductionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, traductionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "TraductionDTO{" +
            "id=" + id +
            ", libelle='" + libelle + '\'' +
            ", contenuTexte='" + contenuTexte + '\'' +
            ", contenuAudio=" + Arrays.toString(contenuAudio) +
            ", contenuAudioContentType='" + contenuAudioContentType + '\'' +
            ", type=" + type +
            ", note=" + note +
            ", cheminDocument='" + cheminDocument + '\'' +
            ", etat=" + etat +
            ", utilisateur=" + utilisateur +
            ", sourceDonnee=" + sourceDonnee +
            ", langue=" + langue +
            '}';
    }
}
