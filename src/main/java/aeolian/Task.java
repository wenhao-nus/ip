package aeolian;

import java.time.format.DateTimeFormatter;

/**
 * Represents a task in the chatbot.
 */
public abstract class Task {
    protected static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy");
    protected String description;
    protected boolean isDone;


    /**
     * Constructs a Task with the given description.
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        assert description != null && !description.trim().isEmpty() : "Description cannot be empty";
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon of the task.
     *
     * @return "X" if done, " " otherwise.
     */
    public String getStatusIcon() {
        return (this.isDone ? "X" : " ");
    }

    /**
     * Returns the description of the task.
     *
     * @return Description of the task.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns true if the task is done.
     *
     * @return True if done, false otherwise.
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void unmarkAsDone() {
        this.isDone = false;
    }

    /**
     * Returns the task in a format suitable for file storage.
     *
     * @return File format string.
     */
    public abstract String toFileFormat();

    /**
     * Returns a string representation of the task.
     *
     * @return String representation.
     */
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
}
