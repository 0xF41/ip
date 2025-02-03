package yapper.data.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import yapper.storage.FileManager;
import yapper.task.Task;
import yapper.ui.Ui;

/**
 * Represents a Person who uses the chatbot.
 */
public class Person {

    /**
     * Path of Person's file to cache user tasks
     */
    private String taskFileName;

    /**
     * File object to cache user tasks
     */
    private File file;

    /**
     * ArrayList to store a list of Person's Tasks
     */
    private ArrayList<Task> taskList;

    /**
     * Constructs a Person instance
     *
     * @param taskFileName name of the file to cache Person's tasks
     */
    public Person(String taskFileName) {
        this.file = FileManager.openFile(taskFileName);
        try {
            this.taskList = FileManager.loadFileContents(this.file);
        } catch (FileNotFoundException e) {
            Ui.printError(String.format("Existing file %s not found.", this.taskFileName));
            this.taskList = new ArrayList<>();
        }
    }

    /**
     * Returns the list of tasks of the Person
     *
     * @return list of tasks of the Person
     */
    public ArrayList<Task> getTaskList() {
        return this.taskList;
    }

    /**
     * Returns the file object of the Person
     *
     * @return file object of the Person
     */
    public File getFile() {
        return this.file;
    }
}
