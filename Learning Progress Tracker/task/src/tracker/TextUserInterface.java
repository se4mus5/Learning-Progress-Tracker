package tracker;

import java.util.Scanner;

public class TextUserInterface {
    private final Scanner scanner = new Scanner(System.in);
    public void start() {
        System.out.println("Learning Progress Tracker");
        while (true) {
            //System.out.print("> "); // tests do not follow specs
            String command = scanner.nextLine().strip();

            switch (command) {
                case "exit":
                    System.out.println("Bye!");
                    Runtime.getRuntime().halt(0);
                    break;
                case "":
                    System.out.println("No input.");
                    break;
                default:
                    System.out.println("Error: unknown command!");
                    break;
            }
        }
    }
}
