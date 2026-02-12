package aeolian;

import java.io.IOException;

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
        assert storage != null : "Storage should be initialized";
        assert taskList != null : "TaskList should be initialized";
        assert ui != null : "Ui should be initialized";
    }

    public String getResponse(String input) {
        assert storage != null : "Storage should be initialized";
        assert taskList != null : "TaskList should be initialized";
        assert ui != null : "Ui should be initialized";
        try {
            return processCommand(input);
        } catch (AeolianException | IOException e) {
            return ui.showException(e);
        }
    }

    private String processCommand(String input) throws AeolianException, IOException {
        if (Parser.isByeCommand(input)) {
            return handleBye();
        } else if (Parser.isListCommand(input)) {
            return handleList();
        } else if (Parser.isTask(input)) {
            return handleAddTask(input);
        } else if (Parser.isFindCommand(input)) {
            return handleFind(input);
        } else if (Parser.isMarkCommand(input)) {
            return handleMark(input);
        } else if (Parser.isUnmarkCommand(input)) {
            return handleUnmark(input);
        } else if (Parser.isDeleteCommand(input)) {
            return handleDelete(input);
        } else {
            throw new AeolianException(" I don't understand that command.");
        }
    }

    private String handleBye() throws IOException {
        storage.save();
        return ui.showGoodbye();
    }

    private String handleList() {
        return ui.showAllTasks(taskList);
    }

    private String handleAddTask(String input) throws AeolianException {
        Task newTask = Parser.parseTask(input);
        taskList.addTask(newTask);
        return ui.showAddTaskSuccess(newTask, taskList);
    }

    private String handleFind(String input) throws AeolianException {
        String keyword = Parser.parseFindKeyword(input);
        assert keyword != null && !keyword.isEmpty() : "Keyword should not be null or empty";
        TaskList matchingTasks = taskList.findTasks(keyword);
        return ui.showMatchingTasks(matchingTasks);
    }

    private String handleMark(String input) throws AeolianException {
        int taskIndex = Parser.parseMarkUnmarkDelete(input);
        validateTaskIndex(taskIndex);
        Task chosenTask = taskList.getTask(taskIndex);
        chosenTask.markAsDone();
        return ui.showMarkTaskSuccess(chosenTask);
    }

    private String handleUnmark(String input) throws AeolianException {
        int taskIndex = Parser.parseMarkUnmarkDelete(input);
        validateTaskIndex(taskIndex);
        Task chosenTask = taskList.getTask(taskIndex);
        chosenTask.unmarkAsDone();
        return ui.showUnmarkTaskSuccess(chosenTask);
    }

    private String handleDelete(String input) throws AeolianException {
        int taskIndex = Parser.parseMarkUnmarkDelete(input);
        validateTaskIndex(taskIndex);
        Task chosenTask = taskList.getTask(taskIndex);
        taskList.removeTask(chosenTask);
        return ui.showDeleteTaskSuccess(chosenTask, taskList);
    }

    private void validateTaskIndex(int index) throws AeolianException {
        if (index < 0 || index >= taskList.getNumberOfTasks()) {
            throw new AeolianException(" There is no such task in the list.");
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
}
