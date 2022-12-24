package tracker.ui;

import tracker.app.AppLogic;

import java.util.Scanner;
import java.util.Set;

import static tracker.ui.InputValidators.*;

public class TextUserInterface {
    private final AppLogic appLogic;
    private final Scanner scanner = new Scanner(System.in);
    public TextUserInterface(AppLogic appLogic) {
        this.appLogic = appLogic;
    }

    public void start() {
        System.out.println("Learning Progress Tracker");
        String command = scanner.nextLine().strip();
        while (!command.equals("exit")) {
            switch (command) {
                case "" -> System.out.println("No input.");
                case "add students" -> addStudents();
                case "add points" -> updatePoints();
                case "list" -> listStudents();
                case "find" -> findStudent();
                case "back" -> System.out.println("Enter 'exit' to exit the program");
                default -> System.out.println("Error: unknown command!");
            }
            command = scanner.nextLine().strip();
        }
        System.out.println("Bye");
    }

    private void addStudents() {
        int entryCount = 0;
        System.out.println("Enter student credentials or 'back' to return");
        while (true) {
            String studentCredentialsEntered = scanner.nextLine();
            if (studentCredentialsEntered.equals("back")) {
                System.out.printf("Total %d students have been added.\n", entryCount);
                break;
            } else if (isStudentDataInputValid(studentCredentialsEntered)) {
                boolean success = appLogic.addStudent(studentCredentialsEntered);
                if (success) {
                    System.out.println("The student has been added.");
                    entryCount++;
                } else {
                    System.out.println("This email is already taken.");
                }
            }
        }
    }

    private void listStudents() {
        Set<String> studentIds = appLogic.listStudents();
        if (studentIds.isEmpty()) {
            System.out.println("No students found");
        } else {
            System.out.println("Students:");
            for (String id : appLogic.listStudents()) {
                System.out.println(id);
            }
        }
    }

    private void findStudent() {
        System.out.println("Enter an id or 'back' to return:");
        while (true) {
            String studentIdEntered = scanner.nextLine();
            if (studentIdEntered.equals("back")) {
                break;
            } else {
                System.out.println(appLogic.findStudent(studentIdEntered));
            }
        }
    }

    private void updatePoints() {
        System.out.println("Enter an id and points or 'back' to return:");
        while (true) {
            String pointsEntered = scanner.nextLine();
            if (pointsEntered.equals("back")) {
                break;
            } else if (isPointsInputValid(pointsEntered)) {
                String id = pointsEntered.substring(0, pointsEntered.indexOf("\s"));
                boolean success = appLogic.updatePointsOfStudent(pointsEntered);
                if (success) {
                    System.out.println("Points updated.");
                } else {
                    System.out.printf("No student is found for id=%s\n", id);
                }
            } else {
                System.out.println("Incorrect points format.");
            }
        }
    }

}
