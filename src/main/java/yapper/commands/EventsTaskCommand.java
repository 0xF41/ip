package yapper.commands;

import java.util.ArrayList;

import yapper.task.EventsTask;
import yapper.task.Task;

/**
 * Represents a command to add an EventsTask task.
 */
public class EventsTaskCommand implements TaskCommand {

    private static final String ASSERT_TASK_LIST_NEGATIVE_STRING = "Task list should not be negative.";
    private static final String ASSERT_EVENTS_TASK_NOT_NULL_STRING = "EventsTask should not be null.";

    /**
     * List of a Person's current tasks.
     */
    private ArrayList<Task> taskList;

    /**
     * EventsTask task to be added.
     */
    private EventsTask ev;

    /**
     * Constructs an EventsCommand object.
     *
     * @param taskList List of a Person's current tasks.
     * @param ev       EventsTask task to be added.
     */
    private EventsTaskCommand(ArrayList<Task> taskList, EventsTask ev) {
        this.taskList = taskList;
        this.ev = ev;
    }

    /**
     * Executes the command to add an EventsTask task.
     *
     * @return true to indicate the chatbot conversation should continue.
     */
    @Override
    public boolean execute(ArrayList<String> responseList) {
        taskList.add(ev);
        assert taskList.size() >= 0 : ASSERT_TASK_LIST_NEGATIVE_STRING;
        responseList.add("Got it. I've added this task:");
        responseList.add(ev.toString());
        responseList.add(String.format("Now you have %d tasks in the list.", taskList.size()));
        return true;
    }

    /**
     * Returns the description of the EventsTask task.
     *
     * @return Description of the EventsTask task.
     */
    @Override
    public String getTaskDescription() {
        return ev.getDescription();
    }

    /**
     * Builds an EventsCommand object.
     *
     * @param taskList List of a Person's current tasks.
     * @param ev       EventsTask task to be added.
     * @return EventsCommand object.
     */
    public static Command buildEventsCommand(ArrayList<Task> taskList, EventsTask ev) {
        assert ev != null : ASSERT_EVENTS_TASK_NOT_NULL_STRING;
        return new EventsTaskCommand(taskList, ev);
    }
}
