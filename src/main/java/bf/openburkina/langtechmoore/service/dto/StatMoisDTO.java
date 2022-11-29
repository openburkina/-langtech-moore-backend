package bf.openburkina.langtechmoore.service.dto;

import java.io.Serializable;

public class StatMoisDTO implements Serializable {

    public String mois;

    public int nombreContributionValide;

    public int nombreContributionEnattente;

    public int nombreContributionRejette;

    public String getMois() {
        return mois;
    }

    public void setMois(String mois) {
        this.mois = mois;
    }

    public int getNombreContributionValide() {
        return nombreContributionValide;
    }

    public void setNombreContributionValide(int nombreContributionValide) {
        this.nombreContributionValide = nombreContributionValide;
    }

    public int getNombreContributionEnattente() {
        return nombreContributionEnattente;
    }

    public void setNombreContributionEnattente(int nombreContributionEnattente) {
        this.nombreContributionEnattente = nombreContributionEnattente;
    }

    public int getNombreContributionRejette() {
        return nombreContributionRejette;
    }

    public void setNombreContributionRejette(int nombreContributionRejette) {
        this.nombreContributionRejette = nombreContributionRejette;
    }
}
