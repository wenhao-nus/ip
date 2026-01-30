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

        while (!Parser.isByeCommand(userInput)) {
            System.out.print(HORIZONTAL_LINE);
            if (Parser.isListCommand(userInput)) {
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < taskList.getNumberOfTasks(); i++) {
                    Task currentTask = taskList.getTask(i);
                    System.out.println(" " + (i+1) + "." + currentTask);
                }
            } else {
                try {
                    if (Parser.isTask(userInput)) {
                        Task newTask = Parser.parseTask(userInput);
                        taskList.addTask(newTask);
                        System.out.println(" Got it. I've added this task:\n"
                                + "   " + newTask + "\n" + " Now you have "
                                + taskList.getNumberOfTasks() + " tasks in the list.");
                    } else if (Parser.isMarkCommand(userInput)) {
                        int taskIndex = Parser.parseMarkUnmarkDelete(userInput);
                        if (taskIndex >= taskList.getNumberOfTasks()) {
                            throw new AeolianException(" There is no such task in the list.");
                        }

                        Task chosenTask = taskList.getTask(taskIndex);
                        chosenTask.markAsDone();
                        System.out.println(" Nice! I've marked this task as done:\n"
                                + "   " + chosenTask);
                    } else if (Parser.isUnmarkCommand(userInput)) {
                        int taskIndex = Parser.parseMarkUnmarkDelete(userInput);
                        if (taskIndex >= taskList.getNumberOfTasks()) {
                            throw new AeolianException(" There is no such task in the list.");
                        }

                        Task chosenTask = taskList.getTask(taskIndex);
                        chosenTask.markAsDone();
                        System.out.println(" OK, I've marked this task as not done yet:\n"
                                + "   " + chosenTask);
                    } else if (Parser.isDeleteCommand(userInput)) {
                        String[] tokens = userInput.split(" ");
                        int taskIndex = Parser.parseMarkUnmarkDelete(userInput);
                        if (taskIndex >= taskList.getNumberOfTasks()) {
                            throw new AeolianException(" There is no such task in the list.");
                        }

                        Task chosenTask = taskList.getTask(taskIndex);
                        taskList.removeTask(chosenTask);
                        System.out.println(" Noted. I've removed this task:\n   " + chosenTask + "\n"
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