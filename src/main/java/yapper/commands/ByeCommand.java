package yapper.commands;

import java.io.File;
import java.util.ArrayList;

import yapper.storage.FileManager;
import yapper.task.Task;
import yapper.ui.Ui;

/**
 * Represents a command to end the chatbot conversation.
 */
public class ByeCommand implements Command {

    /**
     * List of a Person's current tasks.
     */
    private ArrayList<Task> taskList;

    /**
     * File to save the tasks to.
     */
    private File file;

    /**
     * Constructs a ByeCommand object.
     *
     * @param taskList List of a Person's current tasks.
     * @param file     File to save the tasks to.
     */
    private ByeCommand(ArrayList<Task> taskList, File file) {
        this.taskList = taskList;
        this.file = file;
    }

    /**
     * Executes the command to end the chatbot conversation.
     *
     * @return false to indicate the end of the chatbot conversation.
     */
    @Override
    public boolean execute() {
        FileManager.saveFileContents(file, taskList);
        Ui.print("Bye. Hope to see you again soon!");
        return false;
    }

    /**
     * Builds a ByeCommand object.
     *
     * @param taskList List of a Person's current tasks.
     * @param file     File to save the tasks to.
     * @return ByeCommand object.
     */
    public static Command buildByeCommand(ArrayList<Task> taskList, File file) {
        return new ByeCommand(taskList, file);
    }
}
