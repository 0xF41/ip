package yapper.commands;

import java.util.ArrayList;

import yapper.ui.Ui;

/**
 * Represents a command to print the help menu.
 */
public class HelpCommand implements Command {

    /**
     * Constructs a HelpCommand object.
     */
    private HelpCommand() {

    }

    /**
     * Executes the command to print the help menu.
     *
     * @param responseList List of responses to be displayed to the user.
     *
     * @return true to indicate the chatbot conversation should continue.
     */
    @Override
    public boolean execute(ArrayList<String> responseList) {
        responseList.add(Ui.printMenu());
        return true;
    }

    /**
     * Builds a HelpCommand object.
     *
     * @return HelpCommand object.
     */
    public static Command buildHelpCommand() {
        return new HelpCommand();
    }

}
