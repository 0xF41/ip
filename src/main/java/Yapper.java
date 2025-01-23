import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Yapper {

    public static final String NAME = "Yapper"; 

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));; 

    public static enum COMMANDS {LIST, BLAH, BYE}


    private static void greet() {
        System.out.println("____________________________________________________________");
        System.out.println(String.format("Hello! I'm %s!", Yapper.NAME));
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");
        
    }   

    private static void startConversation() throws IOException {
        String cmd = "";
        do {
            String[] tokens = br.readLine().split(" ");
            cmd = tokens[0];
            if (!cmd.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println(cmd);
                System.out.println("____________________________________________________________");
            } else {
                System.out.println("____________________________________________________________");
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
            }
        } while (!cmd.equals("bye"));
    }

    public static void main(String[] args) throws IOException {
        Yapper.greet();
        Yapper.startConversation();
    }
}
