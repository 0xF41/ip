package yapper.task;

/**
 * Represents a ToDos task.
 */
public class ToDosTask extends Task {

    private static final String TODOS_INFO_FORMAT_STRING = "[T]%s";

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
        return String.format(TODOS_INFO_FORMAT_STRING, super.toString());
    }
}
