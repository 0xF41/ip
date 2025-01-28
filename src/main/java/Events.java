import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Events extends Task {

    /**
     * Represents the instance of when the Events is started
     */
    private LocalDateTime fromLocalDateTime;

    /**
     * Represents the instance of when the Events is due
     */
    private LocalDateTime toLocalDateTime;

    public LocalDateTime getFromLocalDateTime() {
        return this.fromLocalDateTime;
    }

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
