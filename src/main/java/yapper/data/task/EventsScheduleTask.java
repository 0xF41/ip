package yapper.data.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Events task.
 */
public class EventsScheduleTask extends ScheduleTask {

    // Constants
    private static final String ASSERT_NEW_DATE_TIME_STRING = "Only two new date and time should be provided.";
    private static final String EVENTS_INFO_FORMAT_STRING = "[E]%s (from: %s to: %s)";
    private static final String DTF_FORMATTER_STRING = "dd-MMM-yyyy HHmm";

    /**
     * Represents the instance of when the Events is started
     */
    private LocalDateTime fromLocalDateTime;

    /**
     * Represents the instance of when the Events is due
     */
    private LocalDateTime toLocalDateTime;

    /**
     * Constructs an Events object.
     *
     * @param description       Description of the Events task.
     * @param fromLocalDateTime Instance of when the Events is started
     * @param toLocalDateTime   Instance of when the Events is due
     */
    public EventsScheduleTask(String description, LocalDateTime fromLocalDateTime, LocalDateTime toLocalDateTime) {
        super(description);
        this.fromLocalDateTime = fromLocalDateTime;
        this.toLocalDateTime = toLocalDateTime;
    }

    /**
     * Returns the instance of when the Events is started
     *
     * @return LocalDateTime instance of when the Events is started
     */
    public LocalDateTime getFromLocalDateTime() {
        return this.fromLocalDateTime;
    }

    /**
     * Returns the instance of when the Events is due
     *
     * @return LocalDateTime instance of when the Events is due
     */
    public LocalDateTime getToLocalDateTime() {
        return this.toLocalDateTime;
    }

    /**
     * String representation of an Events
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DTF_FORMATTER_STRING);
        return String.format(
                EVENTS_INFO_FORMAT_STRING,
                super.toString(),
                this.fromLocalDateTime.format(formatter),
                this.toLocalDateTime.format(formatter));
    }

    /**
     * Reschedules the task to a new date and time.
     *
     * @param newDateTime New date and time for the task.
     * @return The rescheduled task.
     */
    @Override
    public ScheduleTask reschedule(LocalDateTime... newDateTime) {
        assert newDateTime.length == 2 : ASSERT_NEW_DATE_TIME_STRING;
        return new EventsScheduleTask(
                this.getDescription(),
                newDateTime[0],
                newDateTime[1]);
    }
}
