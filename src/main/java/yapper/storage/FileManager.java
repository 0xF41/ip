package yapper.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import yapper.task.DeadlineTask;
import yapper.task.EventsTask;
import yapper.task.Task;
import yapper.task.ToDosTask;

/**
 * Represents a file manager to handle file operations.
 */
public class FileManager {

    private static final String EVENTS_COMMAND_STRING = "Events";
    private static final String WRITE_STRING_FORMAT_STRING = "%s\n";
    private static final String ERR_FILE_ERROR_OCCURRED = "File error occurred.";
    private static final String IS_DONE_SYMBOL = "X";
    private static final String ERR_TASK_NOT_ADDED_STRING = "%s is not added to %s.";
    private static final String DEADLINE_COMMAND_STRING = "Deadline";
    private static final String TODOS_COMMAND_STRING = "Todos";
    private static final String WRITE_FORMAT_STRING = "%s,%s,%s,%s,%s";
    private static final String DATE_TIME_FORMAT_STRING = "dd-MM-yyyy HHmm";
    private static final String CSV_FILE_HEADERS_STRING = "Type,Description,isDone,From,To";
    private static final String ASSERT_UNKNOWN_EVENT_TYPE = "Unknown event type: ";

    /**
     * Append text to file
     *
     * @param filePath     path of file to append text to
     * @param textToAppend text to append to file
     * @param append       true to append, false to overwrite
     * @throws IOException
     */
    private static void appendToFile(String filePath, String textToAppend, boolean append) throws IOException {
        FileWriter fw = new FileWriter(filePath, append);
        fw.write(textToAppend + "\n");
        fw.close();
    }

    /**
     * Save taskList to file
     *
     * @param file     file to save taskList to
     * @param taskList list of tasks to save
     * @return true if save is successful, false otherwise
     */
    public static boolean saveFileContents(File file, ArrayList<Task> taskList) {
        String filePath = file.getName();
        try {
            appendToFile(filePath, CSV_FILE_HEADERS_STRING, false);
            for (Task t : taskList) {
                if (t instanceof ToDosTask) {
                    ToDosTask td = (ToDosTask) t;
                    FileManager.appendToFile(filePath,
                            String.format(WRITE_FORMAT_STRING, TODOS_COMMAND_STRING, td.getDescription(), td.getStatusIcon(), "", ""),
                            true);
                } else if (t instanceof DeadlineTask) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_STRING);
                    DeadlineTask dl = (DeadlineTask) t;
                    FileManager.appendToFile(filePath, String.format(WRITE_FORMAT_STRING, DEADLINE_COMMAND_STRING, dl.getDescription(),
                            dl.getStatusIcon(), "", dl.getByLocalDateTime().format(dtf)), true);
                } else if (t instanceof EventsTask) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_STRING);
                    EventsTask ev = (EventsTask) t;
                    FileManager.appendToFile(filePath, String.format(WRITE_FORMAT_STRING, EVENTS_COMMAND_STRING, ev.getDescription(),
                            ev.getStatusIcon(), ev.getFromLocalDateTime().format(dtf),
                            ev.getToLocalDateTime().format(dtf)), true);
                } else {
                    System.out.println(String.format(ERR_TASK_NOT_ADDED_STRING, t, file.getName()));
                }
            }
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    /**
     * Load contents from file to taskList
     *
     * @param file file to load contents from
     * @return ArrayList of tasks loaded from file
     * @throws FileNotFoundException
     */
    public static ArrayList<Task> loadFileContents(File file) throws FileNotFoundException {
        ArrayList<Task> taskList = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_STRING);

        Scanner s = new Scanner(file);
        s.nextLine(); // skip first row containing csv headers
        while (s.hasNext()) {
            String[] tokens = s.nextLine().split(",");
            String taskType = "";
            String taskDescription = "";
            String taskIsDone = "";
            String taskFrom = "";
            String taskTo = "";
            taskType = tokens[0];
            taskDescription = tokens[1];
            if (tokens.length > 2) {
                taskIsDone = tokens[2];
            }
            if (tokens.length > 3) {
                taskFrom = tokens[3];
            }
            if (tokens.length > 4) {
                taskTo = tokens[4]; // task by in EventsTask
            }
            switch (taskType) {
            case TODOS_COMMAND_STRING:
                ToDosTask td = new ToDosTask(taskDescription);
                if (taskIsDone.equals(IS_DONE_SYMBOL)) {
                    td.markAsDone();
                }
                taskList.add(td);
                break;
            case DEADLINE_COMMAND_STRING:
                LocalDateTime taskByLocalDateTime = LocalDateTime.parse(taskTo, dtf);
                DeadlineTask dl = new DeadlineTask(taskDescription, taskByLocalDateTime);
                if (taskIsDone.equals(IS_DONE_SYMBOL)) {
                    dl.markAsDone();
                }
                taskList.add(dl);
                break;
            case EVENTS_COMMAND_STRING:
                LocalDateTime taskFromLocalDateTime = LocalDateTime.parse(taskFrom, dtf);
                LocalDateTime taskToLocalDateTime = LocalDateTime.parse(taskTo, dtf);
                EventsTask ev = new EventsTask(taskDescription, taskFromLocalDateTime, taskToLocalDateTime);
                if (taskIsDone.equals(IS_DONE_SYMBOL)) {
                    ev.markAsDone();
                }
                taskList.add(ev);
                break;
            default:
                assert false : ASSERT_UNKNOWN_EVENT_TYPE + taskType;
                break;
            }
        }
        s.close();

        return taskList;
    }

    /**
     * Open file with specified taskFileName
     *
     * @param taskFileName name of file to open
     * @return File object of the opened file
     */
    public static File openFile(String taskFileName) {
        File file = new File(taskFileName);
        try {
            file.createNewFile(); // create a new file if relative pathname DNE

            try (FileWriter fw = new FileWriter(file, true)) {
                if (file.length() == 0) { // Write first CSV row if file is empty
                    fw.write(String.format(WRITE_STRING_FORMAT_STRING, CSV_FILE_HEADERS_STRING));
                }
            }
        } catch (IOException e) {
            System.out.println(ERR_FILE_ERROR_OCCURRED);
            System.out.println(e.getLocalizedMessage());
            return null;
        }
        return file;
    }
}
