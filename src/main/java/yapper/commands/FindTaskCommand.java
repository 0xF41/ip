package yapper.commands;

import java.util.ArrayList;

import yapper.task.Task;
import yapper.ui.Ui;

/**
 * Represents a command to find tasks with a search term.
 */
public class FindTaskCommand implements Command {

    /**
     * List of a Person's current tasks.
     */
    private ArrayList<Task> taskList;
    private String searchTerm;

    /**
     * Constructs a FindCommand object.
     * 
     * @param taskList List of a Person's current tasks.
     */
    private FindTaskCommand(ArrayList<Task> taskList, String searchTerm) {
        this.taskList = taskList;
        this.searchTerm = searchTerm;
    }

    /**
     * Executes the command to find tasks with a search term.
     * 
     * @return true to indicate the chatbot conversation should continue.
     */
    @Override
    public boolean execute() {
        if (this.taskList.isEmpty()) {
            Ui.printError(String.format("Tasks with search term \"%s\" not found!", searchTerm));
            return true;
        }
        for (int i = 0, n = taskList.size(); i < n; i++) { // O(nm)
            Task t = taskList.get(i);
            if (t.getDescription().contains(searchTerm)) {
                Ui.print(String.format("%d. %s", i + 1, t));
            }
        }
        return true;
    }

    /**
     * Builds a FindCommand object.
     * 
     * @param taskList List of a Person's current tasks.
     * @return FindCommand object.
     */
    public static Command buildFindCommand(ArrayList<Task> taskList, String searchTerm) {
        return new FindTaskCommand(taskList, searchTerm);
    }
}
