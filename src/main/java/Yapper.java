import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Yapper {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));; 

    public static final String NAME = "Yapper"; 

    public static ArrayList<String> items = new ArrayList<>();

    private static void greet() {
        System.out.println("____________________________________________________________");
        System.out.println(String.format("Hello! I'm %s!", Yapper.NAME));
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");
        
    }   

    private static void startConversation() throws IOException {

        String cmd = "";
        do {
            cmd = br.readLine();

            if (cmd.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                return;
            }  
            if (cmd.equals("list")) {
                System.out.println("____________________________________________________________");
                for (int i = 0, n = items.size(); i < n; i++) {
                    System.out.println(String.format("%d. %s", i + 1, items.get(i)));
                }
                System.out.println("____________________________________________________________");
            } else {
                items.add(cmd);
                System.out.println("____________________________________________________________");
                System.out.println(String.format("Added: %s", cmd));
                System.out.println("____________________________________________________________");
            }

        } while (!cmd.equals("bye"));
    }

    public static void main(String[] args) throws IOException {
        Yapper.greet();
        Yapper.startConversation();
    }
}
