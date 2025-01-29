import java.util.ArrayList;

public class EventsCommand implements Command {

    private ArrayList<Task> taskList;
    private Events ev;

    @Override
    public boolean execute() {
        taskList.add(ev);
        Ui.print("Got it. I've added this task:");
        Ui.print(ev);
        Ui.print(String.format("Now you have %d tasks in the list.", taskList.size()));
        return true;
    }

    public static Command buildEventsCommand(ArrayList<Task> taskList, Events ev) {
        return new EventsCommand(taskList, ev);
    }

    private EventsCommand(ArrayList<Task> taskList, Events ev) {
        this.taskList = taskList;
        this.ev = ev;
    }
}
