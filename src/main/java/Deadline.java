import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    /**
     * Represents the Deadline of the task
     */
    private LocalDateTime byLocalDateTime; 

    public LocalDateTime getByLocalDateTime() {
        return this.byLocalDateTime;
    }

    /**
     * String representation of a Deadline object. 
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        return String.format("[D]%s (by: %s)", super.toString(), this.byLocalDateTime.format(formatter));
    }

    public Deadline(String description, LocalDateTime byLocalDateTime) {
        super(description);
        this.byLocalDateTime = byLocalDateTime;
    }

}
