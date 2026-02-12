package aeolian;

import java.io.IOException;

/**
 * Deals with output interactions with the user.
 */
public class Ui {

    /**
     * Displays a greeting message to the user.
     *
     * @return Greeting message.
     */
    public String showGreetings() {
        return "Hello! I'm Aeolian\nWhat can I do for you?";
    }

    /**
     * Displays a goodbye message to the user.
     *
     * @return Goodbye message.
     */
    public String showGoodbye() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Displays an error message based on the exception encountered.
     *
     * @param e The exception that occurred.
     * @return Error message.
     */
    public String showException(Exception e) {
        assert e != null : "Exception cannot be null";
        if (e instanceof AeolianException) {
            return e.getMessage();
        }
        if (e instanceof IOException) {
            return "IO error.";
        }
        return "An error has occurred.";
    }

    /**
     * Displays all tasks in the task list.
     *
     * @param taskList The list of tasks to be displayed.
     * @return List of all tasks.
     */
    public String showAllTasks(TaskList taskList) {
        return formatTaskList(" Here are the tasks in your list:\n", taskList);
    }

    /**
     * Displays a success message after adding a task.
     *
     * @param newTask The task that was added.
     * @param taskList The updated task list.
     * @return Success message.
     */
    public String showAddTaskSuccess(Task newTask, TaskList taskList) {
        assert newTask != null : "Task cannot be null";
        assert taskList != null : "TaskList cannot be null";
        return String.format(" Got it. I've added this task:\n   %s\n Now you have %d tasks in the list.",
                newTask, taskList.getNumberOfTasks());
    }

    /**
     * Displays a success message after marking a task as done.
     *
     * @param chosenTask The task that was marked.
     * @return Success message.
     */
    public String showMarkTaskSuccess(Task chosenTask) {
        assert chosenTask != null : "Task cannot be null";
        return String.format(" Nice! I've marked this task as done:\n   %s", chosenTask);
    }

    /**
     * Displays a success message after unmarking a task as done.
     *
     * @param chosenTask The task that was unmarked.
     * @return Success message.
     */
    public String showUnmarkTaskSuccess(Task chosenTask) {
        assert chosenTask != null : "Task cannot be null";
        return String.format(" OK, I've marked this task as not done yet:\n   %s", chosenTask);
    }

    /**
     * Displays a success message after deleting a task.
     *
     * @param chosenTask The task that was deleted.
     * @param taskList The updated task list.
     * @return Success message.
     */
    public String showDeleteTaskSuccess(Task chosenTask, TaskList taskList) {
        assert chosenTask != null : "Task cannot be null";
        assert taskList != null : "TaskList cannot be null";
        return String.format(" Noted. I've removed this task:\n   %s\n Now you have %d tasks in the list.",
                chosenTask, taskList.getNumberOfTasks());
    }

    /**
     * Displays the tasks that match a search keyword.
     *
     * @param matchingTasks The list of tasks that matched the search.
     * @return List of matching tasks.
     */
    public String showMatchingTasks(TaskList matchingTasks) {
        return formatTaskList(" Here are the matching tasks in your list:\n", matchingTasks);
    }

    private String formatTaskList(String header, TaskList taskList) {
        assert taskList != null : "TaskList cannot be null";
        StringBuilder sb = new StringBuilder(header);
        for (int i = 0; i < taskList.getNumberOfTasks(); i++) {
            sb.append(formatTaskLine(i + 1, taskList.getTask(i)));
        }
        return sb.toString().trim();
    }

    private String formatTaskLine(int index, Task task) {
        return String.format(" %d.%s\n", index, task);
    }
}
