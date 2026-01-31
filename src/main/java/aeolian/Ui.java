package aeolian;

import java.io.IOException;

public class Ui {
    private static final String HORIZONTAL_LINE =
            "____________________________________________________________\n";

    public void showGreetings() {
        System.out.print(HORIZONTAL_LINE +
                " Hello! I'm Aeolian\n" +
                " What can I do for you?\n" +
                HORIZONTAL_LINE);
    }

    public void showGoodbye() {
        System.out.print(HORIZONTAL_LINE
                + " Bye. Hope to see you again soon!\n" + HORIZONTAL_LINE);
    }

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

    public void showAllTasks(TaskList taskList) {
        System.out.print(HORIZONTAL_LINE);
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < taskList.getNumberOfTasks(); i++) {
            Task currentTask = taskList.getTask(i);
            System.out.println(" " + (i + 1) + "." + currentTask);
        }
        System.out.print(HORIZONTAL_LINE);
    }

    public void showAddTaskSuccess(Task newTask, TaskList taskList) {
        System.out.print(HORIZONTAL_LINE);
        System.out.println(" Got it. I've added this task:\n"
                + "   " + newTask + "\n" + " Now you have "
                + taskList.getNumberOfTasks() + " tasks in the list.");
        System.out.print(HORIZONTAL_LINE);
    }

    public void showMarkTaskSuccess(Task chosenTask) {
        System.out.print(HORIZONTAL_LINE);
        System.out.println(" Nice! I've marked this task as done:\n"
                + "   " + chosenTask);
        System.out.print(HORIZONTAL_LINE);
    }

    public void showUnmarkTaskSuccess(Task chosenTask) {
        System.out.print(HORIZONTAL_LINE);
        System.out.println(" OK, I've marked this task as not done yet:\n"
                + "   " + chosenTask);
        System.out.print(HORIZONTAL_LINE);
    }

    public void showDeleteTaskSuccess(Task chosenTask, TaskList taskList) {
        System.out.print(HORIZONTAL_LINE);
        System.out.println(" Noted. I've removed this task:\n   " + chosenTask + "\n"
                + " Now you have "
                + taskList.getNumberOfTasks() + " tasks in the list.");
        System.out.print(HORIZONTAL_LINE);
    }
}
