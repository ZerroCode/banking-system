package exceptions;

public class PostDatedCheckException extends RuntimeException {
    public PostDatedCheckException(String message) {
        super(message);
    }
}
