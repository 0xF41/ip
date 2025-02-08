package yapper.commands;

import java.util.ArrayList;

import yapper.task.Task;

/**
 * Represents a command to mark a task as done.
 */
public class MarkCommand implements Command {

    private static final String ASSERT_TASK_NULL_STRING = "Task should not be null.";
    private static final String MARK_INFO_STRING = "Nice! I've marked this task as done:";

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
     * @param responseList List of responses to be displayed to the user.
     * @return True if the command is successfully executed, false otherwise.
     */
    @Override
    public boolean execute(ArrayList<String> responseList) {
        Task t = this.taskList.get(this.idx);
        assert t != null : ASSERT_TASK_NULL_STRING;
        t.markAsDone();
        responseList.add(MARK_INFO_STRING);
        responseList.add(t.toString());
        return true;
    }

    /**
     * Builds a MarkCommand object.
     *
     * @param taskList List of a Person's current tasks.
     * @param idx      Index of the task to be marked as done.
     * @return MarkCommand object.
     */
    public static Command buildMarkCommand(ArrayList<Task> taskList, int idx) throws IndexOutOfBoundsException {
        try {
            taskList.get(idx);
        } catch (IndexOutOfBoundsException e) {
            throw e;
        }
        return new MarkCommand(taskList, idx);
    }

}
