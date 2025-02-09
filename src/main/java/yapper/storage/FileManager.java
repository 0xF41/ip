package yapper.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import yapper.data.notes.Note;
import yapper.data.task.DeadlineScheduleTask;
import yapper.data.task.EventsScheduleTask;
import yapper.data.task.Task;
import yapper.data.task.ToDosTask;

/**
 * Represents a file manager to handle file operations.
 */
public class FileManager {

    private static final String ASSERT_UNKNOWN_EVENT_TYPE = "Unknown event type: ";

    private static final String ERR_TASK_NOT_ADDED_STRING = "%s is not added to %s.";
    private static final String ERR_FILE_ERROR_OCCURRED = "File error occurred.";

    private static final String IS_DONE_SYMBOL = "X";

    private static final String EVENTS_COMMAND_STRING = "Events";
    private static final String DEADLINE_COMMAND_STRING = "Deadline";
    private static final String TODOS_COMMAND_STRING = "Todos";

    private static final String WRITE_STRING_FORMAT_STRING = "%s\n";
    private static final String WRITE_TASK_FORMAT_STRING = "%s,%s,%s,%s,%s";
    private static final String WRITE_NOTE_FORMAT_STRING = "%s,%s";

    private static final String DATE_TIME_FORMAT_STRING = "dd-MM-yyyy HHmm";

