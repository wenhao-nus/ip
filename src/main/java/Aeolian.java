import java.util.Scanner;
import java.util.ArrayList;

public class Aeolian {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        final String HORIZONTAL_LINE =
                "____________________________________________________________\n";
        final String GREETING_MESSAGE = HORIZONTAL_LINE +
                " Hello! I'm Aeolian\n" +
                " What can I do for you?\n" +
                HORIZONTAL_LINE;
        final String GOODBYE_MESSAGE = HORIZONTAL_LINE
                + " Bye. Hope to see you again soon!\n" + HORIZONTAL_LINE;

        ArrayList<String> listStore = new ArrayList<String>();

        System.out.println(GREETING_MESSAGE);
        String userInput = sc.nextLine();

        while (!userInput.equals("bye")) {
            if (userInput.equals("list")) {
                System.out.print(HORIZONTAL_LINE);
                for (int i = 0; i < listStore.size(); i++) {
                    System.out.println(i+1 + ". " + listStore.get(i));
                }
                System.out.println(HORIZONTAL_LINE);

            } else {
                listStore.add(userInput);
                System.out.println(HORIZONTAL_LINE + " added: " + userInput +
                        '\n' + HORIZONTAL_LINE);
            }
            userInput = sc.nextLine();
        }

        System.out.println(GOODBYE_MESSAGE);
        sc.close();
    }
}
