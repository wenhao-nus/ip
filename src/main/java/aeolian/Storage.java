package aeolian;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Deals with loading tasks from a file on disk and saving tasks in a file.
 */
public class Storage {
    private final TaskList taskList;
    private final String filePath;

    /**
     * Constructs a Storage object with the specified file path.
     *
     * @param filePath Path to the file where tasks are stored.
     */
    public Storage(String filePath) {
        this.taskList = loadTasksFromFile(filePath);
        this.filePath = filePath;
    }

    /**
     * Returns the task list loaded from the file.
     *
     * @return The task list.
     */
    public TaskList getTaskList() {
        return this.taskList;
    }

    /**
     * Saves the current task list to the file.
     *
     * @throws IOException If there is an error writing to the file.
     */
    public void save() throws IOException {
        saveTasksToFile(filePath, taskList);
    }

    /**
     * Loads tasks from the specified file path, creating an empty list if the file does not exist.
     *
     * @param filePath Path to the storage file.
     * @return A TaskList initialized with tasks parsed from the file.
     */
    private TaskList loadTasksFromFile(String filePath) {
        TaskList tasks = new TaskList();
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
                        tasks.addTask(t);
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

    /**
     * Saves the provided tasks to the specified file path, creating parent directories if needed.
     *
     * @param filePath Path to the storage file.
     * @param tasks The tasks to serialize and write.
     * @throws IOException If there is an error while writing to the file.
     */
    private void saveTasksToFile(String filePath, TaskList tasks) throws IOException {
        File file = new File(filePath);

        File parent = file.getParentFile(); // referring to ./data
        if (parent != null && !parent.exists()) {
            parent.mkdirs(); // create ./data if missing
        }

        FileWriter fw = new FileWriter(file); // overwrite
        for (int i = 0; i < tasks.getNumberOfTasks(); i++) {
            fw.write(serializeTask(tasks.getTask(i)));
            fw.write(System.lineSeparator()); // add newline in cross-platform way
        }
        fw.close();
    }

    /**
     * Parses a single serialized task line into a Task.
     *
     * <p>Expected formats:
     * <pre>
     * T | 0/1 | desc
     * D | 0/1 | desc | by
     * E | 0/1 | desc | from | to
     * </pre>
     *
     * @param line The line to parse.
     * @return The Task represented by the line.
     * @throws AeolianException If task-specific parsing fails (e.g., invalid date).
     * @throws IllegalArgumentException If the line is malformed or the type is unknown.
     */
    private Task parseTaskLine(String line) throws AeolianException {
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

    /**
     * Serializes a task into its storage line format.
     *
     * @param t The task to serialize.
     * @return A single-line string representation suitable for storage.
     */
    private String serializeTask(Task t) {
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

