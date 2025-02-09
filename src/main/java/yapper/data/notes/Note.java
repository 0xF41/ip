package yapper.data.notes;

import yapper.data.ContentDisplayable;

/**
 * Represents a Note object.
 */
public class Note implements ContentDisplayable {

    // Constants
    private static final String NOTE_FORMAT_STRING = "%s: %s";

    /*
     * Title of the Note
     */
    private String noteName;

    /*
     * Content of the Note
     */
    private String noteContent;

    /**
     * Constructs a Note object
     *
     * @param noteName    title of the Note
     * @param noteContent content of the Note to be added
     * @return Note object
     */
    public Note(String noteName, String noteContent) {
        this.noteName = noteName;
        this.noteContent = noteContent;
    }

    /**
     * Return the title of the note.
     *
     * @return title of the note.
     */
    public String getName() {
        return this.noteName;
    }

    /**
     * Return the content of the note.
     *
     * @return content of the note.
     */
    @Override
    public String toString() {
        return String.format(NOTE_FORMAT_STRING, this.noteName, this.noteContent);
    }

    /**
     * Return the tile of the note
     *
     * @return the title of the note
     */
    @Override
    public String getDescription() {
        return this.getName();
    }
}
