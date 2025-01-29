package yapper.commands;

import yapper.ui.Ui;

public class HelpCommand implements Command {

    @Override
    public boolean execute() {
        Ui.printMenu();
        return true;
    }

    public static Command buildHelpCommand() {
        return new HelpCommand();
    }

    private HelpCommand() {

    }
}
