package aeolian;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public int getNumberOfTasks() {
        return tasks.size();
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public TaskList findTasks(String keyword) {
        TaskList matchingTasks = new TaskList();
        for (Task task : tasks) {
            if (task.getDescription().contains(keyword)) {
                matchingTasks.addTask(task);
            }
        }
        return matchingTasks;
    }
}
