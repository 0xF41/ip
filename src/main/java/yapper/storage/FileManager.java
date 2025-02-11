package yapper.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Represents a file manager to handle file operations.
 */
public abstract class FileManager {

    // Error messages
    protected static final String ASSERT_UNKNOWN_EVENT_TYPE = "Unknown event type: ";

    protected static final String ERR_TASK_NOT_ADDED_STRING = "%s is not added to %s.";
    protected static final String ERR_FILE_ERROR_OCCURRED = "File error occurred.";

    // Mark as done symbol
    protected static final String IS_DONE_SYMBOL = "X";

    // Tasks
    protected static final String EVENTS_COMMAND_STRING = "Events";
    protected static final String DEADLINE_COMMAND_STRING = "Deadline";
    protected static final String TODOS_COMMAND_STRING = "Todos";

    // Write format strings
    protected static final String WRITE_STRING_FORMAT_STRING = "%s\n";
    protected static final String WRITE_TASK_FORMAT_STRING = "%s,%s,%s,%s,%s";
    protected static final String WRITE_NOTE_FORMAT_STRING = "%s,%s";

    // Date time format string
    protected static final String DATE_TIME_FORMAT_STRING = "dd-MM-yyyy HHmm";

    // CSV headers
    protected static final String TASK_CSV_FILE_HEADERS_STRING = "Type,Description,isDone,From,To";
    protected static final String NOTE_CSV_FILE_HEADERS_STRING = "Title,Content";

    /**
     * Append text to file
     *
     * @param filePath     path of file to append text to
     * @param textToAppend text to append to file
     * @param append       true to append, false to overwrite
     * @throws IOException
     */
    protected static void appendToFile(String filePath, String textToAppend, boolean append) throws IOException {
        FileWriter fw = new FileWriter(filePath, append);
        fw.write(textToAppend + "\n");
        fw.close();
    }


    /**
     * Write CSV headers to file if file is empty
     *
     * @param file file to write CSV headers to
     * @throws IOException
     */
    protected static void writeCsvHeadersToTaskFile(File file, String csvFileHeaderString) throws IOException {
        try (FileWriter fw = new FileWriter(file, true)) {
            if (file.length() == 0) {
                fw.write(String.format(WRITE_STRING_FORMAT_STRING, csvFileHeaderString));
            }
        }
    }
}
