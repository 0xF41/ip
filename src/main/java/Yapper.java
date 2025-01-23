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

            if (cmd.equals("bye")) { // end conversation

                System.out.println("____________________________________________________________");
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                return;

            }

            if (cmd.equals("list")) { // list tasks

                System.out.println("____________________________________________________________");
                for (int i = 0, n = taskList.size(); i < n; i++) {
                    Task t = taskList.get(i);
                    System.out.println(String.format("%d. %s", i + 1, t));
                }
                System.out.println("____________________________________________________________");

            } else if (cmd.split(" ")[0].equals("mark")) { // mark X

                int idx = Integer.parseInt(cmd.split(" ")[1]) - 1;
                Task t = taskList.get(idx);
                t.markAsDone();
                System.out.println("____________________________________________________________");
                System.out.println("Nice! I've marked this task as done:");
                System.out.println(t);
                System.out.println("____________________________________________________________");

            } else if (cmd.split(" ")[0].equals("unmark")) { // unmark X

                int idx = Integer.parseInt(cmd.split(" ")[1]) - 1;
                Task t = taskList.get(idx);
                t.markAsUndone();
                System.out.println("____________________________________________________________");
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println(t);
                System.out.println("____________________________________________________________");

            } else if (cmd.split(" ")[0].equals("todo")) { // todo X

                ToDos td = new ToDos(cmd.substring("todo".length() + 1));
                taskList.add(td);
                System.out.println("____________________________________________________________");
                System.out.println("Got it. I've added this task:");
                System.out.println(td);
                System.out.println(String.format("Now you have %d tasks in the list.", taskList.size()));
                System.out.println("____________________________________________________________");

            } else if (cmd.split(" ")[0].equals("deadline")) { // deadline X /by Y

                int deadlineIndex = cmd.indexOf("deadline") + 9;
                int byIndex = cmd.indexOf("/by");

                String description = cmd.substring(deadlineIndex, byIndex).trim();
                String dueDate = cmd.substring(byIndex + 4).trim();

                Deadline dl = new Deadline(description, dueDate);
                taskList.add(dl);
                System.out.println("____________________________________________________________");
                System.out.println("Got it. I've added this task:");
                System.out.println(dl);
                System.out.println(String.format("Now you have %d tasks in the list.", taskList.size()));
                System.out.println("____________________________________________________________");

            } else if (cmd.split(" ")[0].equals("event")) { // event X /from Y /to Z

                int eventIndex = cmd.indexOf("event") + 6; // Start after "event "
                int fromIndex = cmd.indexOf("/from");
                int toIndex = cmd.indexOf("/to");

                String description = cmd.substring(eventIndex, fromIndex).trim();
                String fromTime = cmd.substring(fromIndex + 6, toIndex).trim();
                String toTime = cmd.substring(toIndex + 4).trim();

                Events e = new Events(description, fromTime, toTime);
                taskList.add(e);

                System.out.println("____________________________________________________________");
                System.out.println("Got it. I've added this task:");
                System.out.println(e);
                System.out.println(String.format("Now you have %d tasks in the list.", taskList.size()));
                System.out.println("____________________________________________________________");

            } 

        } while (!cmd.equals("bye"));
    }

    public static void main(String[] args) throws IOException {
        Yapper.greet();
        Yapper.startConversation();
    }
}
