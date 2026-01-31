package aeolian;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeadlineTest {

    @Test
    public void constructor_validDate_success() throws AeolianException {
        Deadline deadline = new Deadline("return book", "2023-12-01");
        assertEquals("return book", deadline.getDescription());
        assertEquals("2023-12-01", deadline.getBy());
    }

    @Test
    public void constructor_invalidDateFormat_exceptionThrown() {
        AeolianException exception = assertThrows(AeolianException.class, () -> {
            new Deadline("return book", "Dec 01 2023");
        });
        assertEquals(" Invalid date format! Use yyyy-MM-dd (e.g., 2019-10-15).", exception.getMessage());
    }

    @Test
    public void toString_validDate_success() throws AeolianException {
        Deadline deadline = new Deadline("return book", "2023-12-01");
        assertEquals("[D][ ] return book (by: Dec 01 2023)", deadline.toString());
    }

    @Test
    public void markAsDone_success() throws AeolianException {
        Deadline deadline = new Deadline("return book", "2023-12-01");
        deadline.markAsDone();
        assertEquals("[D][X] return book (by: Dec 01 2023)", deadline.toString());
    }

    @Test
    public void constructor_invalidDateRange_exceptionThrown() throws AeolianException {
        AeolianException exception = assertThrows(AeolianException.class, () -> {
            new Deadline("return book", "2025-55-55");
        });
        assertEquals(" Invalid date format! Use yyyy-MM-dd (e.g., 2019-10-15).", exception.getMessage());
    }
}
