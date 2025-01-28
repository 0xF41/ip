import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Manages the files used by Yapper chatbot
 */
public class FileManager {

    /**
     * Append text to specified file in filepath
     * 
     * @param filePath     path of file
     * @param textToAppend text to append
     * @param append       set true for append to file and false to overwrite file
     * @throws IOException
     */
    private static void appendToFile(String filePath, String textToAppend, boolean append) throws IOException {
        FileWriter fw = new FileWriter(filePath, append);
        fw.write("\n" + textToAppend);
        fw.close();
    }

    /**
     * Save contents from the current taskList to <file-name.csv> according to open
     * file descriptor
     * 
     * @param file     open file descriptor
     * @param taskList user list of tasks
     */
    public static boolean saveFileContents(File file, ArrayList<Task> taskList) {
        String filePath = file.getName();
        try {
            appendToFile(filePath, "Type,Description,isDone,From,To", false);
            for (Task t : taskList) {
                if (t instanceof ToDos) {
                    ToDos td = (ToDos) t;
                    FileManager.appendToFile(filePath,
                            String.format("%s,%s,%s,%s,%s", "Todos", td.description, td.getStatusIcon(), "", ""), true);
                } else if (t instanceof Deadline) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
                    Deadline dl = (Deadline) t;
                    FileManager.appendToFile(filePath, String.format("%s,%s,%s,%s,%s", "Deadline", dl.description,
                            dl.getStatusIcon(), "", dl.getByLocalDateTime().format(dtf)), true);
                } else if (t instanceof Events) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
                    Events ev = (Events) t;
                    FileManager.appendToFile(filePath, String.format("%s,%s,%s,%s,%s", "Deadline", ev.description,
                            ev.getStatusIcon(), ev.getFromLocalDateTime().format(dtf),
                            ev.getToLocalDateTime().format(dtf)), true);
                } else {
                    Ui.print(String.format("%s is not added to %s.", t, file.getName()));
                }
            }
        } catch (IOException e) {
            Ui.printError(e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    /**
     * Loads and returns the file contents into an ArrayList<Task>
     * 
     * @param file File object to be read from
     * @return An ArrayList of Task objects - ArrayList<Task>
     * @throws FileNotFoundException
     */
    public static ArrayList<Task> loadFileContents(File file) throws FileNotFoundException {
        ArrayList<Task> taskList = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");

        Scanner s = new Scanner(file);
        s.nextLine(); // skip first row containing csv headers
        while (s.hasNext()) {
            String[] tokens = s.nextLine().split(",");
            String taskType = "", taskDescription = "", taskIsDone = "", taskFrom = "", taskTo = "";
            taskType = tokens[0];
            taskDescription = tokens[1];
            if (tokens.length > 2)
                taskIsDone = tokens[2];
            if (tokens.length > 3)
                taskFrom = tokens[3];
            if (tokens.length > 4)
                taskTo = tokens[4]; // task by in Events

            switch (taskType) {
                case "Todos":
                    ToDos td = new ToDos(taskDescription);
                    if (taskIsDone.equals("X"))
                        td.markAsDone();
                    taskList.add(td);
                    break;
                case "Deadline":
                    LocalDateTime taskByLocalDateTime = LocalDateTime.parse(taskTo, dtf);
                    Deadline dl = new Deadline(taskDescription, taskByLocalDateTime);
                    if (taskIsDone.equals("X"))
                        dl.markAsDone();
                    taskList.add(dl);
                    break;
                case "Event":
                    LocalDateTime taskFromLocalDateTime = LocalDateTime.parse(taskFrom, dtf);
                    LocalDateTime taskToLocalDateTime = LocalDateTime.parse(taskTo, dtf);
                    Events ev = new Events(taskDescription, taskFromLocalDateTime, taskToLocalDateTime);
                    if (taskIsDone.equals("X"))
                        ev.markAsDone();
                    taskList.add(ev);
                    break;
                default:
                    Ui.printError("Unknown task: " + taskDescription + "is not loaded!");
                    break;
            }
        }
        s.close();

        return taskList;
    }

    /**
     * Opens a Person's cached file containing Tasks
     * 
     * @param taskFileName relative filepath of Person's cached file
     * @return File instance
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
            Ui.printError("File error occurred."); 
            Ui.printError(e.getLocalizedMessage());
            return null;
        }
        return file;
    }
}
