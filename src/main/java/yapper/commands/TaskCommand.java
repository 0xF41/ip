package yapper.commands;

/**
 * Represents a Task command to be executed by the chatbot.
 */
public interface TaskCommand extends Command {
    /**
     * Returns the description of the task.
     *
     * @return Description of the task.
     */
    public String getTaskDescription();
}
