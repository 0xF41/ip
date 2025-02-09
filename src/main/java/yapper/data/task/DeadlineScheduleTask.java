package yapper.data.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Deadline task.
 */
public class DeadlineScheduleTask extends ScheduleTask {

    private static final String ASSERT_NEW_DATE_TIME_STRING = "Only one new date and time should be provided.";

    private static final String DEADLINE_INFO_FORMAT_STRING = "[D]%s (by: %s)";
    private static final String DTF_FORMATTER_STRING = "dd-MMM-yyyy";
    /**
     * Represents the Deadline of the task
     */
    private LocalDateTime byLocalDateTime;

    /**
     * Constructs a Deadline object.
     *
     * @param description     Description of the Deadline task.
     * @param byLocalDateTime Deadline of the task.
     */
    public DeadlineScheduleTask(String description, LocalDateTime byLocalDateTime) {
        super(description);
        this.byLocalDateTime = byLocalDateTime;
    }

    /**
     * String representation of a Deadline object.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DTF_FORMATTER_STRING);
        return String.format(DEADLINE_INFO_FORMAT_STRING, super.toString(), this.byLocalDateTime.format(formatter));
    }

    /**
     * Gets the Deadline of the task.
     *
     * @return Deadline of the task.
     */
    public LocalDateTime getByLocalDateTime() {
        return this.byLocalDateTime;
    }

    /**
     * Reschedules the task to a new date and time.
     *
     * @param newDateTime New date and time for the task.
     * @return The rescheduled task.
     */
    @Override
    public ScheduleTask reschedule(LocalDateTime... newDateTime) {
        assert newDateTime.length == 1 : ASSERT_NEW_DATE_TIME_STRING;
        return new DeadlineScheduleTask(this.getDescription(), newDateTime[0]);
    }
}
