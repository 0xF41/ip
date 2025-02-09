package yapper.commands;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import yapper.data.task.Task;
import yapper.data.ContentDisplayable;

/**
 * Represents a command to find tasks with a search term.
 */
public class FindCommand<T extends ContentDisplayable> implements Command {

    private static final String NOT_FOUND_STRING = "Tasks with search term \"%s\" not found!";
    private static final String LIST_OUTPUT_FORMAT_STRING = "%d. %s";

    /**
     * List of a Person's current tasks.
     */
    private ArrayList<T> taskList;

    /**
     * Search term
     */
    private String searchTerm;

    /**
     * Constructs a FindCommand object.
     *
     * @param list List of a Person's current tasks.
     */
    public FindCommand(ArrayList<T> list, String searchTerm) {
        this.taskList = list;
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
}
