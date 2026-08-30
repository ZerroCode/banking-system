package exceptions;

public class CheckTooOldException extends RuntimeException {
    public CheckTooOldException(String message) {
        super(message);
    }
}
