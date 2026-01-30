public class Parser {
    public static boolean isByeCommand(String userInput) {
        return userInput.equals("bye");
    }

    public static boolean isListCommand(String userInput) {
        return userInput.equals("list");
    }

    public static boolean isTask(String userInput) {
        return (userInput.startsWith("todo") || userInput.startsWith("deadline") || userInput.startsWith("event"));
    }

    public static boolean isMarkCommand(String userInput) {
        return userInput.startsWith("mark");
    }

    public static boolean isDeleteCommand(String userInput) {
        return userInput.startsWith("delete");
    }

    public static boolean isUnmarkCommand(String userInput) {
        return userInput.startsWith("unmark");
    }

    public static int parseMarkUnmarkDelete(String userInput) throws AeolianException {
        if (userInput.matches("mark \\d+") || userInput.matches("unmark \\d+")
                || userInput.matches("delete \\d+")) {
            return Integer.parseInt(userInput.split(" ")[1]) - 1;
        } else {
            throw new AeolianException(" I don't understand that command.");
        }
    }

    public static Task parseTask(String userInput) throws AeolianException {
        String[] parts = userInput.split(" ");

        if (parts[0].equals("todo")) {
            if (parts.length < 2) {
                throw new AeolianException(" Description of todo cannot be empty!");
            }
            String description = parts[1].trim();
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
                throw new AeolianException(" Description of deadline cannot be enpty!");
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
                throw new AeolianException(" Description of deadline cannot be enpty!");
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



