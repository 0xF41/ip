package yapper.commands;

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
     * @return true to indicate the chatbot conversation should continue.
     */
    @Override
    public boolean execute() {
        Ui.printMenu();
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
