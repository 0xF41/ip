package yapper.commands;

import java.util.ArrayList;

import yapper.data.notes.Note;

/**
 * Represents a command to add a note.
 */
public class NoteCommand implements Command {

    // Constants
    private static final String NOTE_INFO_FORMAT_STRING = "Note \"%s\" has been added.";

    /**
     * List of a Person's current notes.
     */
    private ArrayList<Note> notesList;

    /**
     * Note to be added.
     */
    private Note noteToAdd;

    /**
     * Constructor of a NoteCommand object.
     *
     * @param title     List of a Person's current tasks.
     * @param content   Index of the task to be marked as done.
     * @return          NoteCommand object.
     */
    private NoteCommand(ArrayList<Note> notesList, Note noteToAdd) {
        this.notesList = notesList;
        this.noteToAdd = noteToAdd;
    }

    /**
     * Executes the command to mark a task as done.
     *
     * @param responseList List of responses to be displayed to the user.
     * @return True if the command is successfully executed, false otherwise.
     */
    @Override
    public boolean execute(ArrayList<String> responseList) {
        notesList.add(this.noteToAdd);
        responseList.add(String.format(NOTE_INFO_FORMAT_STRING, this.noteToAdd.getName()));
        return true;
    }

    /**
     * Builds a NoteCommand object.
     *
     * @param notesList List of a Person's current notes.
     * @param noteToAdd Note to be added.
     * @return NoteCommand object.
     */
    public static Command buildNoteCommand(ArrayList<Note> notesList, Note noteToAdd) {
        return new NoteCommand(notesList, noteToAdd);
    }

}
