package aeolian;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents an event task with a start and end date.
 */
public class Event extends Task {

    private final LocalDate from;
    private final LocalDate to;

    /**
     * Constructs an Event task with the given description, start date, and end date.
     *
     * @param description Description of the event.
     * @param from Start date in yyyy-MM-dd format.
     * @param to End date in yyyy-MM-dd format.
     * @throws AeolianException If the date format is invalid.
     */
    public Event(String description, String from, String to) throws AeolianException {
        super(description);

        try {
            this.from = LocalDate.parse(from.trim()); // yyyy-MM-dd
            this.to = LocalDate.parse(to.trim());     // yyyy-MM-dd
        } catch (DateTimeParseException e) {
            throw new AeolianException(" Invalid date format! Use yyyy-MM-dd (e.g., 2019-10-15).");
        }
    }

    /**
     * Returns the end date as a string.
     *
     * @return End date in yyyy-MM-dd format.
     */
    public String getTo() {
        return this.to.toString();
    }

    /**
     * Returns the start date as a string.
     *
     * @return Start date in yyyy-MM-dd format.
     */
    public String getFrom() {
        return this.from.toString();
    }

    /**
     * Returns a string representation of the event task.
     *
     * @return String representation.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from.format(OUTPUT_FORMAT)
                + " to: " + this.to.format(OUTPUT_FORMAT) + ")";
    }
}