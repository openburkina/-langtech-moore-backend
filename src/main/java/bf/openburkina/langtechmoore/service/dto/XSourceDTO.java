package bf.openburkina.langtechmoore.service.dto;

import java.time.ZonedDateTime;

public class XSourceDTO {
    public String typeTraduction;
    public String etat;
    public ZonedDateTime debut;
    public ZonedDateTime fin;

    public String getTypeTraduction() {
        return typeTraduction;
    }

    public void setTypeTraduction(String typeTraduction) {
        this.typeTraduction = typeTraduction;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public ZonedDateTime getDebut() {
        return debut;
    }

    public void setDebut(ZonedDateTime debut) {
        this.debut = debut;
    }

    public ZonedDateTime getFin() {
        return fin;
    }

    public void setFin(ZonedDateTime fin) {
        this.fin = fin;
    }
}
