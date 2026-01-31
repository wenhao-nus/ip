package aeolian;

import java.util.Scanner;
import java.io.IOException;

/**
 * Represents the main chatbot class Aeolian.
 */
public class Aeolian {
    private Storage storage;
    private TaskList taskList;
    private Ui ui;
    private static final String FILE_PATH = "./data/aeolian.txt";

    /**
     * Constructs an Aeolian object with the given file path.
     *
     * @param filePath Path to the storage file.
     */
    public Aeolian(String filePath) {
        this.storage = new Storage(filePath);
        this.taskList = this.storage.getTaskList();
        this.ui = new Ui();
    }

    /**
     * Main method to start the Aeolian application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        new Aeolian(FILE_PATH).run();
    }

    /**
     * Runs the main loop of the application, processing user commands.
     */
    public void run() {
        Scanner sc = new Scanner(System.in);

        ui.showGreetings();
        String userInput = sc.nextLine();

        while (!Parser.isByeCommand(userInput)) {
            if (Parser.isListCommand(userInput)) {
                ui.showAllTasks(taskList);
            } else {
                try {
                    if (Parser.isTask(userInput)) {
                        Task newTask = Parser.parseTask(userInput);
                        taskList.addTask(newTask);
                        ui.showAddTaskSuccess(newTask, taskList);
                    } else if (Parser.isMarkCommand(userInput)) {
                        int taskIndex = Parser.parseMarkUnmarkDelete(userInput);
                        if (taskIndex >= taskList.getNumberOfTasks()) {
                            throw new AeolianException(" There is no such task in the list.");
                        }

                        Task chosenTask = taskList.getTask(taskIndex);
                        chosenTask.markAsDone();
                        ui.showMarkTaskSuccess(chosenTask);
                    } else if (Parser.isUnmarkCommand(userInput)) {
                        int taskIndex = Parser.parseMarkUnmarkDelete(userInput);
                        if (taskIndex >= taskList.getNumberOfTasks()) {
                            throw new AeolianException(" There is no such task in the list.");
                        }

                        Task chosenTask = taskList.getTask(taskIndex);
                        chosenTask.unmarkAsDone();
                        ui.showUnmarkTaskSuccess(chosenTask);
                    } else if (Parser.isDeleteCommand(userInput)) {
                        String[] tokens = userInput.split(" ");
                        int taskIndex = Parser.parseMarkUnmarkDelete(userInput);
                        if (taskIndex >= taskList.getNumberOfTasks()) {
                            throw new AeolianException(" There is no such task in the list.");
                        }

                        Task chosenTask = taskList.getTask(taskIndex);
                        taskList.removeTask(chosenTask);
                        ui.showDeleteTaskSuccess(chosenTask, taskList);
                    } else {
                        throw new AeolianException(" I don't understand that command.");
                    }
                } catch (AeolianException e) {
                    ui.showException(e);
                }
            }
            userInput = sc.nextLine();
        }

        try {
            storage.save();
        } catch (IOException e) {
            ui.showException(e);
        }

        ui.showGoodbye();
        sc.close();
    }
}