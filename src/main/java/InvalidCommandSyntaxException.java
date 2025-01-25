public class InvalidCommandSyntaxException extends Exception {
    
    public InvalidCommandSyntaxException(String message) {
        System.out.print(String.format("%s | ", message));
    }

    public InvalidCommandSyntaxException(String message, Throwable cause) {
        super(message, cause);
    }
}
