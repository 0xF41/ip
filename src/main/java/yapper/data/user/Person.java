package yapper.data.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import yapper.storage.FileManager;
import yapper.task.Task;

/**
 * Represents a Person who uses the chatbot.
 */
public class Person {

    private static final String ERR_FILE_NOT_FOUND_FORMAT_STRING = "Existing file %s not found.";

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
            System.out.println(String.format(ERR_FILE_NOT_FOUND_FORMAT_STRING, this.taskFileName));
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
