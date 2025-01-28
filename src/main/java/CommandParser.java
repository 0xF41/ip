import java.io.File;
import java.util.ArrayList;

/**
 * CommandParser parses the commands entered by the user into the chatbot.
 */
public class CommandParser {

    /**
     * Chatbot commands
     */
    public enum Command {
        LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, BYE, HELP;

        // Check for valid commands
        public static Command fromString(String command) throws InvalidCommandSyntaxException {
            try {
                return Command.valueOf(command.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new InvalidCommandSyntaxException("See usage with \"help\"");
            }
        }
    }

    /**
     * Prints out all Tasks of the user
     */
    private static void list(String cmd, ArrayList<Task> taskList) throws InvalidCommandSyntaxException {
        if (cmd.split(" ").length != 1) {
            throw new InvalidCommandSyntaxException("See usage with \"help\"");
        }

        if (taskList.isEmpty()) {
            System.out.println("List is empty!");
            return;
        }
        for (int i = 0, n = taskList.size(); i < n; i++) {
            Task t = taskList.get(i);
            System.out.println(String.format("%d. %s", i + 1, t));
        }
    }

    /**
     * Mark a specified task as completed.
     * 
     * @param cmd user command
     */
    private static void mark(String cmd, ArrayList<Task> taskList)
            throws InvalidCommandSyntaxException, IndexOutOfBoundsException {
        if (cmd.split(" ").length != 2) {
            throw new InvalidCommandSyntaxException("Usage: mark <task-index>");
        }
        int idx = -1;
        try {
            idx = Integer.parseInt(cmd.split(" ")[1]) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidCommandSyntaxException("See usage with \"help\"");
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommandSyntaxException("See usage with \"help\"");
        }
        Task t = taskList.get(idx);
        t.markAsDone();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(t);
    }

    /**
     * Unmark a specified task as incomplete.
     * 
     * @param cmd User command
     */
    private static void unmark(String cmd, ArrayList<Task> taskList)
            throws InvalidCommandSyntaxException, IndexOutOfBoundsException {
        if (cmd.split(" ").length != 2) {
            throw new InvalidCommandSyntaxException("See usage with \"help\"");
        }
        int idx = -1;
        try {
            idx = Integer.parseInt(cmd.split(" ")[1]) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidCommandSyntaxException("See usage with \"help\"");
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommandSyntaxException("See usage with \"help\"");
        }
        Task t = taskList.get(idx);
        t.markAsUndone();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(t);
    }

    /**
     * Creates a new ToDos Task in the user's list of task.
     */
    private static void todo(String cmd, ArrayList<Task> taskList) throws InvalidCommandSyntaxException {
        ToDos td = new ToDos(cmd.substring("todo".length() + 1));
        taskList.add(td);
        System.out.println("Got it. I've added this task:");
        System.out.println(td);
        System.out.println(String.format("Now you have %d tasks in the list.", taskList.size()));
    }

    /**
     * Creates a Deadline Task in the user's list of task
     * 
     * @param cmd User command
     */
    private static void deadline(String cmd, ArrayList<Task> taskList) throws InvalidCommandSyntaxException {
        int deadlineIndex = cmd.indexOf("deadline") + 9;
        int byIndex = cmd.indexOf("/by");

        String description = cmd.substring(deadlineIndex, byIndex).trim();
        String dueDate = cmd.substring(byIndex + 4).trim();

        Deadline dl = new Deadline(description, dueDate);
        taskList.add(dl);
        System.out.println("Got it. I've added this task:");
        System.out.println(dl);
        System.out.println(String.format("Now you have %d tasks in the list.", taskList.size()));
    }

    /**
     * Create an Event Task in the user's list of tasks
     * 
     * @param cmd User command
     */
    private static void event(String cmd, ArrayList<Task> taskList) throws InvalidCommandSyntaxException {
        int eventIndex = cmd.indexOf("event") + 6; // Start after "event "
        int fromIndex = cmd.indexOf("/from");
        int toIndex = cmd.indexOf("/to");

        String description = cmd.substring(eventIndex, fromIndex).trim();
        String fromTime = cmd.substring(fromIndex + 6, toIndex).trim();
        String toTime = cmd.substring(toIndex + 4).trim();

        Events e = new Events(description, fromTime, toTime);
        taskList.add(e);

        System.out.println("Got it. I've added this task:");
        System.out.println(e);
        System.out.println(String.format("Now you have %d tasks in the list.", taskList.size()));
    }

