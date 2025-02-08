package yapper.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Represents the user interface of the chatbot.
 */
public class Ui {

    private static final String HELP_MENU_STRING = """
              __   __
              \\ \\ / /_ _ _ __  _ __   ___ _ __
               \\ V / _` | '_ \\| '_ \\ / _ \\ '__|
                | | (_| | |_) | |_) |  __/ |
                |_|\\__,_| .__/| .__/ \\___|_|
                        |_|   |_|

            Usage:
              list                  - Show current task list
              find <search_term>    - Find tasks with <search_term>
              mark <task_number>    - Mark task with <task_number> as done
              unmark <task_number>  - Unmark task with <task_number> as incomplete
              todo <task_name>      - Create a new task specified with <task_name>
                                      deadline <task_name> /by <deadline>
                                    - Create a new Deadline task with a <deadline> with <dd-MM-yyyy HHmm> format
              event <task_name> /from <start_time> /to <end_time>
                                    - Create a new Event task with a <start_time> and <end_time> with
              <dd-MM-yyyy HHmm> format
              delete <task_number>  - Delete task with <task_number> on the list
              bye                   - End the conversation with the chatbot
              help                  - Show this help menu
            """;
    private static final String HELLO_SECOND_LINE_STRING = "What can I do for you?";
    private static final String HELLO_FORMAT_STRING = "Hello! I'm %s!\n";
    private static final String BYE_STRING = "Bye. Hope to see you again soon!";
    /**
     * BufferedReader to read user input.
     */
    private static BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in));;

    /**
     * Reads the user's command.
     *
     * @return User's command.
     * @throws IOException If an I/O error occurs.
     */
    public static String readCommand() throws IOException {
        return Ui.br.readLine();
    }


    /**
     * Return the string representation of an object.
     *
     * @param obj Object to be converted to string.
     */
    public static String toString(Object obj) {
        return obj.toString();
    }

    /**
     * Return the string representation of the bye message.
     */
    public static String printBye() {
        return BYE_STRING;
    }

    /**
     * Return the string representation of the error message.
     *
     * @param e Error message.
     */
    public static String toErrorString(Object e) {
        return e.toString();
    }

    /**
     * Return the string representation of greeting message.
     *
     * @param botName Name of the chatbot.
     */
    public static String printGreet(String botName) {
        String str = "";
        str += String.format(HELLO_FORMAT_STRING, botName);
        str += HELLO_SECOND_LINE_STRING;
        return str;
    }

    /**
     * Return the string representation of the help menu.
     */
    public static String printMenu() {
        String menu = HELP_MENU_STRING;
        return menu;
    }

}
