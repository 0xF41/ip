package yapper.commands;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import yapper.task.Task;

/**
 * Represents a command to find tasks with a search term.
 */
public class FindTaskCommand implements Command {

    private static final String NOT_FOUND_STRING = "Tasks with search term \"%s\" not found!";
    private static final String LIST_OUTPUT_FORMAT_STRING = "%d. %s";

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
     * @param responseList List of responses to be displayed to the user.
     * @return True if the command is successfully executed, false otherwise.
     */
    @Override
    public boolean execute(ArrayList<String> responseList) {
        if (this.taskList.isEmpty()) {
            responseList.add(String.format(NOT_FOUND_STRING, searchTerm));
            return true;
        }

        AtomicInteger idx = new AtomicInteger(1); // To track the index for formatting
        taskList.stream()
                .filter(task -> task.getDescription().contains(searchTerm))
                .map(task -> String.format(LIST_OUTPUT_FORMAT_STRING, idx.getAndIncrement(), task))
                .forEach(responseList::add);

        if (responseList.isEmpty()) {
            responseList.add(String.format(NOT_FOUND_STRING, searchTerm));
            return true;
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
