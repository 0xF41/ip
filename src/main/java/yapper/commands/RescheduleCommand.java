package yapper.commands;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import yapper.data.task.ScheduleTask;
import yapper.data.task.Task;

/**
 * Represents a command to reschedule a task.
 */
public class RescheduleCommand implements Command {

    private static final String ASSERT_TASK_IS_NULL_STRING = "Rescheduled task should not be null.";
    private static final String EXECUTE_INFO_STRING = "Task has been rescheduled!";
    private static final String DTF_FORMATTER_STRING = "dd-MM-yyyy HHmm";

    private static final int RESCHEDULE_COMMAND_MAX_DTL_ARGS = 2;

    /**
     * List of a Person's current tasks.
     */
    private ArrayList<Task> taskList;

    /**
     * Index of the task to be rescheduled.
     */
    private int idx;

    /**
     * New date and time for the task.
     */
    private String[] newDateTimeString;

    /**
     * Constructs a RescheduleCommand object.
     *
     * @param taskList          List of a Person's current tasks.
     * @param idx               Index of the task to be rescheduled.
     * @param newDateTimeString New date and time for the task.
     * @return RescheduleCommand object.
     */
    private RescheduleCommand(ArrayList<Task> taskList, int idx, String... newDateTimeString) {
        this.taskList = taskList;
        this.idx = idx;
        this.newDateTimeString = newDateTimeString;
    }

    /**
     * Builds a RescheduleCommand object.
     *
     * @param taskList          List of a Person's current tasks.
     * @param idx               Index of the task to be rescheduled.
     * @param newDateTimeString New date and time for the task.
     * @return RescheduleCommand object.
     */
    public static Command buildRescheduleCommand(ArrayList<Task> taskList, int idx, String... newDateTimeString) {
        return new RescheduleCommand(taskList, idx, newDateTimeString);
    }

    /**
     * Executes the command to reschedule a task.
     * date and time and replaces the old task with the new task.
     *
     * @param responseList List of responses to be displayed to the user.
     * @return True if the command is successfully executed, false otherwise.
     */
    @Override
    public boolean execute(ArrayList<String> responseList) {

        ScheduleTask oldTask = (ScheduleTask) this.taskList.get(this.idx);
        LocalDateTime[] newDateTime = new LocalDateTime[RESCHEDULE_COMMAND_MAX_DTL_ARGS];

        for (int i = 0; i < newDateTimeString.length; i++) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DTF_FORMATTER_STRING);
            newDateTime[i] = LocalDateTime.parse(this.newDateTimeString[i], formatter);
        }

        ScheduleTask rescheduledTask = oldTask.reschedule(newDateTime);

        assert rescheduledTask != null : ASSERT_TASK_IS_NULL_STRING;

        this.taskList.set(this.idx, rescheduledTask);
        responseList.add(EXECUTE_INFO_STRING);
        responseList.add(rescheduledTask.toString());

        return true;
    }
}
