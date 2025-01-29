package yapper.commands;

import java.util.ArrayList;

import yapper.task.Task;
import yapper.ui.Ui;

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
     * Executes the command to mark a task as not done.
     *
     * @return true to indicate the chatbot conversation should continue.
     */
    @Override
    public boolean execute() {
        Task t = taskList.get(idx);
        t.markAsUndone();
        Ui.print("OK, I've marked this task as not done yet:");
        Ui.print(t);
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
}
