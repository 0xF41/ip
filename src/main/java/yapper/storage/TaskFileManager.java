package yapper.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import yapper.data.task.DeadlineScheduleTask;
import yapper.data.task.EventsScheduleTask;
import yapper.data.task.Task;
import yapper.data.task.ToDosTask;

/**
 * Manages the loading and saving of tasks to file.
 */
public class TaskFileManager extends FileManager implements Persistable<Task> {

    private static final String EMPTY_STRING = "";

    /**
     * Constructs a TaskFileManager instance.
     */
    public TaskFileManager() {

    }

    /**
     * Open file with specified taskFileName
     *
     * @param taskFileName name of cached note file to open
     * @return File object of the opened file
     */
    @Override
    public File open(String taskFileName) {
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
     * Load contents from file to taskList
     *
     * @param file file to load contents from
     * @return ArrayList of tasks loaded from file
     * @throws FileNotFoundException if file is not found
     */
    @Override
    public ArrayList<Task> load(File file) throws FileNotFoundException {
        ArrayList<Task> taskList = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_STRING);

        Scanner s = new Scanner(file);
        s.nextLine(); // skip first row containing csv headers
        while (s.hasNext()) {
            String[] tokens = s.nextLine().split(",");
            String taskType = EMPTY_STRING;
            String taskDescription = EMPTY_STRING;
            String taskIsDone = EMPTY_STRING;
            String taskFrom = EMPTY_STRING;
            String taskTo = EMPTY_STRING;
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
     * Save taskList to file
     *
     * @param file     file to save taskList to
     * @param taskList list of tasks to save
     * @return true if save is successful
     */
    @Override
    public boolean save(File file, ArrayList<Task> taskList) {
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
                        EMPTY_STRING,
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
                        EMPTY_STRING, EMPTY_STRING),
                true);
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
}
