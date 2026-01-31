package aeolian;

import java.io.IOException;

/**
 * Deals with output interactions with the user.
 */
public class Ui {
    private static final String HORIZONTAL_LINE =
            "____________________________________________________________\n";

    /**
     * Displays a greeting message to the user.
     */
    public void showGreetings() {
        System.out.print(HORIZONTAL_LINE +
                " Hello! I'm Aeolian\n" +
                " What can I do for you?\n" +
                HORIZONTAL_LINE);
    }

    /**
     * Displays a goodbye message to the user.
     */
    public void showGoodbye() {
        System.out.print(HORIZONTAL_LINE
                + " Bye. Hope to see you again soon!\n" + HORIZONTAL_LINE);
    }

    /**
     * Displays an error message based on the exception encountered.
     *
     * @param e The exception that occurred.
     */
    public void showException(Exception e) {
        if (e instanceof AeolianException) {
            System.out.print(HORIZONTAL_LINE);
            System.out.println(e.getMessage());
            System.out.print(HORIZONTAL_LINE);
        } else if (e instanceof IOException) {
            System.out.print(HORIZONTAL_LINE);
            System.out.println("IO error.");
            System.out.print(HORIZONTAL_LINE);
        } else {
            System.out.print(HORIZONTAL_LINE);
            System.out.println("An error has occurred.");
            System.out.print(HORIZONTAL_LINE);
        }
    }

    /**
     * Displays all tasks in the task list.
     *
     * @param taskList The list of tasks to be displayed.
     */
    public void showAllTasks(TaskList taskList) {
        System.out.print(HORIZONTAL_LINE);
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < taskList.getNumberOfTasks(); i++) {
            Task currentTask = taskList.getTask(i);
            System.out.println(" " + (i + 1) + "." + currentTask);
        }
        System.out.print(HORIZONTAL_LINE);
    }

    /**
     * Displays a success message after adding a task.
     *
     * @param newTask The task that was added.
     * @param taskList The updated task list.
     */
    public void showAddTaskSuccess(Task newTask, TaskList taskList) {
        System.out.print(HORIZONTAL_LINE);
        System.out.println(" Got it. I've added this task:\n"
                + "   " + newTask + "\n" + " Now you have "
                + taskList.getNumberOfTasks() + " tasks in the list.");
        System.out.print(HORIZONTAL_LINE);
    }

    /**
     * Displays a success message after marking a task as done.
     *
     * @param chosenTask The task that was marked.
     */
    public void showMarkTaskSuccess(Task chosenTask) {
        System.out.print(HORIZONTAL_LINE);
        System.out.println(" Nice! I've marked this task as done:\n"
                + "   " + chosenTask);
        System.out.print(HORIZONTAL_LINE);
    }

    /**
     * Displays a success message after unmarking a task as done.
     *
     * @param chosenTask The task that was unmarked.
     */
    public void showUnmarkTaskSuccess(Task chosenTask) {
        System.out.print(HORIZONTAL_LINE);
        System.out.println(" OK, I've marked this task as not done yet:\n"
                + "   " + chosenTask);
        System.out.print(HORIZONTAL_LINE);
    }

    /**
     * Displays a success message after deleting a task.
     *
     * @param chosenTask The task that was deleted.
     * @param taskList The updated task list.
     */
    public void showDeleteTaskSuccess(Task chosenTask, TaskList taskList) {
        System.out.print(HORIZONTAL_LINE);
        System.out.println(" Noted. I've removed this task:\n   " + chosenTask + "\n"
                + " Now you have "
                + taskList.getNumberOfTasks() + " tasks in the list.");
        System.out.print(HORIZONTAL_LINE);
    }
}
