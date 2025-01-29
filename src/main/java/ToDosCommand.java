import java.util.ArrayList;

public class ToDosCommand implements Command {

    ArrayList<Task> taskList;
    
    ToDos td;

    @Override
    public boolean execute() {
        taskList.add(td);
        Ui.print("Got it. I've added this task:");
        Ui.print(td);
        Ui.print(String.format("Now you have %d tasks in the list.", taskList.size()));
        return true;
    }

    public static Command buildToDosCommand(ArrayList<Task> taskList, ToDos td) {
        return new ToDosCommand(taskList, td);
    }

    private ToDosCommand(ArrayList<Task> taskList, ToDos td) {
        this.taskList = taskList;
        this.td = td;
    }
}
