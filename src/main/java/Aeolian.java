import java.util.Scanner;

public class Aeolian {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String greeting = "____________________________________________________________\n" +
                " Hello! I'm Aeolian\n" +
                " What can I do for you?\n" +
                "____________________________________________________________\n";

        System.out.println(greeting);
        String userInput = sc.nextLine();

        while (!userInput.equals("bye")) {
            System.out.print("____________________________________________________________\n");
            System.out.println(userInput);
            System.out.print("____________________________________________________________\n");

            userInput = sc.nextLine();
        }

        String goodbye = "____________________________________________________________\n" +
                " Bye. Hope to see you again soon!\n" +
                "____________________________________________________________\n";

        System.out.println(goodbye);
        sc.close();
    }
}
