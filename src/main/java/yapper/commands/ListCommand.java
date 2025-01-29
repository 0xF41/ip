package yapper.commands;

import java.util.ArrayList;

import yapper.task.Task;
import yapper.ui.Ui;

/**
 * Represents a command to list all tasks.
 */
public class ListCommand implements Command {

    /**
     * List of a Person's current tasks.
     */
    private ArrayList<Task> taskList;

    /**
     * Executes the command to list all tasks.
     *
     * @return true to indicate the chatbot conversation should continue.
     */
    @Override
    public boolean execute() {
        if (this.taskList.isEmpty()) {
            Ui.printError("List is empty!");
            return true;
        }
        for (int i = 0, n = taskList.size(); i < n; i++) {
            Task t = taskList.get(i);
            Ui.print(String.format("%d. %s", i + 1, t));
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

    /**
     * Constructs a ListCommand object.
     *
     * @param taskList List of a Person's current tasks.
     */
    private ListCommand(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }
}
