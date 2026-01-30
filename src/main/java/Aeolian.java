import java.util.Scanner;
import java.io.IOException;

public class Aeolian {
    private Storage storage;
    private TaskList taskList;

    public Aeolian(String filePath) {
        this.storage = new Storage(filePath);
        this.taskList = this.storage.getTaskList();
    }

    public static void main(String[] args) {
        final String FILE_PATH = "./data/aeolian.txt";
        new Aeolian(FILE_PATH).run();
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        final String HORIZONTAL_LINE =
                "____________________________________________________________\n";
        final String GREETING_MESSAGE = HORIZONTAL_LINE +
                " Hello! I'm Aeolian\n" +
                " What can I do for you?\n" +
                HORIZONTAL_LINE;
        final String GOODBYE_MESSAGE = HORIZONTAL_LINE
                + " Bye. Hope to see you again soon!\n" + HORIZONTAL_LINE;

        System.out.print(GREETING_MESSAGE);
        String userInput = sc.nextLine();

        while (!userInput.equals("bye")) {
            System.out.print(HORIZONTAL_LINE);
            if (userInput.equals("list")) {
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < taskList.getNumberOfTasks(); i++) {
                    Task currentTask = taskList.getTask(i);
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
                        taskList.addTask(newTask);
                        System.out.println(" Got it. I've added this task:\n"
                                + "   " + newTask + "\n" + " Now you have "
                                + taskList.getNumberOfTasks() + " tasks in the list.");
                    } else if (parts[0].equals("deadline")) {

                        int byIndex = userInput.indexOf(" /by ");
                        if (byIndex == -1) {
                            throw new AeolianException(" Deadline must have /by yyyy-MM-dd");
                        }
                        if (byIndex < 9) {
                            throw new AeolianException(" Description of deadline cannot be enpty!");
                        }

                        String description = userInput.substring(9, byIndex).trim();
                        String by = userInput.substring(byIndex + 5).trim();

                        if (description.isEmpty()) {
                            throw new AeolianException(" Description of deadline cannot be empty!");
                        }

                        Task newTask = new Deadline(description, by); // parses yyyy-MM-dd
                        taskList.addTask(newTask);
                        System.out.println(" Got it. I've added this task:\n"
                                + "   " + newTask + "\n" + " Now you have "
                                + taskList.getNumberOfTasks() + " tasks in the list.");

                    } else if (parts[0].equals("event")) {
                        int fromIndex = userInput.indexOf(" /from ");
                        int toIndex = userInput.indexOf(" /to ");
                        if (fromIndex == -1 || toIndex == -1 || toIndex < fromIndex) {
                            throw new AeolianException(" Event must have /from yyyy-MM-dd and /to yyyy-MM-dd");
                        }

                        if (fromIndex < 6) {
                            throw new AeolianException(" Description of deadline cannot be enpty!");
                        }

                        String description = userInput.substring(6, fromIndex).trim();
                        String from = userInput.substring(fromIndex + 7, toIndex).trim();
                        String to = userInput.substring(toIndex + 5).trim();

                        if (description.isEmpty()) {
                            throw new AeolianException(" Description of event cannot be empty!");
                        }

                        Task newTask = new Event(description, from, to); // parses yyyy-MM-dd
                        taskList.addTask(newTask);

                        System.out.println(" Got it. I've added this task:\n"
                                + "   " + newTask + "\n" + " Now you have "
                                + taskList.getNumberOfTasks() + " tasks in the list.");

                    } else if (userInput.matches("mark \\d+")) {
                        String[] tokens = userInput.split(" ");
                        int taskIndex = Integer.parseInt(tokens[1]) - 1;
                        Task chosenTask = taskList.getTask(taskIndex);
                        chosenTask.markAsDone();
                        System.out.println(" Nice! I've marked this task as done:\n"
                                + "   " + chosenTask);
                    } else if (userInput.matches("unmark \\d+")) {
                        String[] tokens = userInput.split(" ");
                        int taskIndex = Integer.parseInt(tokens[1]) - 1;
                        Task chosenTask = taskList.getTask(taskIndex);
                        chosenTask.unmarkAsDone();
                        System.out.println(" OK, I've marked this task as not done yet:\n"
                                + "   " + chosenTask);
                    } else if (userInput.matches("delete \\d+")) {
                        int indexToDelete = Integer.parseInt(parts[1]) - 1;
                        Task deletedTask = taskList.getTask(indexToDelete);
                        taskList.removeTask(deletedTask);
                        System.out.println(" Noted. I've removed this task:\n   " + deletedTask + "\n"
                                + " Now you have "
                                + taskList.getNumberOfTasks() + " tasks in the list.");
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

        try {
            storage.save();
        } catch (IOException e) {
            System.out.println("Error saving tasks to file.");
        }

        System.out.println(GOODBYE_MESSAGE);
        sc.close();
    }

}