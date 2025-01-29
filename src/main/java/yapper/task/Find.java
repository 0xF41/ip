package yapper.task;

public class Find extends Task {

    /**
     * String representation of Find
     */
    @Override
    public String toString() {
        return String.format("[F]%s", super.toString());
    }

    /**
     * Constructs a Find object.
     *
     * @param description Description of the Find task.
     */
    public Find(String description) {
        super(description);
    }
}
