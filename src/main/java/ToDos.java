public class ToDos extends Task {
    
    /**
     * String representation of ToDos
     */
    @Override
    public String toString() {
        return String.format("[T]%s", super.toString());
    }

    /**
     * Constructs a new ToDos object. Todos <: Task
     * @param description description of the ToDos object
     */
    public ToDos(String description) {
        super(description);
    }
}
