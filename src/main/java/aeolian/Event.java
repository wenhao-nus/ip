package aeolian;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Event extends Task {

    private final LocalDate from;
    private final LocalDate to;

    public Event(String description, String from, String to) throws AeolianException {
        super(description);

        try {
            this.from = LocalDate.parse(from.trim()); // yyyy-MM-dd
            this.to = LocalDate.parse(to.trim());     // yyyy-MM-dd
        } catch (DateTimeParseException e) {
            throw new AeolianException(" Invalid date format! Use yyyy-MM-dd (e.g., 2019-10-15).");
        }
    }

    public String getTo() {
        return this.to.toString();
    }

    public String getFrom() {
        return this.from.toString();
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from.format(OUTPUT_FORMAT)
                + " to: " + this.to.format(OUTPUT_FORMAT) + ")";
    }
}