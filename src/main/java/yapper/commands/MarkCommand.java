package yapper.commands;

import java.util.ArrayList;

import yapper.task.Task;
import yapper.ui.Ui;

/**
 * Represents a command to mark a task as done.
 */
public class MarkCommand implements Command {

    /**
     * List of a Person's current tasks.
     */
    private ArrayList<Task> taskList;

    /**
     * Index of the task to be marked as done.
     */
    private int idx;

    /**
     * Constructs a MarkCommand object.
     *
     * @param taskList List of a Person's current tasks.
     * @param idx      Index of the task to be marked as done.
     */
    private MarkCommand(ArrayList<Task> taskList, int idx) {
        this.taskList = taskList;
        this.idx = idx;
    }

    /**
     * Executes the command to mark a task as done.
     *
     * @return true to indicate the chatbot conversation should continue.
     */
    @Override
    public boolean execute() {
        Task t = this.taskList.get(this.idx);
        t.markAsDone();
        Ui.print("Nice! I've marked this task as done:");
        Ui.print(t);
        return true;
    }

    /**
     * Builds a MarkCommand object.
     *
     * @param taskList List of a Person's current tasks.
     * @param idx      Index of the task to be marked as done.
     * @return MarkCommand object.
     */
    public static Command buildMarkCommand(ArrayList<Task> taskList, int idx) {
        return new MarkCommand(taskList, idx);
    }

}
