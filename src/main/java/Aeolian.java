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

        ArrayList<Task> taskStore = new ArrayList<>();

        System.out.print(GREETING_MESSAGE);
        String userInput = sc.nextLine();

        while (!userInput.equals("bye")) {
            System.out.print(HORIZONTAL_LINE);
            if (userInput.equals("list")) {
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < taskStore.size(); i++) {
                    Task currentTask = taskStore.get(i);
                    System.out.println(" " + (i+1) + "." + currentTask);
                }
            } else {
                try {
                    String[] parts = userInput.split(" ", 2);

                    if (parts[0].equals("todo")) {

                        if (parts.length < 2) {
                            throw new AeolianException(" Description of todo cannot be empty!");
                        }
                        String description = parts[1].trim();
                        if (description.isEmpty()) {
                            throw new AeolianException(" Description of todo cannot be empty!");
                        }

                        Task newTask = new Todo(description);
                        taskStore.add(newTask);
                        System.out.println(" Got it. I've added this task:\n"
                                + "   " + newTask + "\n" + " Now you have "
                                + taskStore.size() + " tasks in the list.");
                    } else if (parts[0].equals("deadline")) {

                        int descEndIndex = userInput.indexOf(" /by ");
                        String description = userInput.substring(9, descEndIndex);
                        String by = userInput.substring(descEndIndex + 5);
                        Task newTask = new Deadline(description, by);
                        taskStore.add(newTask);
                        System.out.println(" Got it. I've added this task:\n"
                                + "   " + newTask + "\n" + " Now you have "
                                + taskStore.size() + " tasks in the list.");

                    } else if (parts[0].equals("event")) {
                        int descEndIndex = userInput.indexOf(" /from ");
                        String description = userInput.substring(6, descEndIndex);
                        int fromEndIndex = userInput.indexOf(" /to ");
                        String from = userInput.substring(descEndIndex + 7, fromEndIndex);
                        String to = userInput.substring(fromEndIndex + 5);
                        Task newTask = new Event(description, from, to);
                        taskStore.add(newTask);

                        System.out.println(" Got it. I've added this task:\n"
                                + "   " + newTask + "\n" + " Now you have "
                                + taskStore.size() + " tasks in the list.");

                    } else if (userInput.matches("mark \\d+")) {
                        String[] tokens = userInput.split(" ");
                        int taskIndex = Integer.parseInt(tokens[1]) - 1;
                        Task chosenTask = taskStore.get(taskIndex);
                        chosenTask.markAsDone();
                        System.out.println(" Nice! I've marked this task as done:\n"
                                + "   " + chosenTask);
                    } else if (userInput.matches("unmark \\d+")) {
                        String[] tokens = userInput.split(" ");
                        int taskIndex = Integer.parseInt(tokens[1]) - 1;
                        Task chosenTask = taskStore.get(taskIndex);
                        chosenTask.unmarkAsDone();
                        System.out.println(" OK, I've marked this task as not done yet:\n"
                                + "   " + chosenTask);
                    } else if (userInput.matches("delete \\d+")) {
                        int indexToDelete = Integer.parseInt(parts[1]) - 1;
                        Task deletedTask = taskStore.get(indexToDelete);
                        taskStore.remove(deletedTask);
                        System.out.println(" Noted. I've removed this task:\n   " + deletedTask + "\n"
                                + " Now you have "
                                + taskStore.size() + " tasks in the list.");
                    } else {
                        throw new AeolianException(" I don't understand that command.");
                    }
                } catch (AeolianException e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.print(HORIZONTAL_LINE);

            userInput = sc.nextLine();
        }

        System.out.println(GOODBYE_MESSAGE);
        sc.close();
    }
}
