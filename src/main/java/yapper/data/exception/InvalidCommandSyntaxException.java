package yapper.data.exception;

/**
 * Represents an exception thrown when the command syntax is invalid.
 */
public class InvalidCommandSyntaxException extends Exception {

    /**
     * Constructs an InvalidCommandSyntaxException object.
     *
     * @param message The message to be displayed.
     */
    public InvalidCommandSyntaxException(String message) {
        super(message);
    }

    /**
     * Constructs an InvalidCommandSyntaxException object.
     *
     * @param message The message to be displayed.
     * @param cause   The cause of the exception.
     */
    public InvalidCommandSyntaxException(String message, Throwable cause) {
        super(message, cause);
    }
}
