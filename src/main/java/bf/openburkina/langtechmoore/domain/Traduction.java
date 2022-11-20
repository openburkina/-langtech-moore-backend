package bf.openburkina.langtechmoore.domain;

import bf.openburkina.langtechmoore.domain.enumeration.Etat;
import bf.openburkina.langtechmoore.domain.enumeration.TypeTraduction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Traduction entity.\n@author The LonkoTech team.
 */
@Entity
@Table(name = "traduction")
public class Traduction extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @Column(name = "contenu_texte")
    private String contenuTexte;

    @Lob
    @Column(name = "contenu_audio")
    private byte[] contenuAudio;

    @Column(name = "contenu_audio_content_type")
    private String contenuAudioContentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TypeTraduction type;

    @Column(name = "note")
    private Integer note;

    @Column(name = "chemin_document")
    private String cheminDocument;

    /**
     * A enlever
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "etat")
    private Etat etat;

    @ManyToOne
    @JsonIgnoreProperties(value = { "sourceDonnees", "traductions", "profil" }, allowSetters = true)
    private Utilisateur utilisateur;

    @ManyToOne
    @JsonIgnoreProperties(value = { "traductions", "categorie", "utilisateur" }, allowSetters = true)
    private SourceDonnee sourceDonnee;

    @ManyToOne
    @JsonIgnoreProperties(value = { "traductions", "categorie", "utilisateur" }, allowSetters = true)
    private Langue langue;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Traduction id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public Traduction libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getContenuTexte() {
        return this.contenuTexte;
    }

    public Traduction contenuTexte(String contenuTexte) {
        this.setContenuTexte(contenuTexte);
        return this;
    }

    public void setContenuTexte(String contenuTexte) {
        this.contenuTexte = contenuTexte;
    }

    public byte[] getContenuAudio() {
        return this.contenuAudio;
    }

    public Traduction contenuAudio(byte[] contenuAudio) {
        this.setContenuAudio(contenuAudio);
        return this;
    }

    public void setContenuAudio(byte[] contenuAudio) {
        this.contenuAudio = contenuAudio;
    }

    public String getContenuAudioContentType() {
        return this.contenuAudioContentType;
    }

    public Traduction contenuAudioContentType(String contenuAudioContentType) {
        this.contenuAudioContentType = contenuAudioContentType;
        return this;
    }

    public void setContenuAudioContentType(String contenuAudioContentType) {
        this.contenuAudioContentType = contenuAudioContentType;
    }

    public TypeTraduction getType() {
        return this.type;
    }

    public Traduction type(TypeTraduction type) {
        this.setType(type);
        return this;
    }

    public void setType(TypeTraduction type) {
        this.type = type;
    }

    public Integer getNote() {
        return this.note;
    }

    public String getCheminDocument() {
        return cheminDocument;
    }

    public void setCheminDocument(String cheminDocument) {
        this.cheminDocument = cheminDocument;
    }

    public Traduction note(Integer note) {
        this.setNote(note);
        return this;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public Etat getEtat() {
        return this.etat;
    }

    public Traduction etat(Etat etat) {
        this.setEtat(etat);
        return this;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public Utilisateur getUtilisateur() {
        return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Traduction utilisateur(Utilisateur utilisateur) {
        this.setUtilisateur(utilisateur);
        return this;
    }

    public SourceDonnee getSourceDonnee() {
        return this.sourceDonnee;
    }

    public void setSourceDonnee(SourceDonnee sourceDonnee) {
        this.sourceDonnee = sourceDonnee;
    }

    public Traduction sourceDonnee(SourceDonnee sourceDonnee) {
        this.setSourceDonnee(sourceDonnee);
        return this;
    }

    public Langue getLangue() {
        return langue;
    }

    public void setLangue(Langue langue) {
        this.langue = langue;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Traduction)) {
            return false;
        }
        return id != null && id.equals(((Traduction) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Traduction{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", contenuTexte='" + getContenuTexte() + "'" +
            ", contenuAudio='" + getContenuAudio() + "'" +
            ", contenuAudioContentType='" + getContenuAudioContentType() + "'" +
            ", type='" + getType() + "'" +
            ", note=" + getNote() +
            ", etat='" + getEtat() + "'" +
            "}";
    }
}
