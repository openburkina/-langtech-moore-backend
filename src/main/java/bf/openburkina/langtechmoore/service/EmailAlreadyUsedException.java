package bf.openburkina.langtechmoore.service;

public class EmailAlreadyUsedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EmailAlreadyUsedException() {
        super("Cet email est déjà utilisé !");
    }
}
