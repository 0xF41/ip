import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Yapper chatbot
 */
public class Yapper {

    /**
     * BufferedReader object to read input from user.
     */
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));;

    /**
     * Name of the chatbot
     */
    public final String name;

    /**
     * The Person's ArrayList to store user tasks.
     */
    private ArrayList<Task> taskList;

    /**
     * The Person's file object to cache user tasks
     */
    private File file; 


    /**
     * Displays a default message when the chatbot is started.
     */
    private void greet() {
        System.out.println("____________________________________________________________");
        System.out.println(String.format("Hello! I'm %s!", this.name));
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");

    }

    /**
     * Starts the conversation between the user and the chatbot. 
     * 
     * @throws IOException
     */
    private void startConversation() throws IOException {
        this.greet();
        String cmd = "";
        while(true) {
            cmd = br.readLine();
            System.out.println("____________________________________________________________");
            try {
                if(!CommandParser.processCommand(cmd, this.taskList, this.file)) break;
            } catch (InvalidCommandSyntaxException e) {
                System.out.println(e);
            } catch (IndexOutOfBoundsException e) {
                System.out.println(e);
            } catch (IllegalArgumentException e) {
                System.out.println(e);
            } finally {
                System.out.println("____________________________________________________________");
            }
        }
        System.out.println("____________________________________________________________");
    }

    /**
     * Constructs a Yapper object representing a yapper chatbot session initiated by a Person. 
     * 
     * @param taskList the Person's task list
     */
    public Yapper(String name, ArrayList<Task> taskList, File file) {
        this.name = name;
        this.taskList = taskList;
        this.file = file;
    }

    /**
     * Main Function
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Person p1 = new Person("usertaskdata.csv");
        Yapper y1 = new Yapper("Yapper", p1.taskList, p1.file);
        y1.startConversation();
    }
}
