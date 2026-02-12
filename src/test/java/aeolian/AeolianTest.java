package aeolian;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class AeolianTest {
    private Aeolian aeolian;
    private Path tempFile;

    @BeforeEach
    public void setUp(@TempDir Path tempDir) {
        tempFile = tempDir.resolve("aeolian_test.txt");
        aeolian = new Aeolian(tempFile.toString());
    }

    @Test
    public void getGreetings_success() {
        assertTrue(aeolian.getGreetings().contains("Hello! I'm Aeolian"));
    }

    @Test
    public void getResponse_todo_success() {
        String response = aeolian.getResponse("todo read book");
        assertTrue(response.contains("Got it. I've added this task"));
        assertTrue(response.contains("[T][ ] read book"));
    }

    @Test
    public void getResponse_list_success() {
        aeolian.getResponse("todo read book");
        String response = aeolian.getResponse("list");
        assertTrue(response.contains("Here are the tasks in your list:"));
        assertTrue(response.contains("1.[T][ ] read book"));
    }

    @Test
    public void getResponse_mark_success() {
        aeolian.getResponse("todo read book");
        String response = aeolian.getResponse("mark 1");
        assertTrue(response.contains("Nice! I've marked this task as done:"));
        assertTrue(response.contains("[T][X] read book"));
    }

    @Test
    public void getResponse_markInvalidIndex_errorMessage() {
        aeolian.getResponse("todo read book");
        String response = aeolian.getResponse("mark 2");
        assertTrue(response.contains("There is no such task in the list."));

        response = aeolian.getResponse("mark 0");
        assertTrue(response.contains("There is no such task in the list."));

        response = aeolian.getResponse("mark -1");
        assertTrue(response.contains("I don't understand that command.")); // Parser throws for negative
    }

    @Test
    public void getResponse_deleteInvalidIndex_errorMessage() {
        String response = aeolian.getResponse("delete 1");
        assertTrue(response.contains("There is no such task in the list."));
    }

    @Test
    public void getResponse_findNoResult_emptyListMessage() {
        aeolian.getResponse("todo read book");
        String response = aeolian.getResponse("find gym");
        assertTrue(response.contains("Here are the matching tasks in your list:"));
        // The list should be empty, so the header only.
        assertEquals("Here are the matching tasks in your list:", response.trim());
    }

    @Test
    public void getResponse_help_success() {
        String response = aeolian.getResponse("help");
        assertTrue(response.contains("Here are the commands you can use:"));
    }

    @Test
    public void getResponse_bye_savesToFile() throws IOException {
        aeolian.getResponse("todo read book");
        aeolian.getResponse("bye");

        assertTrue(Files.exists(tempFile));
        String content = Files.readString(tempFile);
        assertTrue(content.contains("T | 0 | read book"));
    }
}
