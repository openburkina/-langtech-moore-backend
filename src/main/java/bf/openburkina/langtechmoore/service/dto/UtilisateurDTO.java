package bf.openburkina.langtechmoore.service.dto;

import bf.openburkina.langtechmoore.domain.enumeration.TypeUtilisateur;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link bf.openburkina.langtechmoore.domain.Utilisateur} entity.
 */
@Schema(description = "Utilisateur entity.\n@author The LonkoTech team.")
public class UtilisateurDTO implements Serializable {

    private Long id;

    private String nom;

    private String prenom;

    private String telephone;

    private String email;

    private String login;

    private String password;


    private TypeUtilisateur typeUtilisateur;

    private Integer pointFidelite;

    private ProfilDTO profil;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TypeUtilisateur getTypeUtilisateur() {
        return typeUtilisateur;
    }

    public void setTypeUtilisateur(TypeUtilisateur typeUtilisateur) {
        this.typeUtilisateur = typeUtilisateur;
    }

    public Integer getPointFidelite() {
        return pointFidelite;
    }

    public void setPointFidelite(Integer pointFidelite) {
        this.pointFidelite = pointFidelite;
    }

    public ProfilDTO getProfil() {
        return profil;
    }

    public void setProfil(ProfilDTO profil) {
        this.profil = profil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UtilisateurDTO)) {
            return false;
        }

        UtilisateurDTO utilisateurDTO = (UtilisateurDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, utilisateurDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UtilisateurDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", email='" + getEmail() + "'" +
            ", login='" + getLogin() + "'" +
            ", password='" + getPassword() + "'" +
            ", typeUtilisateur='" + getTypeUtilisateur() + "'" +
            ", pointFidelite=" + getPointFidelite() +
            ", profil=" + getProfil() +
            "}";
    }
}
