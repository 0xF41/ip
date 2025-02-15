package yapper.commands;

import java.io.File;
import java.util.ArrayList;

import javafx.application.Platform;
import yapper.data.notes.Note;
import yapper.data.task.Task;
import yapper.storage.NoteFileManager;
import yapper.storage.TaskFileManager;

/**
 * Represents a command to end the chatbot conversation.
 */
public class ByeCommand implements Command {

    /**
     * Message to display when the chatbot conversation ends.
     */
    private static final String BYE_MESSAGE = "Bye. Hope to see you again soon!";

    /**
     * List of a Person's current tasks.
     */
    private ArrayList<Task> taskList;

    /**
     * List of a Person's current notes.
     */
    private ArrayList<Note> noteList;

    /**
     * File to save the tasks to.
     */
    private File taskFile;

    /**
     * File to save the notes to.
     */
    private File noteFile;

    /**
     * TaskFileManager to manage tasks.
     */
    private TaskFileManager taskFileManager;

    /**
     * NoteFileManager to manage notes.
     */
    private NoteFileManager noteFileManager;

    /**
     * Constructs a ByeCommand object.
     *
     * @param taskList List of a Person's current tasks.
     * @param noteList List of a Person's current notes.
     * @param taskFile File to save the tasks to.
     * @param noteFile File to save the notes to.
     */
    public ByeCommand(ArrayList<Task> taskList, ArrayList<Note> noteList, File taskFile, File noteFile,
            TaskFileManager taskFileManager, NoteFileManager noteFileManager) {
        this.taskList = taskList;
        this.noteList = noteList;
        this.taskFile = taskFile;
        this.noteFile = noteFile;
        this.taskFileManager = taskFileManager;
        this.noteFileManager = noteFileManager;
    }

    /**
     * Executes the command to end the chatbot conversation.
     *
     * @param responseList List of responses to add to.
     * @return false to indicate that the chatbot conversation has ended.
     */
    @Override
    public boolean execute(ArrayList<String> responseList) {
        this.taskFileManager.save(taskFile, taskList);
        this.noteFileManager.save(noteFile, noteList);
        responseList.add(BYE_MESSAGE);
        Platform.exit();
        System.exit(0);
        return false;
    }
}
