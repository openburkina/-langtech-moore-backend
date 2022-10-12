package bf.openburkina.langtechmoore.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link bf.openburkina.langtechmoore.domain.SourceDonnee} entity.
 */
@Schema(description = "SourceDonnee entity.\n@author The LonkoTech team.")
public class SourceDonneeDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelle;

    private CategorieDTO categorie;

    private Long categorieId;

    private UtilisateurDTO utilisateur;

    private Long utilisateurId;

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

    public CategorieDTO getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieDTO categorie) {
        this.categorie = categorie;
    }

    public UtilisateurDTO getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(UtilisateurDTO utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Long getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(Long categorieId) {
        this.categorieId = categorieId;
    }

    public Long getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Long utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SourceDonneeDTO)) {
            return false;
        }

        SourceDonneeDTO sourceDonneeDTO = (SourceDonneeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sourceDonneeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SourceDonneeDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", categorie=" + getCategorie() +
            ", utilisateur=" + getUtilisateur() +
            "}";
    }
}
