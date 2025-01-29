import java.util.ArrayList;

public class DeleteCommand implements Command {

    private ArrayList<Task> taskList;
    private int idx; 

    @Override
    public boolean execute() {
        Ui.print("Noted. I've removed this task: ");
        Ui.print(taskList.get(idx));
        taskList.remove(idx);
        Ui.print(String.format("Now you have %d tasks in the list.", taskList.size()));
        return true;
    }

    public static Command buildDeleteCommand(ArrayList<Task> taskList, int idx) {
        return new DeleteCommand(taskList, idx);
    }

    private DeleteCommand(ArrayList<Task> taskList, int idx) {
        this.taskList = taskList;
        this.idx = idx;
    }
}
