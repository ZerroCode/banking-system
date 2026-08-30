package exceptions;

public class InvalidMenuSelectionException extends RuntimeException {
    public InvalidMenuSelectionException(String message) {
        super(message);
    }
}
