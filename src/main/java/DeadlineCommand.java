import java.util.ArrayList;

public class DeadlineCommand implements Command {

    private ArrayList<Task> taskList;
    private Deadline dl;

    @Override
    public boolean execute() {
        taskList.add(dl);
        Ui.print("Got it. I've added this task:");
        Ui.print(dl);
        Ui.print(String.format("Now you have %d tasks in the list.", taskList.size()));
        return true;
    }

    public static Command buildDeadlineCommand(ArrayList<Task> taskList, Deadline dl) {
        return new DeadlineCommand(taskList, dl);
    }

    private DeadlineCommand(ArrayList<Task> taskList, Deadline dl) {
        this.taskList = taskList;
        this.dl = dl;
    }
}
