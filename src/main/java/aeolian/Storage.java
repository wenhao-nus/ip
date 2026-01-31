package aeolian;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {
    private final TaskList taskList;
    private final String filePath;

    public Storage(String filePath) {
        this.taskList = loadTasksFromFile(filePath);
        this.filePath = filePath;
    }

    public TaskList getTaskList() {
        return this.taskList;
    }

    public void save() throws IOException {
        saveTasksToFile(filePath, taskList);
    }


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

    private void saveTasksToFile(String filePath, TaskList tasks) throws IOException {
        File file = new File(filePath);

        File parent = file.getParentFile(); // referring to ./data
        if (parent != null && !parent.exists()) {
            parent.mkdirs(); // create ./data if missing
        }

        FileWriter fw = new FileWriter(file); // overwrite
        for (int i = 0; i < tasks.getNumberOfTasks(); i++ ) {
            fw.write(serializeTask(tasks.getTask(i)));
            fw.write(System.lineSeparator()); // add newline in cross-platform way
        }
        fw.close();
    }

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
