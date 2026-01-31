package aeolian;

import java.util.ArrayList;

/**
 * Contains the list of tasks and has operations to add/delete tasks in the list.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index Index of the task to return.
     * @return The task at the specified index.
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return Number of tasks.
     */
    public int getNumberOfTasks() {
        return tasks.size();
    }

    /**
     * Removes the specified task from the list.
     *
     * @param task The task to be removed.
     */
    public void removeTask(Task task) {
        tasks.remove(task);
    }
}
