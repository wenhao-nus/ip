public class Todo extends Task {

    public Todo(String description) throws AeolianException {
        super(description);
        if (description.isEmpty()) {
            throw new AeolianException("Description of Todo cannot be empty!");
        }

    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}