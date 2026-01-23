import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.ArrayList;

public class Aeolian {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        final String HORIZONTAL_LINE =
                "____________________________________________________________\n";
        final String GREETING_MESSAGE = HORIZONTAL_LINE +
                " Hello! I'm Aeolian\n" +
                " What can I do for you?\n" +
                HORIZONTAL_LINE;
        final String GOODBYE_MESSAGE = HORIZONTAL_LINE
                + " Bye. Hope to see you again soon!\n" + HORIZONTAL_LINE;

        ArrayList<Task> listStore = new ArrayList<>();

        System.out.println(GREETING_MESSAGE);
        String userInput = sc.nextLine();

        while (!userInput.equals("bye")) {
            if (userInput.equals("list")) {
                System.out.print(HORIZONTAL_LINE);
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < listStore.size(); i++) {
                    Task currentTask = listStore.get(i);
                    System.out.println(" " + (i+1) + "." + currentTask);
                }
                System.out.println(HORIZONTAL_LINE);

            } else {

                if (userInput.matches("mark \\d+")) {
                    String[] tokens = userInput.split(" ");
                    int taskIndex = Integer.parseInt(tokens[1]) - 1;
                    Task chosenTask = listStore.get(taskIndex);
                    chosenTask.markAsDone();
                    System.out.print(HORIZONTAL_LINE +
                            " Nice! I've marked this task as done:\n"
                            + "   " + chosenTask +
                            '\n' + HORIZONTAL_LINE);
                } else if (userInput.matches("unmark \\d+")) {
                    String[] tokens = userInput.split(" ");
                    int taskIndex = Integer.parseInt(tokens[1]) - 1;
                    Task chosenTask = listStore.get(taskIndex);
                    chosenTask.unmarkAsDone();
                    System.out.print(HORIZONTAL_LINE +
                            " OK, I've marked this task as not done yet:\n"
                            + "   " + chosenTask +
                            '\n' + HORIZONTAL_LINE);
                } else {
                    listStore.add(new Task(userInput));
                    System.out.println(HORIZONTAL_LINE + " added: " + userInput +
                            '\n' + HORIZONTAL_LINE);
                }
            }
            userInput = sc.nextLine();
        }

        System.out.println(GOODBYE_MESSAGE);
        sc.close();
    }
}
