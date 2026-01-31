package aeolian;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {
    private final LocalDate by;

    /**
     * Constructs a Deadline task with the given description and deadline date.
     *
     * @param description Description of the task.
     * @param by Deadline date in yyyy-MM-dd format.
     * @throws AeolianException If the date format is invalid.
     */
    public Deadline(String description, String by) throws AeolianException {
        super(description);
        try {
            this.by = LocalDate.parse(by.trim()); // expects yyyy-MM-dd
        } catch (DateTimeParseException e) {
            throw new AeolianException(" Invalid date format! Use yyyy-MM-dd (e.g., 2019-10-15).");
        }
    }

    /**
     * Returns the deadline date as a string.
     *
     * @return Deadline date in yyyy-MM-dd format.
     */
    public String getBy() {
        return this.by.toString();
    }

    /**
     * Returns a string representation of the deadline task.
     *
     * @return String representation.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by.format(OUTPUT_FORMAT) + ")";
    }
}