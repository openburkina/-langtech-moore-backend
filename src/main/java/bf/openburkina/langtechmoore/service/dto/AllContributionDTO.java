package bf.openburkina.langtechmoore.service.dto;

import java.util.List;

public class AllContributionDTO {
    public String utilisateur;
    public String typeTraduction;
    public Integer pointFedelite;

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Integer getPointFedelite() {
        return pointFedelite;
    }

    public void setPointFedelite(Integer pointFedelite) {
        this.pointFedelite = pointFedelite;
    }

    public String getTypeTraduction() {
        return typeTraduction;
    }

    public void setTypeTraduction(String typeTraduction) {
        this.typeTraduction = typeTraduction;
    }
}
