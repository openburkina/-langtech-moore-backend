package bf.openburkina.langtechmoore.service.dto;

import bf.openburkina.langtechmoore.domain.Utilisateur;

import java.util.List;

public class BestContributorDTO {
    private List<Utilisateur> utilisateurs;
    private long pointFidelite;

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    public long getPointFidelite() {
        return pointFidelite;
    }

    public void setPointFidelite(long pointFidelite) {
        this.pointFidelite = pointFidelite;
    }

    @Override
    public String toString() {
        return "BestContributorDTO{" +
            "utilisateurs=" + utilisateurs +
            ", pointFidelite=" + pointFidelite +
            '}';
    }
}
