package enzosdev.bjjtrack.exceptions;

public class UserAlreadyDeactivatedException extends RuntimeException {
    public UserAlreadyDeactivatedException(String message) {
        super(message);
    }
}
