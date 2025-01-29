package yapper.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Events task.
 */
public class Events extends Task {

    /**
     * Represents the instance of when the Events is started
     */
    private LocalDateTime fromLocalDateTime;

    /**
     * Represents the instance of when the Events is due
     */
    private LocalDateTime toLocalDateTime;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HHmm");
        return String.format("[E]%s (from: %s to: %s)", super.toString(), this.fromLocalDateTime.format(formatter),
                this.toLocalDateTime.format(formatter));
    }

    public Events(String description, LocalDateTime fromLocalDateTime, LocalDateTime toLocalDateTime) {
        super(description);
        this.fromLocalDateTime = fromLocalDateTime;
        this.toLocalDateTime = toLocalDateTime;
    }
}
