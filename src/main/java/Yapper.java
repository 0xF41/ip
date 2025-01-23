import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Yapper {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));;

    public static final String NAME = "Yapper";

    public static ArrayList<Task> taskList = new ArrayList<>();

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
                for (int i = 0, n = taskList.size(); i < n; i++) {
                    System.out.println(String.format("%d.[%s] %s", i + 1, taskList.get(i).getStatusIcon(),
                            taskList.get(i).getDescription()));
                }
                System.out.println("____________________________________________________________");

            } else if (cmd.split(" ")[0].equals("mark")) {

                int idx = Integer.parseInt(cmd.split(" ")[1]) - 1;
                Task t = taskList.get(idx);
                t.markAsDone();
                System.out.println("____________________________________________________________");
                System.out.println("Nice! I've marked this task as done:");
                System.out.println(String.format("[%s] %s", t.getStatusIcon(), t.getDescription()));
                System.out.println("____________________________________________________________");

            } else if (cmd.split(" ")[0].equals("unmark")) {

                int idx = Integer.parseInt(cmd.split(" ")[1]) - 1;
                Task t = taskList.get(idx);
                t.markAsUndone();
                System.out.println("____________________________________________________________");
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println(String.format("[%s] %s", t.getStatusIcon(), t.getDescription()));
                System.out.println("____________________________________________________________");

            } else {

                Task t = new Task(cmd);
                taskList.add(t);
                System.out.println("____________________________________________________________");
                System.out.println(String.format("Added: %s", t.getDescription()));
                System.out.println("____________________________________________________________");

            }

        } while (!cmd.equals("bye"));
    }

    public static void main(String[] args) throws IOException {
        Yapper.greet();
        Yapper.startConversation();
    }
}
