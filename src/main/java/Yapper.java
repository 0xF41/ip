import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
     * An ArrayList to store the Tasks of the user.
     */
    private ArrayList<Task> taskList;

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
     * Displays the bye message before the chatbot is terminated.
     */
    private void bye() {
        System.out.println("Bye. Hope to see you again soon!");
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
                if(!CommandParser.processCommand(cmd, taskList)) break;
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
        this.bye();
        System.out.println("____________________________________________________________");
    }

    /**
     * Constructs a Yapper object representing a yapper chatbot session initiated by a Person. 
     * 
     * @param taskList the Person's task list
     */
    public Yapper(String name, ArrayList<Task> taskList) {
        this.name = name;
        this.taskList = taskList;
    }

    /**
     * Main Function
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Person p1 = new Person();
        Yapper y1 = new Yapper("Yapper", p1.taskList);
        y1.startConversation();
    }
}
