package aeolian;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {

    @Test
    public void parseTask_todo_success() throws AeolianException {
        Task task = Parser.parseTask("todo read book");
        assertTrue(task instanceof Todo);
        assertEquals("[T][ ] read book", task.toString());
    }

    @Test
    public void parseTask_todoEmptyDescription_exceptionThrown() {
        AeolianException exception = assertThrows(AeolianException.class, () -> {
            Parser.parseTask("todo ");
        });
        assertEquals(" Description of todo cannot be empty!", exception.getMessage());
    }

    @Test
    public void parseTask_deadline_success() throws AeolianException {
        Task task = Parser.parseTask("deadline return book /by 2023-12-01");
        assertTrue(task instanceof Deadline);
        assertEquals("[D][ ] return book (by: Dec 01 2023)", task.toString());
    }

    @Test
    public void parseTask_deadlineMissingBy_exceptionThrown() {
        AeolianException exception = assertThrows(AeolianException.class, () -> {
            Parser.parseTask("deadline return book");
        });
        assertTrue(exception.getMessage().contains("deadline must have /by yyyy-MM-dd"));
    }

    @Test
    public void parseTask_event_success() throws AeolianException {
        Task task = Parser.parseTask("event project meeting /from 2023-12-01 /to 2023-12-02");
        assertTrue(task instanceof Event);
        assertEquals("[E][ ] project meeting (from: Dec 01 2023 to: Dec 02 2023)", task.toString());
    }

    @Test
    public void parseTask_eventMissingInfo_exceptionThrown() {
        assertThrows(AeolianException.class, () -> {
            Parser.parseTask("event project meeting /from 2023-12-01");
        });
        assertThrows(AeolianException.class, () -> {
            Parser.parseTask("event project meeting /to 2023-12-02");
        });
    }

    @Test
    public void parseMarkUnmarkDelete_validInput_success() throws AeolianException {
        assertEquals(0, Parser.parseMarkUnmarkDelete("mark 1"));
        assertEquals(4, Parser.parseMarkUnmarkDelete("unmark 5"));
        assertEquals(9, Parser.parseMarkUnmarkDelete("delete 10"));
    }

    @Test
    public void parseMarkUnmarkDelete_invalidInput_exceptionThrown() {
        assertThrows(AeolianException.class, () -> {
            Parser.parseMarkUnmarkDelete("mark abc");
        });
    }

    @Test
    public void isFindCommand_validInput_success() {
        assertTrue(Parser.isFindCommand("find book"));
        assertTrue(Parser.isFindCommand("find "));
        assertTrue(Parser.isFindCommand("find"));
    }

    @Test
    public void parseFindKeyword_validInput_success() throws AeolianException {
        assertEquals("book", Parser.parseFindKeyword("find book"));
        assertEquals("read book", Parser.parseFindKeyword("find  read book "));
    }

    @Test
    public void parseFindKeyword_emptyKeyword_exceptionThrown() {
        assertThrows(AeolianException.class, () -> {
            Parser.parseFindKeyword("find");
        });
        assertThrows(AeolianException.class, () -> {
            Parser.parseFindKeyword("find   ");
        });
    }
}
