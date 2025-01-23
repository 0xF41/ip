public class Events extends Task {

    /**
     * Represents the instance of when the Events is started
     */
    protected String from;

    /**
     * Represents the instance of when the Events is due
     */
    protected String to;

    /**
     * String representation of a Events.
     */
    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)", super.toString(), this.from, this.to);
    }

    /**
     * Constructs a Events object. Events <: Task
     * 
     * @param description description of the Events object
     * @param from start time of an Events
     * @param to end time of an Events
     */
    public Events(String description, String from, String to) {
        this.from = from;
        this.to = to;
        super(description);
    }
}
