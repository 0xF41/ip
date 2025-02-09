package yapper.commands;

import java.util.ArrayList;

import yapper.data.notes.Note;
import yapper.data.task.Task;

/**
 * Represents a command to delete a task.
 */
public class DeleteCommand<T> implements Command {

    // Constants
    private static final String DELETE_REMAINING_TASKS_STRING = "You now have %d tasks.";
    private static final String DELETE_TASK_INFO_STRING = "Noted. I've removed this task: ";
    private static final String DELETE_REMAINING_NOTES_STRING = "You now have %d notes.";
    private static final String DELETE_NOTE_INFO_STRING = "Noted. I've removed this note: ";

    // Error messages
    private static final String ASSERT_LIST_NEGATIVE_STRING = "List should not be negative.";

    /**
     * List of a Person's current tasks/notes.
     */
    private ArrayList<T> list;

    /**
     * Index of the task to be deleted in the list.
     */
    private int idx;

    /**
     * Constructs a DeleteCommand object.
     *
     * @param list List of a Person's current tasks/notes.
     * @param idx  Index of the task to be deleted in the list.
     */
    public DeleteCommand(ArrayList<T> list, int idx) {
        this.list = list;
        this.idx = idx;
    }

    /**
     * Executes the command to delete a task.
     *
     * @param responseList List of responses to be displayed to the user.
     * @return True if the command is successfully executed, false otherwise.
     */
    @Override
    public boolean execute(ArrayList<String> responseList) {

        if (list.get(idx) instanceof Task) {
            responseList.add(DELETE_TASK_INFO_STRING);
        } else if (list.get(idx) instanceof Note) {
            responseList.add(DELETE_NOTE_INFO_STRING);
        }

        responseList.add(list.get(idx).toString());

        assert list.size() >= 0 : ASSERT_LIST_NEGATIVE_STRING;

        if (list.get(idx) instanceof Task) {
            responseList.add(String.format(DELETE_REMAINING_TASKS_STRING, list.size() - 1));
        } else if (list.get(idx) instanceof Note) {
            responseList.add(String.format(DELETE_REMAINING_NOTES_STRING, list.size() - 1));
        }

        list.remove(idx);

        return true;
    }
}
