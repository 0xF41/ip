public class Deadline extends Task {

    /**
     * Represents the instance of when the Deadline is due
     */
    protected String by;

    public String getBy() {
        return this.by;
    }

    /**
     * String representation of a Deadline object. 
     */
    @Override
    public String toString() {
        // return "[D]" + super.toString() + " (by: " + by + ")";
        return String.format("[D]%s (by: %s)", super.toString(), this.by);
    }

    /**
     * Constructs a Deadline object. Deadline <: Task
     * @param description description of the Deadline object
     * @param by represent the instance of when the deadline is due
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

}
