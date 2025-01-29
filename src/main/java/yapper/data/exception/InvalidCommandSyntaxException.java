package yapper.data.exception;

public class InvalidCommandSyntaxException extends Exception {

    public InvalidCommandSyntaxException(String message) {
        super(message);
    }

    public InvalidCommandSyntaxException(String message, Throwable cause) {
        super(message, cause);
    }
}
