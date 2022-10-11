package bf.openburkina.langtechmoore.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * SourceDonnee entity.\n@author The LonkoTech team.
 */
@Entity
@Table(name = "source_donnee")
public class SourceDonnee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @OneToMany(mappedBy = "sourceDonnee")
    @JsonIgnoreProperties(value = { "utilisateur", "sourceDonnee" }, allowSetters = true)
    private Set<Traduction> traductions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "sourceDonnees" }, allowSetters = true)
    private Categorie categorie;

    @ManyToOne
    @JsonIgnoreProperties(value = { "sourceDonnees", "traductions", "profil" }, allowSetters = true)
    private Utilisateur utilisateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SourceDonnee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public SourceDonnee libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Traduction> getTraductions() {
        return this.traductions;
    }

    public void setTraductions(Set<Traduction> traductions) {
        if (this.traductions != null) {
            this.traductions.forEach(i -> i.setSourceDonnee(null));
        }
        if (traductions != null) {
            traductions.forEach(i -> i.setSourceDonnee(this));
        }
        this.traductions = traductions;
    }

    public SourceDonnee traductions(Set<Traduction> traductions) {
        this.setTraductions(traductions);
        return this;
    }

    public SourceDonnee addTraduction(Traduction traduction) {
        this.traductions.add(traduction);
        traduction.setSourceDonnee(this);
        return this;
    }

    public SourceDonnee removeTraduction(Traduction traduction) {
        this.traductions.remove(traduction);
        traduction.setSourceDonnee(null);
        return this;
    }

    public Categorie getCategorie() {
        return this.categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public SourceDonnee categorie(Categorie categorie) {
        this.setCategorie(categorie);
        return this;
    }

    public Utilisateur getUtilisateur() {
        return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public SourceDonnee utilisateur(Utilisateur utilisateur) {
        this.setUtilisateur(utilisateur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SourceDonnee)) {
            return false;
        }
        return id != null && id.equals(((SourceDonnee) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SourceDonnee{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
