package bf.openburkina.langtechmoore.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Categorie entity.\n@author The LonkoTech team.
 */
@Entity
@Table(name = "categorie")
public class Categorie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "categorie")
    @JsonIgnoreProperties(value = { "traductions", "categorie", "utilisateur" }, allowSetters = true)
    private Set<SourceDonnee> sourceDonnees = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Categorie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public Categorie libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return this.description;
    }

    public Categorie description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<SourceDonnee> getSourceDonnees() {
        return this.sourceDonnees;
    }

    public void setSourceDonnees(Set<SourceDonnee> sourceDonnees) {
        if (this.sourceDonnees != null) {
            this.sourceDonnees.forEach(i -> i.setCategorie(null));
        }
        if (sourceDonnees != null) {
            sourceDonnees.forEach(i -> i.setCategorie(this));
        }
        this.sourceDonnees = sourceDonnees;
    }

    public Categorie sourceDonnees(Set<SourceDonnee> sourceDonnees) {
        this.setSourceDonnees(sourceDonnees);
        return this;
    }

    public Categorie addSourceDonnee(SourceDonnee sourceDonnee) {
        this.sourceDonnees.add(sourceDonnee);
        sourceDonnee.setCategorie(this);
        return this;
    }

    public Categorie removeSourceDonnee(SourceDonnee sourceDonnee) {
        this.sourceDonnees.remove(sourceDonnee);
        sourceDonnee.setCategorie(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Categorie)) {
            return false;
        }
        return id != null && id.equals(((Categorie) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Categorie{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
