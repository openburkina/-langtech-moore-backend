package bf.openburkina.langtechmoore.service.dto;

import java.time.Instant;

public class DateDTO {
    private Instant debut;
    private Instant fin;

    public Instant getDebut() {
        return debut;
    }

    public void setDebut(Instant debut) {
        this.debut = debut;
    }

    public Instant getFin() {
        return fin;
    }

    public void setFin(Instant fin) {
        this.fin = fin;
    }
}
