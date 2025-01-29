import java.io.File;
import java.util.ArrayList;

public class ByeCommand implements Command {

    ArrayList<Task> taskList;
    File file; 

    @Override
    public boolean execute() {
        FileManager.saveFileContents(file, taskList);
        Ui.print("Bye. Hope to see you again soon!");
        return false;
    }

    public static Command buildByeCommand(ArrayList<Task> taskList, File file) {
        return new ByeCommand(taskList, file);
    }

    private ByeCommand(ArrayList<Task> taskList, File file) {
        this.taskList = taskList;
        this.file = file;
    }
}
