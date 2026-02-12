package aeolian;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class EventTest {
    @Test
    public void constructor_validDates_success() throws AeolianException {
        Event event = new Event("project meeting", "2023-10-15", "2023-10-16");
        assertEquals("project meeting", event.getDescription());
        assertEquals("2023-10-15", event.getFrom());
        assertEquals("2023-10-16", event.getTo());
    }

    @Test
    public void constructor_sameDates_success() throws AeolianException {
        Event event = new Event("project meeting", "2023-10-15", "2023-10-15");
        assertEquals("2023-10-15", event.getFrom());
        assertEquals("2023-10-15", event.getTo());
    }

    @Test
    public void constructor_fromAfterTo_throwsException() {
        AeolianException exception = assertThrows(AeolianException.class, () -> {
            new Event("project meeting", "2023-10-16", "2023-10-15");
        });
        assertEquals(" The 'from' date must be before or equal to the 'to' date.", exception.getMessage());
    }
}
