package aeolian;

import java.util.ArrayList;

/**
 * Contains the list of tasks and has operations to add/delete tasks in the list.
 */
class TaskList {
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
        assert index >= 0 && index < tasks.size() : "Index out of bounds";
        return tasks.get(index);
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        assert task != null : "Cannot add a null task";
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

    /**
     * Returns a new list containing tasks whose descriptions contain the given keyword.
     * The match is case-sensitive and performs a simple substring check.
     *
     * @param keyword The keyword to search for in task descriptions.
     * @return A TaskList with all matching tasks; empty if none match.
     */
    public TaskList findTasks(String keyword) {
        assert keyword != null : "Keyword cannot be null";
        TaskList matchingTasks = new TaskList();
        for (Task task : tasks) {
            if (task.getDescription().contains(keyword)) {
                matchingTasks.addTask(task);
            }
        }
        return matchingTasks;
    }
}

