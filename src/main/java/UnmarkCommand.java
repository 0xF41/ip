import java.util.ArrayList;

public class UnmarkCommand implements Command {

    private ArrayList<Task> taskList;
    private int idx; 

    @Override
    public boolean execute() {
        Task t = taskList.get(idx);
        t.markAsUndone();
        Ui.print("OK, I've marked this task as not done yet:");
        Ui.print(t);
        return true;
    }

    public static Command buildUnmarkCommand(ArrayList<Task> taskList, int idx) {
        return new UnmarkCommand(taskList, idx);
    }

    private UnmarkCommand(ArrayList<Task> taskList, int idx) {
        this.taskList = taskList;
        this.idx = idx;
    }
}
