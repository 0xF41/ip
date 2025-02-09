package yapper.commands;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a command to list all objects of a type T.
 * @param <T> type of the objects in the list
 */
public class ListCommand<T> implements Command {

    private static final String LIST_EMPTY_STRING = "Specified List is empty!";
    private static final String LIST_OUTPUT_FORMAT_STRING = "%d. %s";

    /**
     * List of a Person's current tasks/notes.
     */
    private ArrayList<T> list;

    /**
     * Constructs a ListCommand object.
     * @param <T> Type of the list.
     *
     * @param list list to be printed upon the execute() method.
     */
    public ListCommand(ArrayList<T> list) {
        this.list = list;
    }

    /**
     * Executes the command to list all tasks.
     *
     * @param responseList List of responses to be displayed to the user.
     * @return True if the command is successfully executed, false otherwise.
     */
    @Override
    public boolean execute(ArrayList<String> responseList) {
        if (this.list.isEmpty()) {
            responseList.add(LIST_EMPTY_STRING);
            return true;
        }

        AtomicInteger idx = new AtomicInteger(1); // To track the index for formatting
        list.stream()
                .map(task -> String.format(LIST_OUTPUT_FORMAT_STRING, idx.getAndIncrement(), task))
                .forEach(responseList::add);

        return true;
    }
}