    private static final String TASK_CSV_FILE_HEADERS_STRING = "Type,Description,isDone,From,To";
    private static final String NOTE_CSV_FILE_HEADERS_STRING = "Title,Content";

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
     * Save EventsTask to file
     *
     * @param filePath path of file to save EventsTask to
     * @param t        EventsTask to save
     * @throws IOException
     */
    private static void saveEventsTaskToFile(String filePath, Task t) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_STRING);
        EventsScheduleTask ev = (EventsScheduleTask) t;
        FileManager.appendToFile(filePath, String.format(
                WRITE_TASK_FORMAT_STRING,
                EVENTS_COMMAND_STRING, ev.getDescription(),
                ev.getStatusIcon(), ev.getFromLocalDateTime().format(dtf),
                ev.getToLocalDateTime().format(dtf)), true);
    }

    /**
     * Save DeadlineTask to file
     *
     * @param filePath path of file to save DeadlineTask to
     * @param t        DeadlineTask to save
     * @throws IOException
     */
    private static void saveDeadlineTaskToFile(String filePath, Task t) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_STRING);
        DeadlineScheduleTask dl = (DeadlineScheduleTask) t;
        FileManager.appendToFile(
                filePath,
                String.format(
                        WRITE_TASK_FORMAT_STRING,
                        DEADLINE_COMMAND_STRING,
                        dl.getDescription(),
                        dl.getStatusIcon(),
                        "",
                        dl.getByLocalDateTime().format(dtf)),
                true);
    }

    /**
     * Save ToDosTask to file
     *
     * @param filePath path of file to save ToDosTask to
     * @param t        ToDosTask to save
     * @throws IOException
     */
    private static void saveToDosTaskToFile(String filePath, Task t) throws IOException {
        ToDosTask td = (ToDosTask) t;
        FileManager.appendToFile(filePath,
                String.format(
                        WRITE_TASK_FORMAT_STRING,
                        TODOS_COMMAND_STRING,
                        td.getDescription(),
                        td.getStatusIcon(),
                        "", ""),
                true);
    }

    /**
     * Load contents from file to taskList
     *
     * @param file file to load contents from
     * @return ArrayList of tasks loaded from file
     * @throws FileNotFoundException
     */
    public static ArrayList<Task> loadTaskFileContents(File file) throws FileNotFoundException {
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
                    loadToDosTaskFromFile(taskList, taskDescription, taskIsDone);
                    break;
                case DEADLINE_COMMAND_STRING:
                    loadDeadlineTaskFromFile(taskList, dtf, taskDescription, taskIsDone, taskTo);
                    break;
                case EVENTS_COMMAND_STRING:
                    loadEventsTaskFromFile(taskList, dtf, taskDescription, taskIsDone, taskFrom, taskTo);
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
     * Load EventsTask from file
     *
     * @param taskList        list of tasks to load EventsTask to
     * @param dtf             DateTimeFormatter to parse LocalDateTime
     * @param taskDescription description of EventsTask
     * @param taskIsDone      status of EventsTask
     * @param taskFrom        start time of EventsTask
     * @param taskTo          end time of EventsTask
     */
    private static void loadEventsTaskFromFile(ArrayList<Task> taskList, DateTimeFormatter dtf, String taskDescription,
            String taskIsDone, String taskFrom, String taskTo) {
        LocalDateTime taskFromLocalDateTime = LocalDateTime.parse(taskFrom, dtf);
        LocalDateTime taskToLocalDateTime = LocalDateTime.parse(taskTo, dtf);
        EventsScheduleTask ev = new EventsScheduleTask(taskDescription, taskFromLocalDateTime, taskToLocalDateTime);
        if (taskIsDone.equals(IS_DONE_SYMBOL)) {
            ev.markAsDone();
        }
        taskList.add(ev);
    }

    /**
     * Load DeadlineTask from file
     *
     * @param taskList        list of tasks to load DeadlineTask to
     * @param dtf             DateTimeFormatter to parse LocalDateTime
     * @param taskDescription description of DeadlineTask
     * @param taskIsDone      status of DeadlineTask
     * @param taskTo          deadline of DeadlineTask
     */
    private static void loadDeadlineTaskFromFile(ArrayList<Task> taskList,
            DateTimeFormatter dtf, String taskDescription, String taskIsDone, String taskTo) {
        LocalDateTime taskByLocalDateTime = LocalDateTime.parse(taskTo, dtf);
        DeadlineScheduleTask dl = new DeadlineScheduleTask(taskDescription, taskByLocalDateTime);
        if (taskIsDone.equals(IS_DONE_SYMBOL)) {
            dl.markAsDone();
        }
        taskList.add(dl);
    }

    /**
     * Load ToDosTask from file
     *
     * @param taskList        list of tasks to load ToDosTask to
     * @param taskDescription description of ToDosTask
     * @param taskIsDone      status of ToDosTask
     */
    private static void loadToDosTaskFromFile(ArrayList<Task> taskList, String taskDescription, String taskIsDone) {
        ToDosTask td = new ToDosTask(taskDescription);
        if (taskIsDone.equals(IS_DONE_SYMBOL)) {
            td.markAsDone();
        }
        taskList.add(td);
    }

    /**
     * Open file with specified taskFileName
     *
     * @param taskFileName name of cached note file to open
     * @return File object of the opened file
     */
    public static File openTaskFile(String taskFileName) {
        File file = new File(taskFileName);
        try {
            file.createNewFile(); // create a new file if relative pathname DNE
            writeCsvHeadersToTaskFile(file, TASK_CSV_FILE_HEADERS_STRING);
        } catch (IOException e) {
            System.out.println(ERR_FILE_ERROR_OCCURRED);
            System.out.println(e.getLocalizedMessage());
            return null;
        }
        return file;
    }

    /**
     * Open file with specified noteFileName
     *
     * @param noteFileName name of cached note file to open
     * @return File object of the opened file
     */
    public static File openNoteFile(String noteFileName) {
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
     * Write CSV headers to file if file is empty
     *
     * @param file file to write CSV headers to
     * @throws IOException
     */
    private static void writeCsvHeadersToTaskFile(File file, String csvFileHeaderString) throws IOException {
        try (FileWriter fw = new FileWriter(file, true)) {
            if (file.length() == 0) {
                fw.write(String.format(WRITE_STRING_FORMAT_STRING, csvFileHeaderString));
            }
        }
    }

    /**
     * Load contents from cached note file to taskList
     *
     * @param file file to load contents from
     * @return ArrayList of tasks loaded from file
     * @throws FileNotFoundException
     */
    public static ArrayList<Note> loadNoteFileContent(File file) throws FileNotFoundException {
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
    public static boolean saveNoteToFile(File file, ArrayList<Note> noteList) {
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

    /**
     * Save taskList to file
     *
     * @param file     file to save taskList to
     * @param taskList list of tasks to save
     * @return true if save is successful
     */
    public static boolean saveTaskToFile(File file, ArrayList<Task> taskList) {
        String filePath = file.getName();
        try {
            appendToFile(filePath, TASK_CSV_FILE_HEADERS_STRING, false);
            for (Task t : taskList) {
                if (t instanceof ToDosTask) {
                    saveToDosTaskToFile(filePath, t);
                } else if (t instanceof DeadlineScheduleTask) {
                    saveDeadlineTaskToFile(filePath, t);
                } else if (t instanceof EventsScheduleTask) {
                    saveEventsTaskToFile(filePath, t);
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

}
