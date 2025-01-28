import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * A Person instance
 */
public class Person {

    /**
     * Path of Person's file to cache user tasks
     */
    private String taskFileName;

    /**
     * File object to cache user tasks
     */
    public File file;

    /**
     * ArrayList to store a list of Person's Tasks
     */
    public ArrayList<Task> taskList;

    /**
     * Constructs a Person instance
     * 
     * @param taskFileName name of the file to cache Person's tasks
     */
    public Person(String taskFileName) {
        this.file = FileManager.openFile(taskFileName);
        try {
            this.taskList = FileManager.loadFileContents(this.file);
        } catch (FileNotFoundException e) {
            System.out.println(String.format("File %s not found.", this.taskFileName));
            this.taskList = new ArrayList<>();
        }
    }
}
