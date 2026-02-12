package aeolian;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Deals with loading tasks from a file on disk and saving tasks in a file.
 */
class Storage {
    private final TaskList taskList;
    private final String filePath;

    /**
     * Constructs a Storage object with the specified file path.
     *
     * @param filePath Path to the file where tasks are stored.
     */
    public Storage(String filePath) {
        this.taskList = loadTasksFromFile(filePath);
        assert this.taskList != null : "loadTasksFromFile should always return a TaskList object";
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
        File file = new File(filePath);
        TaskList tasks = new TaskList();

        if (!file.exists()) {
            return tasks;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                loadLine(fileScanner.nextLine(), tasks);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading tasks from file.");
        }
        return tasks;
    }

    private void loadLine(String line, TaskList tasks) {
        String trimmedLine = line.trim();
        if (trimmedLine.isEmpty()) {
            return;
        }

        try {
            Task t = parseTaskLine(trimmedLine);
            tasks.addTask(t);
        } catch (Exception e) {
            System.out.println("Corrupted line from storage, skipping it.");
        }
    }

    /**
     * Saves the provided tasks to the specified file path, creating parent directories if needed.
     *
     * @param filePath Path to the storage file.
     * @param tasks The tasks to serialize and write.
     * @throws IOException If there is an error while writing to the file.
     */
    private void saveTasksToFile(String filePath, TaskList tasks) throws IOException {
        assert filePath != null : "File path cannot be null";
        assert tasks != null : "TaskList cannot be null";
        ensureParentDirectoryExists(filePath);

        try (FileWriter fw = new FileWriter(filePath)) {
            for (int i = 0; i < tasks.getNumberOfTasks(); i++) {
                fw.write(tasks.getTask(i).toFileFormat());
                fw.write(System.lineSeparator()); // add newline in cross-platform way
            }
        } catch (IOException e) {
            System.out.println("Error writing tasks to file.");
        }
    }

    private void ensureParentDirectoryExists(String filePath) {
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
    }

    /**
     * Parses a single serialized task line into a Task.
     *
     * Expected formats:
     * T | 0/1 | desc
     * D | 0/1 | desc | by
     * E | 0/1 | desc | from | to
     *
     * @param line The line to parse.
     * @return The Task represented by the line.
     * @throws AeolianException If task-specific parsing fails (e.g., invalid date).
     * @throws IllegalArgumentException If the line is malformed or the type is unknown.
     */
    private Task parseTaskLine(String line) throws AeolianException {
        String[] parts = line.split("\\s*\\|\\s*");
        return createTask(parts);
    }

    private void validateBasicFormat(String[] parts) {
        if (parts.length < 3) {
            throw new IllegalArgumentException("Bad line format in storage.");
        }
    }

    private Task createTask(String[] parts) throws AeolianException {
        assert parts != null : "Parts array cannot be null";
        validateBasicFormat(parts);

        String type = parts[0];
        Task task;
        switch (type) {
        case "T":
            task = createTodo(parts);
            break;
        case "D":
            task = createDeadline(parts);
            break;
        case "E":
            task = createEvent(parts);
            break;
        default:
            throw new IllegalArgumentException("Unknown task type in storage: " + type);
        }

        if (parts[1].equals("1")) {
            task.markAsDone();
        }
        return task;
    }

    private Task createTodo(String[] parts) {
        assert parts != null && parts.length >= 3 : "Parts array must have at least 3 elements for Todo";
        return new Todo(parts[2]);
    }

    private Task createDeadline(String[] parts) throws AeolianException {
        assert parts != null : "Parts array cannot be null";
        if (parts.length < 4) {
            throw new IllegalArgumentException("Bad deadline format in storage.");
        }
        return new Deadline(parts[2], parts[3]);
    }

    private Task createEvent(String[] parts) throws AeolianException {
        assert parts != null : "Parts array cannot be null";
        if (parts.length < 5) {
            throw new IllegalArgumentException("Bad event format in storage.");
        }
        return new Event(parts[2], parts[3], parts[4]);
    }
}

