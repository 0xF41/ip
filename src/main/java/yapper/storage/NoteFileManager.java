package yapper.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import yapper.data.notes.Note;

/**
 * Manages the loading and saving of notes to file.
 */
public class NoteFileManager extends FileManager {

    /**
     * Open file with specified noteFileName
     *
     * @param noteFileName name of cached note file to open
     * @return File object of the opened file
     */
    public static File open(String noteFileName) {
        File file = new File(noteFileName);
        try {
            file.createNewFile();
            writeCsvHeadersToTaskFile(file, NOTE_CSV_FILE_HEADERS_STRING);
        } catch (IOException e) {
            System.out.println(ERR_FILE_ERROR_OCCURRED);
            System.out.println(e.getLocalizedMessage());
            return null;
        }
        return file;
    }

    /**
     * Load contents from cached note file to taskList
     *
     * @param file file to load contents from
     * @return ArrayList of tasks loaded from file
     * @throws FileNotFoundException
     */
    public static ArrayList<Note> load(File file) throws FileNotFoundException {
        ArrayList<Note> noteList = new ArrayList<>();
        Scanner s = new Scanner(file);
        s.nextLine(); // skip first row containing csv headers
        while (s.hasNext()) {
            String[] tokens = s.nextLine().split(",");
            String title = tokens[0];
            String content = tokens[1];
            noteList.add(new Note(title, content));
        }
        s.close();
        return noteList;
    }

    /**
     * Save noteList to file
     *
     * @param file     file to save noteList to
     * @param noteList list of notes to save
     * @return true if save is successful
     */
    public static boolean save(File file, ArrayList<Note> noteList) {
        String filePath = file.getName();
        try {
            appendToFile(filePath, NOTE_CSV_FILE_HEADERS_STRING, false);
            for (Note n : noteList) {
                appendToFile(filePath, String.format(WRITE_NOTE_FORMAT_STRING, n.getName(), n.getDescription()), true);
            }
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
            return false;
        }
        return true;
    }
}
