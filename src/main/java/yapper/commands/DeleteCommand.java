package yapper.commands;

import java.util.ArrayList;

import yapper.task.Task;

/**
 * Represents a command to delete a task.
 */
public class DeleteCommand implements Command {

    private static final String ASSERT_TASK_LIST_NEGATIVE_STRING = "Task list should not be negative.";
    private static final String ASSERT_DELETE_TASKLIST_NOT_NULL_STRING = "EventsTask should not be null.";

    /**
     * List of a Person's current tasks.
     */
    private ArrayList<Task> taskList;

    /**
     * Index of the task to be deleted.
     */
    private int idx;

    /**
     * Constructs a DeleteCommand object.
     *
     * @param taskList List of a Person's current tasks.
     * @param idx      Index of the task to be deleted.
     */
    private DeleteCommand(ArrayList<Task> taskList, int idx) {
        this.taskList = taskList;
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
        responseList.add("Noted. I've removed this task: ");
        responseList.add(taskList.get(idx).toString());
        taskList.remove(idx);
        assert taskList.size() >= 0 : ASSERT_TASK_LIST_NEGATIVE_STRING;
        responseList.add(String.format("Now you have %d tasks in the list.", taskList.size()));
        return true;
    }

    /**
     * Builds a DeleteCommand object.
     *
     * @param taskList List of a Person's current tasks.
     * @param idx      Index of the task to be deleted.
     * @return DeleteCommand object.
     */
    public static Command buildDeleteCommand(ArrayList<Task> taskList, int idx) {
        assert taskList != null : ASSERT_DELETE_TASKLIST_NOT_NULL_STRING;
        return new DeleteCommand(taskList, idx);
    }
}
