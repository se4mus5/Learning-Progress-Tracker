package tracker.ui;

import java.util.Scanner;

import static tracker.ui.InputValidators.*;

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
                case "add students":
                    addStudents();
                    break;

                case "back":
                    System.out.println("Enter 'exit' to exit the program");
                    break;

                default:
                    System.out.println("Error: unknown command!");
                    break;
            }
        }
    }

    private void addStudents() {
        int entryCount = 0;
        System.out.println("Enter student credentials or 'back' to return");
        while (true) {
            String studentCredentialsEntered = scanner.nextLine();
            if (studentCredentialsEntered.equals("back")) {
                System.out.printf("Total %d students have been added.\n", entryCount);
                break;
            }
            if (isInputValid(studentCredentialsEntered)) {
                System.out.println("The student has been added.");
                entryCount++;
            }
        }
    }

    static String[] parseInput(String studentCredentialsEntered) {
        String firstName = studentCredentialsEntered.substring(0, studentCredentialsEntered.indexOf("\s"));
        String lastName = studentCredentialsEntered.substring(studentCredentialsEntered.indexOf("\s") + 1, studentCredentialsEntered.lastIndexOf("\s"));
        String email = studentCredentialsEntered.substring(studentCredentialsEntered.lastIndexOf("\s") + 1);
        return new String[] {firstName, lastName, email};
    }

    static boolean isInputValid(String studentCredentialsEntered) {
        String firstName;
        String lastName;
        String email;

        String[] credentialsEntered;
        if (!isInputStructureValid(studentCredentialsEntered)) {
            System.out.println("Incorrect credentials.");
            return false;
        }

        credentialsEntered = parseInput(studentCredentialsEntered);
        firstName = credentialsEntered[0];
        lastName = credentialsEntered[1];
        email = credentialsEntered[2];

        if (!isValidFirstName(firstName)) {
            System.out.println("Incorrect first name.");
            return false;
        } else if (!isValidLastName(lastName)) {
            System.out.println("Incorrect last name.");
            return false;
        } else if (!isValidEmail(email)) {
            System.out.println("Incorrect email.");
            return false;
        }

        return true;
    }
}
