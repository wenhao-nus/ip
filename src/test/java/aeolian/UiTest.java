package aeolian;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UiTest {
    @Test
    public void testShowGreetings() {
        Ui ui = new Ui();
        assertEquals("Hello! I'm Aeolian\nWhat can I do for you?", ui.showGreetings());
    }

    @Test
    public void testShowGoodbye() {
        Ui ui = new Ui();
        assertEquals("Bye. Hope to see you again soon!", ui.showGoodbye());
    }

    @Test
    public void testShowAddTaskSuccess() {
        Ui ui = new Ui();
        Task task = new Todo("test task");
        TaskList taskList = new TaskList();
        taskList.addTask(task);
        String expected = " Got it. I've added this task:\n"
                + "   [T][ ] test task\n"
                + " Now you have 1 tasks in the list.";
        assertEquals(expected, ui.showAddTaskSuccess(task, taskList));
    }

    @Test
    public void testShowAllTasks() {
        Ui ui = new Ui();
        TaskList taskList = new TaskList();
        taskList.addTask(new Todo("task 1"));
        taskList.addTask(new Todo("task 2"));
        String expected = "Here are the tasks in your list:\n"
                + " 1.[T][ ] task 1\n"
                + " 2.[T][ ] task 2";
        assertEquals(expected, ui.showAllTasks(taskList));
    }

    @Test
    public void testShowException() {
        Ui ui = new Ui();
        assertEquals("test error", ui.showException(new AeolianException("test error")));
        assertEquals("IO error.", ui.showException(new java.io.IOException()));
        assertEquals("An error has occurred.", ui.showException(new Exception()));
    }
}
