public class ToDos extends Task {
    
    @Override
    public String toString() {
        return String.format("[T]%s", super.toString());
    }

    public ToDos(String description) {
        super(description);
    }
}
