package yapper.data.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import yapper.data.notes.Note;
import yapper.data.task.Task;
import yapper.storage.FileManager;

/**
 * Represents a Person who uses the chatbot.
 */
public class Person {

    // Error messages
    private static final String ERR_FILE_NOT_FOUND_FORMAT_STRING = "Existing file %s not found.";

    /**
     * Path of Person's file to cache user tasks.
     */
    private String taskFileName;

    /**
     * File object to cache user tasks.
     */
    private File taskFile;

    /**
     * File object to cache user notes.
     */
    private File noteFile;

    /**
     * ArrayList to store a list of Person's Tasks.
     */
    private ArrayList<Task> taskList;

    /**
     * noteList to store a list of Person's Note.
     */
    private ArrayList<Note> noteList;

    /**
     * Constructs a Person instance.
     *
     * @param taskFileName name of the file to cache Person's tasks.
     */
    public Person(String taskFileName, String noteFileName) {
        this.taskFile = FileManager.openTaskFile(taskFileName);
        this.noteFile = FileManager.openNoteFile(noteFileName);
        try {
            this.taskList = FileManager.loadTaskFileContents(this.taskFile);
            this.noteList = FileManager.loadNoteFileContent(this.noteFile);
        } catch (FileNotFoundException e) {
            System.out.println(String.format(ERR_FILE_NOT_FOUND_FORMAT_STRING, this.taskFileName));
            this.taskList = new ArrayList<>();
        }
    }

    /**
     * Returns the list of tasks of the Person.
     *
     * @return list of tasks of the Person.
     */
    public ArrayList<Task> getTaskList() {
        return this.taskList;
    }

    /**
     * Returns the list of notes of the Person.
     *
     * @return list of notes of the Person.
     */
    public ArrayList<Note> getNoteList() {
        return this.noteList;
    }

    /**
     * Returns the object to store notes.
     *
     * @return File object containing Tasks data.
     */
    public File getTaskFile() {
        return this.taskFile;
    }

    /**
     * Returns the file to store notes.
     *
     * @return File object containing Notes data.
     */
    public File getNoteFile() {
        return this.noteFile;
    }
}
