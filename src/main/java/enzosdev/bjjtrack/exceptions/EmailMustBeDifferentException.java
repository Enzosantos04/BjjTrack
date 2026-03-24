package enzosdev.bjjtrack.exceptions;

public class EmailMustBeDifferentException extends RuntimeException {
    public EmailMustBeDifferentException(String message) {
        super(message);
    }
}
