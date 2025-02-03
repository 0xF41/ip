package yapper.commands;

import java.util.ArrayList;

import yapper.task.Task;
import yapper.task.ToDos;
import yapper.ui.Ui;

/**
 * Represents a command to add a ToDos task.
 */
public class ToDosTaskCommand implements TaskCommand {

    /**
     * List of a Person's current tasks.
     */
    private ArrayList<Task> taskList;

    /**
     * ToDos task to be added.
     */
    private ToDos td;

    /**
     * Constructs a ToDosCommand object.
     *
     * @param taskList List of a Person's current tasks.
     * @param td       ToDos task to be added.
     */
    private ToDosTaskCommand(ArrayList<Task> taskList, ToDos td) {
        this.taskList = taskList;
        this.td = td;
    }

    /**
     * Returns the description of the ToDos task.
     *
     * @return Description of the ToDos task.
     */
    @Override
    public String getTaskDescription() {
        return td.getDescription();
    }

    /**
     * Executes the command to add a ToDos task.
     *
     * @return true to indicate the chatbot conversation should continue.
     */
    @Override
    public boolean execute() {
        taskList.add(td);
        Ui.print("Got it. I've added this task:");
        Ui.print(td);
        Ui.print(String.format("Now you have %d tasks in the list.", taskList.size()));
        return true;
    }

    /**
     * Builds a ToDosCommand object.
     *
     * @param taskList List of a Person's current tasks.
     * @param td       ToDos task to be added.
     * @return ToDosCommand object.
     */
    public static Command buildToDosCommand(ArrayList<Task> taskList, ToDos td) {
        return new ToDosTaskCommand(taskList, td);
    }
}
