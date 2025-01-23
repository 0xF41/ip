import java.util.ArrayList;
import java.util.Arrays;

public class CommandValidator {

    public enum commands {
        BYE("bye"),
        LIST("list"),
        MARK("mark"),
        UNMARK("unmark"),
        TODO("todo"),
        DEADLINE("deadline"),
        EVENT("event");
    };

    private static final ArrayList<String> VALID_COMMANDS = new ArrayList<>();
}
