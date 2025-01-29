import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Yapper chatbot
 */
public class Yapper {

    /**
     * Name of the chatbot
     */
    private final String name;

    /**
     * The Person's ArrayList to store user tasks.
     */
    private ArrayList<Task> taskList;

    /**
     * The Person's file object to cache user tasks
     */
    private File file;

    /**
     * Starts the conversation between the user and the chatbot.
     * 
     * @throws IOException
     */
    public void run() throws IOException {
        Ui.printGreet(this.name);
        String cmd = "";
        Boolean ongoing = true;
        while (ongoing) {
            cmd = Ui.readCommand();
            Ui.clearConsole();
            Ui.printLine();
            try {
                Command command = CommandParser.parse(cmd, taskList, file);
                ongoing = command.execute();
            } catch (InvalidCommandSyntaxException e) {
                Ui.printError(e.getLocalizedMessage());
            } catch (IndexOutOfBoundsException e) {
                Ui.printError(e.getLocalizedMessage());
            } catch (IllegalArgumentException e) {
                Ui.printError(e.getLocalizedMessage());
            } finally {
                Ui.printLine();
            }
        }
    }

    /**
     * Constructs a Yapper object representing a yapper chatbot session initiated by
     * a Person.
     * 
     * @param taskList the Person's task list
     */
    public Yapper(String name, ArrayList<Task> taskList, File file) {
        this.name = name;
        this.taskList = taskList;
        this.file = file;
    }
}
