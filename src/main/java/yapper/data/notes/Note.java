package yapper.data.notes;

import yapper.data.ContentDisplayable;

/**
 * Note
 */
public class Note implements ContentDisplayable {

    private static final String NOTE_FORMAT_STRING = "%s: %s";

    private String noteName;

    private String noteContent;

    /**
     * Constructs a Note object
     *
     * @param noteName    title of the Note
     * @param noteContent content of the Note to be added
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
