package aeolian;

/**
 * Deals with making sense of the user command.
 */
public class Parser {
    /**
     * Checks if the user input is a "bye" command.
     *
     * @param userInput Input string from user.
     * @return True if input is a "bye" command, false otherwise.
     */
    public static boolean isByeCommand(String userInput) {
        return userInput.equals("bye");
    }

    /**
     * Checks if the user input is a "list" command.
     *
     * @param userInput Input string from user.
     * @return True if input is a "list" command, false otherwise.
     */
    public static boolean isListCommand(String userInput) {
        return userInput.equals("list");
    }

    /**
     * Checks if the user input is a type of task command.
     * A task command is a "todo", "deadline" or "event" command.
     *
     * @param userInput Input string from user.
     * @return True if input is a task command, false otherwise.
     */
    public static boolean isTask(String userInput) {
        return (userInput.startsWith("todo") || userInput.startsWith("deadline") || userInput.startsWith("event"));
    }

    /**
     * Checks if the user input is a "mark" command.
     *
     * @param userInput Input string from user.
     * @return True if input starts with "mark", false otherwise.
     */
    public static boolean isMarkCommand(String userInput) {
        return userInput.startsWith("mark");
    }

    /**
     * Checks if the user input is a "delete" command.
     *
     * @param userInput Input string from user.
     * @return True if input starts with "delete", false otherwise.
     */
    public static boolean isDeleteCommand(String userInput) {
        return userInput.startsWith("delete");
    }

    /**
     * Checks if the user input is an "unmark" command.
     *
     * @param userInput Input string from user.
     * @return True if input starts with "unmark", false otherwise.
     */
    public static boolean isUnmarkCommand(String userInput) {
        return userInput.startsWith("unmark");
    }

    public static boolean isFindCommand(String userInput) {
        return userInput.startsWith("find");
    }

    public static String parseFindKeyword(String userInput) throws AeolianException {
        if (userInput.trim().equals("find")) {
            throw new AeolianException(" The keyword for find cannot be empty.");
        }
        String keyword = userInput.substring(5).trim();
        if (keyword.isEmpty()) {
            throw new AeolianException(" The keyword for find cannot be empty.");
        }
        return keyword;
    }

    /**
     * Parses the task index from mark, unmark, or delete commands.
     *
     * @param userInput Input string from user.
     * @return The 0-based index of the task.
     * @throws AeolianException If the command format is invalid.
     */
    public static int parseMarkUnmarkDelete(String userInput) throws AeolianException {
        if (userInput.matches("mark \\d+") || userInput.matches("unmark \\d+")
                || userInput.matches("delete \\d+")) {
            return Integer.parseInt(userInput.split(" ")[1]) - 1;
        } else {
            throw new AeolianException(" I don't understand that command.");
        }
    }

    /**
     * Parses a task command and returns the corresponding Task object.
     *
     * @param userInput Input string from user.
     * @return The Task object represented by the input.
     * @throws AeolianException If the command format is invalid or description is missing.
     */
    public static Task parseTask(String userInput) throws AeolianException {
        String[] parts = userInput.split(" ");

        if (parts[0].equals("todo")) {
            if (parts.length < 2) {
                throw new AeolianException(" Description of todo cannot be empty!");
            }
            String description = userInput.substring(5).trim();
            if (description.isEmpty()) {
                throw new AeolianException(" Description of todo cannot be empty!");
            }

            return new Todo(description);
        } else if (parts[0].equals("deadline")) {

            int byIndex = userInput.indexOf(" /by ");
            if (byIndex == -1) {
                throw new AeolianException(" deadline must have /by yyyy-MM-dd");
            }
            if (byIndex < 9) {
                throw new AeolianException(" Description of deadline cannot be empty!");
            }

            String description = userInput.substring(9, byIndex).trim();
            String by = userInput.substring(byIndex + 5).trim();

            if (description.isEmpty()) {
                throw new AeolianException(" Description of deadline cannot be empty!");
            }

            return new Deadline(description, by); // parses yyyy-MM-dd
        } else {
            int fromIndex = userInput.indexOf(" /from ");
            int toIndex = userInput.indexOf(" /to ");
            if (fromIndex == -1 || toIndex == -1 || toIndex < fromIndex) {
                throw new AeolianException(" Event must have /from yyyy-MM-dd and /to yyyy-MM-dd");
            }

            if (fromIndex < 6) {
                throw new AeolianException(" Description of event cannot be empty!");
            }

            String description = userInput.substring(6, fromIndex).trim();
            String from = userInput.substring(fromIndex + 7, toIndex).trim();
            String to = userInput.substring(toIndex + 5).trim();

            if (description.isEmpty()) {
                throw new AeolianException(" Description of event cannot be empty!");
            }
            return new Event(description, from, to);
        }
    }
}