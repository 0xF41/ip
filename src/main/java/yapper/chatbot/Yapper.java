package yapper.chatbot;

import java.io.File;
import java.util.ArrayList;

import yapper.task.Task;

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

    /**
     * Returns the name of the chatbot.
     *
     * @return The name of the chatbot
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the task list.
     *
     * @return The task list
     */
    public ArrayList<Task> getTaskList() {
        return this.taskList;
    }

    /**
     * Returns the file to store the task list.
     *
     * @return The file to store the task list
     */
    public File getFile() {
        return this.file;
    }
}
