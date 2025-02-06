package yapper.commands;

import java.util.ArrayList;

import yapper.task.Task;

/**
 * Represents a command to list all tasks.
 */
public class ListCommand implements Command {

    /**
     * List of a Person's current tasks.
     */
    private ArrayList<Task> taskList;

    /**
     * Constructs a ListCommand object.
     *
     * @param taskList List of a Person's current tasks.
     */
    private ListCommand(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    /**
     * Executes the command to list all tasks.
     *
     * @param responseList List of responses to be displayed to the user.
     * @return True if the command is successfully executed, false otherwise.
     */
    @Override
    public boolean execute(ArrayList<String> responseList) {
        if (this.taskList.isEmpty()) {
            responseList.add("List is empty!");
            return true;
        }
        for (int i = 0, n = taskList.size(); i < n; i++) {
            Task t = taskList.get(i);
            responseList.add(String.format("%d. %s", i + 1, t));
        }
        return true;
    }

    /**
     * Builds a ListCommand object.
     *
     * @param taskList List of a Person's current tasks.
     * @return ListCommand object.
     */
    public static Command buildListCommand(ArrayList<Task> taskList) {
        return new ListCommand(taskList);
    }
}
