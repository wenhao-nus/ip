import java.util.Scanner;

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

        System.out.println(GREETING_MESSAGE);
        String userInput = sc.nextLine();

        while (!userInput.equals("bye")) {
            System.out.println(HORIZONTAL_LINE + userInput + '\n' + HORIZONTAL_LINE);

            userInput = sc.nextLine();
        }

        System.out.println(GOODBYE_MESSAGE);
        sc.close();
    }
}
