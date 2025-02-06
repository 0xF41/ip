package yapper.commands;

import java.util.ArrayList;

import yapper.task.Task;
import yapper.task.ToDos;

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
     * @param responseList List of responses to be displayed to the user.
     * @return True if the command is successfully executed, false otherwise.
     */
    @Override
    public boolean execute(ArrayList<String> responseList) {
        taskList.add(td);
        responseList.add("Got it. I've added this task:");
        responseList.add(td.toString());
        responseList.add(String.format("Now you have %d tasks in the list.", taskList.size()));
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
