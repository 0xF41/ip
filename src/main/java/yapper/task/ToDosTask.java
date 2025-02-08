package yapper.task;

/**
 * Represents a ToDos task.
 */
public class ToDosTask extends Task {

    /**
     * Constructs a ToDos object.
     *
     * @param description Description of the ToDos task.
     */
    public ToDosTask(String description) {
        super(description);
    }

    /**
     * String representation of ToDos
     */
    @Override
    public String toString() {
        return String.format("[T]%s", super.toString());
    }
}
