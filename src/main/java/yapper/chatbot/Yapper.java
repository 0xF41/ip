package yapper.chatbot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import yapper.commands.Command;
import yapper.data.exception.InvalidCommandSyntaxException;
import yapper.parser.CommandParser;
import yapper.task.Task;
import yapper.ui.Ui;

/**
 * Yapper represents a chatbot that interacts with the user.
 */
public class Yapper {

    /**
     * The name of the Chatbot
     */
    private final String name;

    /**
     * The Person's task list
     */
    private ArrayList<Task> taskList;

    /**
     * The file to store the task list
     */
    private File file;

    /**
     * Runs the Yapper chatbot session.
     *
     * @throws IOException if an I/O error occurs
     */
    public void run() throws IOException {
        Ui.printGreet(this.name);
        String cmd = "";
        Boolean ongoing = true;
        while (ongoing) {
            cmd = Ui.readCommand();
            Ui.clearConsole();
            Ui.printLine();
            try {
                Command command = CommandParser.parse(cmd, taskList, file);
                ongoing = command.execute();
            } catch (InvalidCommandSyntaxException e) {
                Ui.printError(e.getLocalizedMessage());
            } catch (IndexOutOfBoundsException e) {
                Ui.printError(e.getLocalizedMessage());
            } catch (IllegalArgumentException e) {
                Ui.printError(e.getLocalizedMessage());
            } finally {
                Ui.printLine();
            }
        }
    }

    /**
     * Constructs a Yapper chatbot.
     * 
     * @param name     The name of the chatbot
     * @param taskList The list of tasks
     * @param file     The file to store the task list
     */
    public Yapper(String name, ArrayList<Task> taskList, File file) {
        this.name = name;
        this.taskList = taskList;
        this.file = file;
    }
}
