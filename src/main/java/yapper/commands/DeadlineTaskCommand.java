package yapper.commands;

import java.util.ArrayList;

import yapper.task.DeadlineTask;
import yapper.task.Task;

/**
 * Represents a command to add a DeadlineTask task.
 */
public class DeadlineTaskCommand implements TaskCommand {

    /**
     * List of a Person's current tasks.
     */
    private ArrayList<Task> taskList;

    /**
     * DeadlineTask task to be added.
     */
    private DeadlineTask dl;

    /**
     * Constructs a DeadlineCommand object.
     *
     * @param taskList List of a Person's current tasks.
     * @param dl       DeadlineTask task to be added.
     */
    private DeadlineTaskCommand(ArrayList<Task> taskList, DeadlineTask dl) {
        this.taskList = taskList;
        this.dl = dl;
    }

    /**
     * Executes the command to add a DeadlineTask task.
     *
     * @param respondList List of responses to be displayed to the user.
     * @return True if the command is successfully executed, false otherwise.
     */
    @Override
    public boolean execute(ArrayList<String> respondList) {
        taskList.add(dl);
        respondList.add("Got it. I've added this task:");
        respondList.add(dl.toString());
        respondList.add(String.format("Now you have %d tasks in the list.", taskList.size()));
        return true;
    }

    /**
     * Returns the description of the DeadlineTask task.
     *
     * @return Description of the DeadlineTask task.
     */
    @Override
    public String getTaskDescription() {
        return dl.getDescription();
    }

    /**
     * Builds a DeadlineCommand object.
     *
     * @param taskList List of a Person's current tasks.
     * @param dl       DeadlineTask task to be added.
     * @return DeadlineCommand object.
     */
    public static Command buildDeadlineCommand(ArrayList<Task> taskList, DeadlineTask dl) {
        return new DeadlineTaskCommand(taskList, dl);
    }

}
