import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Aeolian {
    private static final String FILE_PATH = "./data/aeolian.txt";

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

        ArrayList<Task> taskStore = loadTasksFromFile(FILE_PATH);
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

        try {
            saveTasksToFile(FILE_PATH, taskStore);
        } catch (IOException e) {
            System.out.println("Error saving tasks to file.");
        }

        System.out.println(GOODBYE_MESSAGE);
        sc.close();
    }

    private static ArrayList<Task> loadTasksFromFile(String filePath) {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return tasks; // first run, nothing to load
        }

        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                try {
                    Task t = parseTaskLine(line);
                    if (t != null) {
                        tasks.add(t);
                    }
                } catch (Exception corruptedLine) {
                    System.out.println("Corrupted line from storage, skipping it.");
                }
            }

            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error reading tasks from file.");
        }
        return tasks;
    }

    private static void saveTasksToFile(String filePath, ArrayList<Task> tasks) throws IOException {
        File file = new File(filePath);

        File parent = file.getParentFile(); // referring to ./data
        if (parent != null && !parent.exists()) {
            parent.mkdirs(); // create ./data if missing
        }

        FileWriter fw = new FileWriter(file); // overwrite
        for (Task t : tasks) {
            fw.write(serializeTask(t));
            fw.write(System.lineSeparator()); // add newline in cross-platform way
        }
        fw.close();
    }

    private static Task parseTaskLine(String line) {
        // Format:
        // T | 0/1 | desc
        // D | 0/1 | desc | by
        // E | 0/1 | desc | from | to
        String[] parts = line.split("\\s*\\|\\s*");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Bad line");
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String desc = parts[2];

        Task task;
        switch (type) {
        case "T":
            task = new Todo(desc);
            break;
        case "D":
            if (parts.length < 4) {
                throw new IllegalArgumentException("Bad deadline line.");
            }
            task = new Deadline(desc, parts[3]);
            break;
        case "E":
            if (parts.length < 5) {
                throw new IllegalArgumentException("Bad event line.");
            }
            task = new Event(desc, parts[3], parts[4]);
            break;
        default:
            throw new IllegalArgumentException("Unknown task type.");
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }

    private static String serializeTask(Task t) {
        String done = t.isDone() ? "1" : "0";

        if (t instanceof Todo) {
            return "T | " + done + " | " + t.getDescription();
        } else if (t instanceof Deadline) {
            Deadline d = (Deadline) t;
            return "D | " + done + " | " + d.getDescription() + " | " + d.getBy();
        } else if (t instanceof Event) {
            Event e = (Event) t;
            return "E | " + done + " | " + e.getDescription() + " | " + e.getFrom() + " | " + e.getTo();
        } else {
            return "T | " + done + " | " + t.getDescription();
        }
    }
}