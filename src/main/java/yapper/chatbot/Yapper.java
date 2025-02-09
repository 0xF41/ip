package yapper.chatbot;

import java.io.File;
import java.util.ArrayList;

import yapper.data.notes.Note;
import yapper.data.task.Task;

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
     * The Person's note list
     */
    private ArrayList<Note> noteList;

    /**
     * The file to store the task list
     */
    private File taskFile;

    private File noteFile;

    /**
     * Constructs a Yapper chatbot.
     *
     * @param name     The name of the chatbot
     * @param taskList The list of tasks
     * @param file     The file to store the task list
     */
    public Yapper(String name, ArrayList<Task> taskList, ArrayList<Note> noteList, File taskFile, File noteFile) {
        this.name = name;
        this.taskList = taskList;
        this.noteList = noteList;
        this.taskFile = taskFile;
        this.noteFile = noteFile;
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
     * Returns the note list.
     *
     * @return The note list
     */
    public ArrayList<Note> getNoteList() {
        return this.noteList;
    }

    /**
     * Returns the file to store the task list.
     *
     * @return The file to store the task list
     */
    public File getTaskFile() {
        return this.taskFile;
    }

    public File getNoteFile() {
        return this.noteFile;
    }
}
