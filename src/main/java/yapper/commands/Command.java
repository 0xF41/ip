package yapper.commands;

import java.util.ArrayList;

/**
 * Represents a command to be executed by the chatbot.
 */
public interface Command {
    /**
     * Executes the command.
     *
     * @param responseList List of responses to be displayed to the user.
     * @return True if the command is successfully executed, false otherwise.
     */
    public boolean execute(ArrayList<String> responseList);
}
