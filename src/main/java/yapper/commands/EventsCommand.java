package yapper.commands;

import java.util.ArrayList;

import yapper.task.Events;
import yapper.task.Task;
import yapper.ui.Ui;

/**
 * Represents a command to add an Events task.
 */
public class EventsCommand implements Command {

    /**
     * List of a Person's current tasks.
     */
    private ArrayList<Task> taskList;

    /**
     * Events task to be added.
     */
    private Events ev;

    /**
     * Executes the command to add an Events task.
     *
     * @return true to indicate the chatbot conversation should continue.
     */
    @Override
    public boolean execute() {
        taskList.add(ev);
        Ui.print("Got it. I've added this task:");
        Ui.print(ev);
        Ui.print(String.format("Now you have %d tasks in the list.", taskList.size()));
        return true;
    }

    /**
     * Builds an EventsCommand object.
     *
     * @param taskList List of a Person's current tasks.
     * @param ev       Events task to be added.
     * @return EventsCommand object.
     */
    public static Command buildEventsCommand(ArrayList<Task> taskList, Events ev) {
        return new EventsCommand(taskList, ev);
    }

    /**
     * Constructs an EventsCommand object.
     *
     * @param taskList List of a Person's current tasks.
     * @param ev       Events task to be added.
     */
    private EventsCommand(ArrayList<Task> taskList, Events ev) {
        this.taskList = taskList;
        this.ev = ev;
    }
}
