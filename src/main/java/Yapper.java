import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Yapper {

    /**
     * BufferedReader object to read input from user.
     */
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));;

    /**
     * Name of the chatbot
     */
    public static final String NAME = "Yapper";

    /**
     * An ArrayList to store the Tasks of the user.
     */
    private static ArrayList<Task> taskList = new ArrayList<>();

    /**
     * Displays a default message when the chatbot is started.
     */
    private static void greet() {
        System.out.println("____________________________________________________________");
        System.out.println(String.format("Hello! I'm %s!", Yapper.NAME));
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");

    }

    /**
     * Displays the bye message before the chatbot is terminated.
     */
    private static void bye() {
        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }

    /**
     * Starts the conversation between the user and the chatbot. 
     * 
     * @throws IOException
     */
    private static void startConversation() throws IOException {
        Yapper.greet();
        String cmd = "";
        do {
            cmd = br.readLine();
            try {
                CommandParser.processCommand(cmd, taskList);
            } catch (InvalidCommandSyntaxException e) {
                System.out.println(e);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(e);
            }
        } while (!cmd.equals("bye"));
        Yapper.bye();
    }

    /**
     * Main Function
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Yapper.startConversation();
    }
}
