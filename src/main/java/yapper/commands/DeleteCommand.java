package yapper.commands;

import java.util.ArrayList;

import yapper.task.Task;
import yapper.ui.Ui;

/**
 * Represents a command to delete a task.
 */
public class DeleteCommand implements Command {

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
     * @return true to indicate the chatbot conversation should continue.
     */
    @Override
    public boolean execute() {
        Ui.print("Noted. I've removed this task: ");
        Ui.print(taskList.get(idx));
        taskList.remove(idx);
        Ui.print(String.format("Now you have %d tasks in the list.", taskList.size()));
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
        return new DeleteCommand(taskList, idx);
    }
}
