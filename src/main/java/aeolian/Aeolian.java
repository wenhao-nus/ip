package aeolian;

import java.io.IOException;
import java.util.Scanner;

/**
 * Represents the main chatbot class Aeolian.
 */
public class Aeolian {
    private Storage storage;
    private TaskList taskList;
    private Ui ui;

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

    public String getResponse(String input) {
        assert storage != null : "Storage should be initialized";
        assert taskList != null : "TaskList should be initialized";
        assert ui != null : "Ui should be initialized";
        try {
            if (Parser.isByeCommand(input)) {
                storage.save();
                return ui.showGoodbye();
            } else if (Parser.isListCommand(input)) {
                return ui.showAllTasks(taskList);
            } else if (Parser.isTask(input)) {
                Task newTask = Parser.parseTask(input);
                taskList.addTask(newTask);
                return ui.showAddTaskSuccess(newTask, taskList);
            } else if (Parser.isFindCommand(input)) {
                String keyword = Parser.parseFindKeyword(input);
                assert keyword != null || !keyword.isEmpty() : "Keyword should not be null";
                TaskList matchingTasks = taskList.findTasks(keyword);
                return ui.showMatchingTasks(matchingTasks);
            } else if (Parser.isMarkCommand(input)) {
                int taskIndex = Parser.parseMarkUnmarkDelete(input);
                if (taskIndex >= taskList.getNumberOfTasks()) {
                    throw new AeolianException(" There is no such task in the list.");
                }
                Task chosenTask = taskList.getTask(taskIndex);
                chosenTask.markAsDone();
                return ui.showMarkTaskSuccess(chosenTask);
            } else if (Parser.isUnmarkCommand(input)) {
                int taskIndex = Parser.parseMarkUnmarkDelete(input);
                if (taskIndex >= taskList.getNumberOfTasks()) {
                    throw new AeolianException(" There is no such task in the list.");
                }
                Task chosenTask = taskList.getTask(taskIndex);
                chosenTask.unmarkAsDone();
                return ui.showUnmarkTaskSuccess(chosenTask);
            } else if (Parser.isDeleteCommand(input)) {
                int taskIndex = Parser.parseMarkUnmarkDelete(input);
                if (taskIndex >= taskList.getNumberOfTasks()) {
                    throw new AeolianException(" There is no such task in the list.");
                }
                Task chosenTask = taskList.getTask(taskIndex);
                taskList.removeTask(chosenTask);
                return ui.showDeleteTaskSuccess(chosenTask, taskList);
            } else {
                throw new AeolianException(" I don't understand that command.");
            }
        } catch (AeolianException | IOException e) {
            return ui.showException(e);
        }
    }

    /**
     * Returns the greeting message from Aeolian.
     *
     * @return Greeting message.
     */
    public String getGreetings() {
        return ui.showGreetings();
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
                    } else if (Parser.isFindCommand(userInput)) {
                        String keyword = Parser.parseFindKeyword(userInput);
                        TaskList matchingTasks = taskList.findTasks(keyword);
                        ui.showMatchingTasks(matchingTasks);
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
