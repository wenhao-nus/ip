package aeolian;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class StorageTest {
    @Test
    public void toFileFormat_todo_success() {
        Todo todo = new Todo("read book");
        assertEquals("T | 0 | read book", todo.toFileFormat());
        todo.markAsDone();
        assertEquals("T | 1 | read book", todo.toFileFormat());
    }

    @Test
    public void toFileFormat_deadline_success() throws AeolianException {
        Deadline deadline = new Deadline("return book", "2023-10-15");
        assertEquals("D | 0 | return book | 2023-10-15", deadline.toFileFormat());
    }

    @Test
    public void toFileFormat_event_success() throws AeolianException {
        Event event = new Event("project meeting", "2023-10-15", "2023-10-16");
        assertEquals("E | 0 | project meeting | 2023-10-15 | 2023-10-16", event.toFileFormat());
    }

    @Test
    public void load_nonExistentFile_returnsEmptyTaskList(@TempDir Path tempDir) {
        Path nonExistent = tempDir.resolve("non_existent.txt");
        Storage storage = new Storage(nonExistent.toString());
        assertEquals(0, storage.getTaskList().getNumberOfTasks());
    }

    @Test
    public void load_fileWithValidAndCorruptedLines_returnsPartiallyFilledTaskList(@TempDir Path tempDir)
            throws IOException {
        Path file = tempDir.resolve("tasks.txt");
        String content = "T | 1 | read book\n"
                + "invalid line\n"
                + "E | 0 | meeting | 2023-10-15 | 2023-10-14\n" // invalid dates
                + "D | 0 | return book | 2023-10-15\n";
        Files.writeString(file, content);

        Storage storage = new Storage(file.toString());
        TaskList taskList = storage.getTaskList();

        // Should have 'read book' and 'return book'. Corrupted lines and invalid dates should be skipped.
        assertEquals(2, taskList.getNumberOfTasks());
        assertEquals("read book", taskList.getTask(0).getDescription());
        assertTrue(taskList.getTask(0).isDone());
        assertEquals("return book", taskList.getTask(1).getDescription());
    }
}
