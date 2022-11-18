package bf.openburkina.langtechmoore.service.dto;

import bf.openburkina.langtechmoore.domain.Authority;
import bf.openburkina.langtechmoore.domain.Profil;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link bf.openburkina.langtechmoore.domain.Profil} entity.
 */
@Schema(description = "Profil entity.\n@author The LonkoTech team.")
public class ProfilDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelle;

    private String description;

    private Set<String> authorities;

    private Boolean profilsChange;

    public ProfilDTO() {}

    public ProfilDTO(Profil profil) {
        this(
            profil.getId(),
            profil.getLibelle(),
            profil.getDescription(),
            profil.getRoles().stream().map(Authority::getName).collect(Collectors.toSet())
        );
    }

    public ProfilDTO(Long id, @NotNull String libelle, String description, Set<String> authorities) {
        this.id = id;
        this.libelle = libelle;
        this.description = description;
        this.authorities = authorities;
    }

    public Boolean getProfilsChange() {
        return profilsChange;
    }

    public void setProfilsChange(Boolean profilsChange) {
        this.profilsChange = profilsChange;
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

    public String getDescription() {
        return description;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProfilDTO)) {
            return false;
        }

        ProfilDTO profilDTO = (ProfilDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, profilDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "ProfilDTO{" +
            "id=" + id +
            ", libelle='" + libelle + '\'' +
            ", description='" + description + '\'' +
            ", authorities=" + authorities +
            ", profilsChange=" + profilsChange +
            '}';
    }
}
