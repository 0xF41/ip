package yapper.data.task;

import java.time.LocalDateTime;

/**
 * Represents a task that can be scheduled.
 */
public abstract class ScheduleTask extends Task {

    /**
     * Constructs a ScheduableTask object.
     *
     * @param description Description of the ScheduableTask.
     */
    public ScheduleTask(String description) {
        super(description);
    }

    /**
     * Reschedules the task to a new date and time.
     *
     * @param newDateTime New date and time for the task.
     * @return The rescheduled task.
     */
    public ScheduleTask reschedule(LocalDateTime... newDateTime) {
        assert false : "This method should be overridden.";
        return null;
    }
}
