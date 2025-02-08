package yapper.task;

import java.time.LocalDateTime;

public abstract class ScheduleTask extends Task {
    /**
     * Reschedules the task to a new date and time.
     *
     * @param newDateTime New date and time for the task.
     * @return The rescheduled task.
     */
    public ScheduleTask reschedule(LocalDateTime... newDateTime) {
        assert newDateTime.length == 1 : "Only one new date and time should be provided.";
        return null;
    }

    /**
     * Constructs a ScheduableTask object.
     *
     * @param description Description of the ScheduableTask.
     */
    public ScheduleTask(String description) {
        super(description);
    }
}
