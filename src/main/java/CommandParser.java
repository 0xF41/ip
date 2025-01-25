import java.util.ArrayList;

/**
 * CommandParser parses the commands entered by the user into the chatbot.
 */
public class CommandParser {

    /**
     * Chatbot commands
     */
    enum Command {
        LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE;

        // Check for valid commands
        public static Command fromString(String command) throws InvalidCommandSyntaxException {
            try {
                return Command.valueOf(command.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new InvalidCommandSyntaxException("Invalid command: " + command);
            }
        }
    }

    /**
     * Prints out all Tasks of the user
     */
    private static void list(String cmd, ArrayList<Task> taskList) throws InvalidCommandSyntaxException {
        if (cmd.split(" ").length != 1) {
            throw new InvalidCommandSyntaxException("Usage: list");
        }

        if (taskList.isEmpty()) {
            System.out.println("List is empty!");
            return;
        }
        System.out.println("____________________________________________________________");
        for (int i = 0, n = taskList.size(); i < n; i++) {
            Task t = taskList.get(i);
            System.out.println(String.format("%d. %s", i + 1, t));
        }
        System.out.println("____________________________________________________________");
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
            throw new InvalidCommandSyntaxException(String.format("%s is not a valid item.", cmd.split(" ")[1]));
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommandSyntaxException(String.format("%s is not a valid item.", cmd.split(" ")[1]));
        }
        Task t = taskList.get(idx);
        t.markAsDone();
        System.out.println("____________________________________________________________");
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(t);
        System.out.println("____________________________________________________________");
    }

    /**
     * Unmark a specified task as incomplete.
     * 
     * @param cmd User command
     */
    private static void unmark(String cmd, ArrayList<Task> taskList)
            throws InvalidCommandSyntaxException, IndexOutOfBoundsException {
        if (cmd.split(" ").length != 2) {
            throw new InvalidCommandSyntaxException("Usage: unmark <task-index>");
        }
        int idx = -1;
        try {
            idx = Integer.parseInt(cmd.split(" ")[1]) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidCommandSyntaxException(String.format("%s is not a valid item.", cmd.split(" ")[1]));
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommandSyntaxException(String.format("%s is not a valid item.", cmd.split(" ")[1]));
        }
        Task t = taskList.get(idx);
        t.markAsUndone();
        System.out.println("____________________________________________________________");
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(t);
        System.out.println("____________________________________________________________");
    }

    /**
     * Creates a new ToDos Task in the user's list of task.
     */
    private static void todo(String cmd, ArrayList<Task> taskList) throws InvalidCommandSyntaxException {
        ToDos td = new ToDos(cmd.substring("todo".length() + 1));
        taskList.add(td);
        System.out.println("____________________________________________________________");
        System.out.println("Got it. I've added this task:");
        System.out.println(td);
        System.out.println(String.format("Now you have %d tasks in the list.", taskList.size()));
        System.out.println("____________________________________________________________");
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
        System.out.println("____________________________________________________________");
        System.out.println("Got it. I've added this task:");
        System.out.println(dl);
        System.out.println(String.format("Now you have %d tasks in the list.", taskList.size()));
        System.out.println("____________________________________________________________");
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

        System.out.println("____________________________________________________________");
        System.out.println("Got it. I've added this task:");
        System.out.println(e);
        System.out.println(String.format("Now you have %d tasks in the list.", taskList.size() - 1));
        System.out.println("____________________________________________________________");
    }

    private static void delete(String cmd, ArrayList<Task> taskList)
            throws InvalidCommandSyntaxException, IndexOutOfBoundsException {
        if (cmd.split(" ").length != 2) {
            throw new InvalidCommandSyntaxException("Usage: delete <task-index>");
        }
        int idx = -1;
        try {
            idx = Integer.parseInt(cmd.split(" ")[1]) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidCommandSyntaxException(String.format("%s is not a valid item.", cmd.split(" ")[1]));
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommandSyntaxException(String.format("%s is not a valid item.", cmd.split(" ")[1]));
        }
        System.out.println("____________________________________________________________");
        System.out.println("Noted. I've removed this task: ");
        System.out.println(taskList.get(idx));
        taskList.remove(idx);
        System.out.println(String.format("Now you have %d tasks in the list.", taskList.size()));
        System.out.println("____________________________________________________________");

    }

    public static void processCommand(String fullCmd, ArrayList<Task> taskList)
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
        }

    }

}
