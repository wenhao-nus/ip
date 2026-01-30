import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {

    private final LocalDate by;

    public Deadline(String description, String by) throws AeolianException {
        super(description);
        try {
            this.by = LocalDate.parse(by.trim()); // expects yyyy-MM-dd
        } catch (DateTimeParseException e) {
            throw new AeolianException(" Invalid date format! Use yyyy-MM-dd (e.g., 2019-10-15).");
        }
    }

    public String getBy() {
        return this.by.toString();
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by.format(OUTPUT_FORMAT) + ")";
    }
}