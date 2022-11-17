package bf.openburkina.langtechmoore.domain;

import bf.openburkina.langtechmoore.domain.enumeration.TypeUtilisateur;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Utilisateur entity.\n@author The LonkoTech team.
 */
@Entity
@Table(name = "utilisateur")
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nom", nullable = true)
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "email")
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_utilisateur", nullable = false)
    private TypeUtilisateur typeUtilisateur;

    @Column(name = "point_fidelite")
    private Integer pointFidelite;

    @OneToMany(mappedBy = "utilisateur")
    @JsonIgnoreProperties(value = { "traductions", "categorie", "utilisateur" }, allowSetters = true)
    private Set<SourceDonnee> sourceDonnees = new HashSet<>();

    @OneToMany(mappedBy = "utilisateur")
    @JsonIgnoreProperties(value = { "utilisateur", "sourceDonnee" }, allowSetters = true)
    private Set<Traduction> traductions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "utilisateurs" }, allowSetters = true)
    private Profil profil;

    @OneToOne
    @JsonIgnoreProperties(value = { "authorities" }, allowSetters = true)
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Utilisateur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Utilisateur nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Utilisateur prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Utilisateur telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return this.email;
    }

    public Utilisateur email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TypeUtilisateur getTypeUtilisateur() {
        return this.typeUtilisateur;
    }

    public Utilisateur typeUtilisateur(TypeUtilisateur typeUtilisateur) {
        this.setTypeUtilisateur(typeUtilisateur);
        return this;
    }

    public void setTypeUtilisateur(TypeUtilisateur typeUtilisateur) {
        this.typeUtilisateur = typeUtilisateur;
    }

    public Integer getPointFidelite() {
        return this.pointFidelite;
    }

    public Utilisateur pointFidelite(Integer pointFidelite) {
        this.setPointFidelite(pointFidelite);
        return this;
    }

    public void setPointFidelite(Integer pointFidelite) {
        this.pointFidelite = pointFidelite;
    }

    public Set<SourceDonnee> getSourceDonnees() {
        return this.sourceDonnees;
    }

    public void setSourceDonnees(Set<SourceDonnee> sourceDonnees) {
        if (this.sourceDonnees != null) {
            this.sourceDonnees.forEach(i -> i.setUtilisateur(null));
        }
        if (sourceDonnees != null) {
            sourceDonnees.forEach(i -> i.setUtilisateur(this));
        }
        this.sourceDonnees = sourceDonnees;
    }

    public Utilisateur sourceDonnees(Set<SourceDonnee> sourceDonnees) {
        this.setSourceDonnees(sourceDonnees);
        return this;
    }

    public Utilisateur addSourceDonnee(SourceDonnee sourceDonnee) {
        this.sourceDonnees.add(sourceDonnee);
        sourceDonnee.setUtilisateur(this);
        return this;
    }

    public Utilisateur removeSourceDonnee(SourceDonnee sourceDonnee) {
        this.sourceDonnees.remove(sourceDonnee);
        sourceDonnee.setUtilisateur(null);
        return this;
    }

    public Set<Traduction> getTraductions() {
        return this.traductions;
    }

    public void setTraductions(Set<Traduction> traductions) {
        if (this.traductions != null) {
            this.traductions.forEach(i -> i.setUtilisateur(null));
        }
        if (traductions != null) {
            traductions.forEach(i -> i.setUtilisateur(this));
        }
        this.traductions = traductions;
    }

    public Utilisateur traductions(Set<Traduction> traductions) {
        this.setTraductions(traductions);
        return this;
    }

    public Utilisateur addTraduction(Traduction traduction) {
        this.traductions.add(traduction);
        traduction.setUtilisateur(this);
        return this;
    }

    public Utilisateur removeTraduction(Traduction traduction) {
        this.traductions.remove(traduction);
        traduction.setUtilisateur(null);
        return this;
    }

    public Profil getProfil() {
        return this.profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public Utilisateur profil(Profil profil) {
        this.setProfil(profil);
        return this;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Utilisateur)) {
            return false;
        }
        return id != null && id.equals(((Utilisateur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", email='" + getEmail() + "'" +
            ", typeUtilisateur='" + getTypeUtilisateur() + "'" +
            ", pointFidelite=" + getPointFidelite() +
            "}";
    }
}
