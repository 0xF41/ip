package yapper.commands;

/**
 * Represents a command to be executed by the chatbot.
 */
public interface Command {
    /**
     * Executes the command.
     *
     * @return true to indicate the chatbot conversation should continue.
     */
    public boolean execute();
}
