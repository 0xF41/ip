import java.util.ArrayList;

public class ListCommand implements Command {

    ArrayList<Task> taskList;

    @Override
    public boolean execute() {
        if (this.taskList.isEmpty()) {
            Ui.printError("List is empty!");
            return false;
        }
        for (int i = 0, n = taskList.size(); i < n; i++) {
            Task t = taskList.get(i);
            Ui.print(String.format("%d. %s", i + 1, t));
        }
        return true;
    }

    public static Command buildListCommand(ArrayList<Task> taskList) {
        return new ListCommand(taskList);
    }

    private ListCommand(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }
}
