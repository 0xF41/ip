package yapper.data.task;

import yapper.data.ContentDisplayable;

/**
 * Task encapsulates an event that is created by the user when communicating
 * with the Yapper chatbot.
 */
public class Task implements ContentDisplayable {

    // Constants
    private static final String TASK_INFO_FORMAT_STRING = "[%s] %s";
    private static final String EMPTY_SYMBOL_STRING = " ";
    private static final String DONE_SYMBOL_STRING = "X";

    /**
     * Description of the task.
     */
    protected String description;

    /**
     * State of whether is task is marked as done (true) or not (false).
     */
    protected boolean isDone;

    /**
     * Constructs a Task object.
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the current status icon of the Task.
     *
     * @return a character indicating the status icon of the Task.
     */
    public String getStatusIcon() {
        return (isDone ? DONE_SYMBOL_STRING : EMPTY_SYMBOL_STRING); // mark done task with X
    }

    /**
     * Returns the description of the Task.
     *
     * @return description of the task
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Marks the current task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the current task as undone.
     */
    public void markAsUndone() {
        this.isDone = false;
    }

    /**
     * String representation of a Task.
     */
    @Override
    public String toString() {
        return String.format(
                TASK_INFO_FORMAT_STRING,
                this.getStatusIcon(),
                this.getDescription());
    }
}