    /**
     * Deletes a Task from the user's task list
     * 
     * @param cmd      User command
     * @param taskList User's task list
     * @throws InvalidCommandSyntaxException
     * @throws IndexOutOfBoundsException
     */
    private static void delete(String cmd, ArrayList<Task> taskList)
            throws InvalidCommandSyntaxException, IndexOutOfBoundsException {
        if (cmd.split(" ").length != 2) {
            throw new InvalidCommandSyntaxException("Type \"help\" for command list");
        }
        int idx = -1;
        try {
            idx = Integer.parseInt(cmd.split(" ")[1]) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidCommandSyntaxException("See usage with \"help\"");
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommandSyntaxException("See usage with \"help\"");
        }
        System.out.println("Noted. I've removed this task: ");
        System.out.println(taskList.get(idx));
        taskList.remove(idx);
        System.out.println(String.format("Now you have %d tasks in the list.", taskList.size()));

    }

    private static void bye(String fullCmd, ArrayList<Task> taskList, File file) throws InvalidCommandSyntaxException {
        if (fullCmd.split(" ").length != 1) {
            throw new InvalidCommandSyntaxException("Type \"help\" for command list");
        }
        FileManager.saveFileContents(file, taskList);
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Return a String of the usage of the chatbot
     * 
     * @return help usage string
     */
    private static String help() {
        String menu = """
        __   __                           
        \\ \\ / /_ _ _ __  _ __   ___ _ __ 
        \\ V / _` | '_ \\| '_ \\ / _ \\ '__|
            | | (_| | |_) | |_) |  __/ |   
            |_|\\__,_| .__/| .__/ \\___|_|   
                    |_|   |_|              

        Usage:
        list                  - Show current task list
        mark <task_number>    - Mark task with <task_number> as done
        unmark <task_number>  - Unmark task with <task_number> as incomplete
        todo <task_name>      - Create a new task specified with <task_name>
        deadline <task_name> /by <deadline> 
                                - Create a new task with a deadline, specifying <deadline> as the task deadline
        event <task_name> /from <start_time> /to <end_time> 
                                - Create a new Event task with a <start_time> and <end_time>
        delete <task_number>  - Delete task with <task_number> on the list
        bye                   - End the conversation with the chatbot
        help                  - Show this help menu
        """;
        return menu;
    }

    /**
     * Processes the command entered by the user into the chatbot.
     * 
     * @param fullCmd  The full input command
     * @param taskList The user's task list
     * @return true if more follow-up commands are to be processed. false if user
     *         exits chatbot
     * @throws InvalidCommandSyntaxException
     * @throws IndexOutOfBoundsException
     */
    public static boolean processCommand(String fullCmd, ArrayList<Task> taskList, File file)
            throws InvalidCommandSyntaxException, IndexOutOfBoundsException {
        String cmd = fullCmd.split(" ")[0];
        if (Command.fromString(cmd).equals(Command.LIST)) { // list tasks
            list(fullCmd, taskList);
        } else if (Command.fromString(cmd).equals(Command.MARK)) { // mark X
            mark(fullCmd, taskList);
        } else if (Command.fromString(cmd).equals(Command.UNMARK)) { // unmark X
            unmark(fullCmd, taskList);
        } else if (Command.fromString(cmd).equals(Command.TODO)) { // todo X
            todo(fullCmd, taskList);
        } else if (Command.fromString(cmd).equals(Command.DEADLINE)) { // deadline X /by Y
            deadline(fullCmd, taskList);
        } else if (Command.fromString(cmd).equals(Command.EVENT)) { // event X /from Y /to Z
            event(fullCmd, taskList);
        } else if (Command.fromString(cmd).equals(Command.DELETE)) { // delete X
            delete(fullCmd, taskList);
        } else if (Command.fromString(cmd).equals(Command.BYE)) { // bye
            bye(fullCmd, taskList, file);
            return false;
        } else if (Command.fromString(cmd).equals(Command.HELP)) { // help
            System.out.println(help());
        }
        return true;
    }

}
