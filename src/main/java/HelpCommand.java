import java.io.File;
import java.util.ArrayList;

public class HelpCommand implements Command {

    ArrayList<Task> taskList;
    File file; 

    @Override
    public boolean execute() {
        Ui.printMenu();
        return true;
    }

    public static Command buildHelpCommand() {
        return new HelpCommand();
    }

    private HelpCommand() {
        
    }
}
