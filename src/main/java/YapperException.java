public class YapperException extends RuntimeException {
    
    public YapperException() {
        super("Test");
    }

    public YapperException(String message) {
        super(message);
    }

    public YapperException(String message, Throwable cause) {
        super(message, cause);
    }
}
