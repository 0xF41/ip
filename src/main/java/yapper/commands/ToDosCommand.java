package yapper.commands;

import java.util.ArrayList;

import yapper.task.Task;
import yapper.task.ToDos;
import yapper.ui.Ui;

public class ToDosCommand implements Command {

    private ArrayList<Task> taskList;
    private ToDos td;

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
