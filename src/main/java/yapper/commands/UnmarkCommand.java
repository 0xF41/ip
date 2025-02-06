package yapper.commands;

import java.util.ArrayList;

import yapper.task.Task;

/**
 * Represents a command to mark a task as not done.
 */
public class UnmarkCommand implements Command {

    /**
     * List of a Person's current tasks.
     */
    private ArrayList<Task> taskList;

    /**
     * Index of the task to be marked as not done.
     */
    private int idx;

    /**
     * Constructs an UnmarkCommand object.
     *
     * @param taskList List of a Person's current tasks.
     * @param idx      Index of the task to be marked as not done.
     */
    private UnmarkCommand(ArrayList<Task> taskList, int idx) {
        this.taskList = taskList;
        this.idx = idx;
    }

    /**
     * Executes the command to mark a task as not done.
     *
     * @param responseList List of responses to be displayed to the user.
     * @return True if the command is successfully executed, false otherwise.
     */
    @Override
    public boolean execute(ArrayList<String> responseList) {
        Task t = taskList.get(idx);
        t.markAsUndone();
        responseList.add("OK, I've marked this task as not done yet:");
        responseList.add(t.toString());
        return true;
    }

    /**
     * Builds an UnmarkCommand object.
     *
     * @param taskList List of a Person's current tasks.
     * @param idx      Index of the task to be marked as not done.
     * @return UnmarkCommand object.
     */
    public static Command buildUnmarkCommand(ArrayList<Task> taskList, int idx) {
        return new UnmarkCommand(taskList, idx);
    }

}
