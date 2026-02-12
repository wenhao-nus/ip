package aeolian;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
    }

    @Test
    public void addTask_success() {
        Task todo = new Todo("read book");
        taskList.addTask(todo);
        assertEquals(1, taskList.getNumberOfTasks());
        assertEquals(todo, taskList.getTask(0));
    }

    @Test
    public void getTask_invalidIndex_assertionError() {
        // Assertions are enabled in tests by default in this environment usually,
        // but let's check if we can catch it.
        // Note: JUnit 5 doesn't always handle java.lang.AssertionError nicely if not expected.
        assertThrows(AssertionError.class, () -> {
            taskList.getTask(0);
        });
    }

    @Test
    public void removeTask_success() {
        Task todo = new Todo("read book");
        taskList.addTask(todo);
        taskList.removeTask(todo);
        assertEquals(0, taskList.getNumberOfTasks());
    }

    @Test
    public void findTasks_matchingKeyword_success() {
        taskList.addTask(new Todo("read book"));
        taskList.addTask(new Todo("write essay"));
        taskList.addTask(new Todo("book club"));

        TaskList result = taskList.findTasks("book");
        assertEquals(2, result.getNumberOfTasks());
        assertEquals("read book", result.getTask(0).getDescription());
        assertEquals("book club", result.getTask(1).getDescription());
    }

    @Test
    public void findTasks_emptyKeyword_matchesAll() {
        taskList.addTask(new Todo("read book"));
        taskList.addTask(new Todo("write essay"));
        TaskList result = taskList.findTasks("");
        assertEquals(2, result.getNumberOfTasks());
    }

    @Test
    public void removeTask_nonExistent_noChange() {
        Task todo1 = new Todo("read book");
        Task todo2 = new Todo("write essay");
        taskList.addTask(todo1);
        taskList.removeTask(todo2);
        assertEquals(1, taskList.getNumberOfTasks());
        assertEquals(todo1, taskList.getTask(0));
    }
}
