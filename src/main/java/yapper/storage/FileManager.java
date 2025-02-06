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
            appendToFile(filePath, "Type,Description,isDone,From,To", false);
            for (Task t : taskList) {
                if (t instanceof ToDosTask) {
                    ToDosTask td = (ToDosTask) t;
                    FileManager.appendToFile(filePath,
                            String.format("%s,%s,%s,%s,%s", "Todos", td.getDescription(), td.getStatusIcon(), "", ""),
                            true);
                } else if (t instanceof DeadlineTask) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
                    DeadlineTask dl = (DeadlineTask) t;
                    FileManager.appendToFile(filePath, String.format("%s,%s,%s,%s,%s", "Deadline", dl.getDescription(),
                            dl.getStatusIcon(), "", dl.getByLocalDateTime().format(dtf)), true);
                } else if (t instanceof EventsTask) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
                    EventsTask ev = (EventsTask) t;
                    FileManager.appendToFile(filePath, String.format("%s,%s,%s,%s,%s", "Events", ev.getDescription(),
                            ev.getStatusIcon(), ev.getFromLocalDateTime().format(dtf),
                            ev.getToLocalDateTime().format(dtf)), true);
                } else {
                    System.out.println(String.format("%s is not added to %s.", t, file.getName()));
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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");

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
            case "Todos":
                ToDosTask td = new ToDosTask(taskDescription);
                if (taskIsDone.equals("X")) {
                    td.markAsDone();
                }
                taskList.add(td);
                break;
            case "Deadline":
                LocalDateTime taskByLocalDateTime = LocalDateTime.parse(taskTo, dtf);
                DeadlineTask dl = new DeadlineTask(taskDescription, taskByLocalDateTime);
                if (taskIsDone.equals("X")) {
                    dl.markAsDone();
                }
                taskList.add(dl);
                break;
            case "Event":
                LocalDateTime taskFromLocalDateTime = LocalDateTime.parse(taskFrom, dtf);
                LocalDateTime taskToLocalDateTime = LocalDateTime.parse(taskTo, dtf);
                EventsTask ev = new EventsTask(taskDescription, taskFromLocalDateTime, taskToLocalDateTime);
                if (taskIsDone.equals("X")) {
                    ev.markAsDone();
                }
                taskList.add(ev);
                break;
            default:
                System.out.println("Unknown task: " + taskDescription + "is not loaded!");
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
                    fw.write("Type,Description,isDone,From,To\n");
                }
            }
        } catch (IOException e) {
            System.out.println("File error occurred.");
            System.out.println(e.getLocalizedMessage());
            return null;
        }
        return file;
    }
}
