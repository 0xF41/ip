package yapper.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Represents a persistable object.
 * @param <T> type of object to persist
 */
public interface Persistable<T> {

    /**
     * Open file with specified fileName
     *
     * @param fileName name of file to open
     * @return File object of the opened file
     */
    public File open(String fileName);

    /**
     * Load contents from file to list
     *
     * @param file file to load contents from
     * @return ArrayList of objects loaded from file
     * @throws FileNotFoundException if file is not found
     */
    public ArrayList<T> load(File file) throws FileNotFoundException;

    /**
     * Save list to file
     *
     * @param file     file to save list to
     * @param noteList list to save to file
     * @return true if save is successful, false otherwise
     */
    public boolean save(File file, ArrayList<T> noteList);
}
