package yapper.commands;

import java.util.ArrayList;

import yapper.task.Deadline;
import yapper.task.Task;
import yapper.ui.Ui;

/**
 * Represents a command to add a Deadline task.
 */
public class DeadlineTaskCommand implements TaskCommand {

    /**
     * List of a Person's current tasks.
     */
    private ArrayList<Task> taskList;

    /**
     * Deadline task to be added.
     */
    private Deadline dl;

    /**
     * Constructs a DeadlineCommand object.
     *
     * @param taskList List of a Person's current tasks.
     * @param dl       Deadline task to be added.
     */
    private DeadlineTaskCommand(ArrayList<Task> taskList, Deadline dl) {
        this.taskList = taskList;
        this.dl = dl;
    }

    /**
     * Executes the command to add a Deadline task.
     *
     * @return true to indicate the chatbot conversation should continue.
     */
    @Override
    public boolean execute() {
        taskList.add(dl);
        Ui.print("Got it. I've added this task:");
        Ui.print(dl);
        Ui.print(String.format("Now you have %d tasks in the list.", taskList.size()));
        return true;
    }

    /**
     * Returns the description of the Deadline task.
     *
     * @return Description of the Deadline task.
     */
    @Override
    public String getTaskDescription() {
        return dl.getDescription();
    }

    /**
     * Builds a DeadlineCommand object.
     *
     * @param taskList List of a Person's current tasks.
     * @param dl       Deadline task to be added.
     * @return DeadlineCommand object.
     */
    public static Command buildDeadlineCommand(ArrayList<Task> taskList, Deadline dl) {
        return new DeadlineTaskCommand(taskList, dl);
    }

}
