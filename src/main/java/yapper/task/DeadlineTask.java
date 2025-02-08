package yapper.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Deadline task.
 */
public class DeadlineTask extends Task {

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
    public DeadlineTask(String description, LocalDateTime byLocalDateTime) {
        super(description);
        this.byLocalDateTime = byLocalDateTime;
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
     * String representation of a Deadline object.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DTF_FORMATTER_STRING);
        return String.format(DEADLINE_INFO_FORMAT_STRING, super.toString(), this.byLocalDateTime.format(formatter));
    }
}
