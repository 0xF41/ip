import java.util.ArrayList;

/**
 * CommandParser parses the commands entered by the user into the chatbot. 
 */
public class CommandParser {

    /**
     * Prints out all Tasks of the user
     */
    private static void list(ArrayList<Task> taskList) {
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
    private static void mark(String cmd, ArrayList<Task> taskList) throws InvalidCommandSyntaxException, ArrayIndexOutOfBoundsException {
        int idx = -1;
        try {
            idx = Integer.parseInt(cmd.split(" ")[1]) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidCommandSyntaxException(String.format("%s is not a valid item.", cmd.split(" ")[1]));
        } catch (ArrayIndexOutOfBoundsException e) {
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
    private static void unmark(String cmd, ArrayList<Task> taskList) throws InvalidCommandSyntaxException, ArrayIndexOutOfBoundsException {
        int idx = -1;
        try {
            idx = Integer.parseInt(cmd.split(" ")[1]) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidCommandSyntaxException(String.format("%s is not a valid item.", cmd.split(" ")[1]));
        } catch (ArrayIndexOutOfBoundsException e) {
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

    private static void delete(String cmd, ArrayList<Task> taskList) throws InvalidCommandSyntaxException, ArrayIndexOutOfBoundsException {
        int idx = -1;
        try {
            idx = Integer.parseInt(cmd.split(" ")[1]) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidCommandSyntaxException(String.format("%s is not a valid item.", cmd.split(" ")[1]));
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidCommandSyntaxException(String.format("%s is not a valid item.", cmd.split(" ")[1]));
        }
        System.out.println("____________________________________________________________");
        System.out.println("Noted. I've removed this task: ");
        System.out.println(taskList.get(idx));
        System.out.println(String.format("Now you have %d tasks in the list.", taskList.size()));
        System.out.println("____________________________________________________________");

        taskList.remove(idx);
    }

    private static void unknown() throws InvalidCommandSyntaxException {
        throw new InvalidCommandSyntaxException("Unknown command.");
    }

    public static void processCommand(String command, ArrayList<Task> taskList) throws InvalidCommandSyntaxException {

        if (command.split(" ").length < 2) {
            throw new InvalidCommandSyntaxException("Invalid command syntax. Expected: 'Command argument'");
        }

        String[] tokens = command.split(" ");
        String cmd = tokens[0];

        if (cmd.equals("list")) { // list tasks
            list(taskList);
        } else if (cmd.split(" ")[0].equals("mark")) { // mark X
            mark(cmd, taskList);
        } else if (cmd.split(" ")[0].equals("unmark")) { // unmark X
            unmark(cmd, taskList);
        } else if (cmd.split(" ")[0].equals("todo")) { // todo X
            todo(cmd, taskList);
        } else if (cmd.split(" ")[0].equals("deadline")) { // deadline X /by Y
            deadline(cmd, taskList);
        } else if (cmd.split(" ")[0].equals("event")) { // event X /from Y /to Z
            event(cmd, taskList);
        } else if (cmd.split(" ")[0].equals("delete")) { // delete X
            delete(cmd, taskList);
        } else {
            unknown();
        }

    }

    public CommandParser() {

    }

}

