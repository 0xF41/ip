package yapper.commands;

import java.util.ArrayList;

import yapper.data.task.Task;
import yapper.data.task.ToDosTask;

/**
 * Represents a command to add a ToDosTask task.
 */
public class ToDosTaskCommand implements TaskCommand {

    // Constants
    private static final String TASK_INFO_STRING = "Got it. I've added this task:";
    private static final String TASK_REMAINING_FORMAT_STRING = "Now you have %d tasks in the list.";
    private static final String ASSERT_TASK_LIST_NEGATIVE_STRING = "Task list should not be negative.";

    /**
     * List of a Person's current tasks.
     */
    private ArrayList<Task> taskList;

    /**
     * ToDosTask task to be added.
     */
    private ToDosTask td;

    /**
     * Constructs a ToDosCommand object.
     *
     * @param taskList List of a Person's current tasks.
     * @param td       ToDosTask task to be added.
     */
    private ToDosTaskCommand(ArrayList<Task> taskList, ToDosTask td) {
        this.taskList = taskList;
        this.td = td;
    }

    /**
     * Returns the description of the ToDosTask task.
     *
     * @return Description of the ToDosTask task.
     */
    @Override
    public String getTaskDescription() {
        return td.getDescription();
    }

    /**
     * Executes the command to add a ToDosTask task.
     *
     * @param responseList List of responses to be displayed to the user.
     * @return True if the command is successfully executed, false otherwise.
     */
    @Override
    public boolean execute(ArrayList<String> responseList) {
        taskList.add(td);
        assert taskList.size() >= 0 : ASSERT_TASK_LIST_NEGATIVE_STRING;
        responseList.add(TASK_INFO_STRING);
        responseList.add(td.toString());
        responseList.add(String.format(TASK_REMAINING_FORMAT_STRING, taskList.size()));
        return true;
    }

    /**
     * Builds a ToDosCommand object.
     *
     * @param taskList List of a Person's current tasks.
     * @param td       ToDosTask task to be added.
     * @return ToDosCommand object.
     */
    public static Command buildToDosCommand(ArrayList<Task> taskList, ToDosTask td) {
        return new ToDosTaskCommand(taskList, td);
    }
}
