package yapper.commands;

import java.util.ArrayList;

import yapper.task.Task;
import yapper.ui.Ui;

public class MarkCommand implements Command {

    private ArrayList<Task> taskList;
    private int idx;

    @Override
    public boolean execute() {
        Task t = this.taskList.get(this.idx);
        t.markAsDone();
        Ui.print("Nice! I've marked this task as done:");
        Ui.print(t);
        return true;
    }

    public static Command buildMarkCommand(ArrayList<Task> taskList, int idx) {
        return new MarkCommand(taskList, idx);
    }

    private MarkCommand(ArrayList<Task> taskList, int idx) {
        this.taskList = taskList;
        this.idx = idx;
    }
}
