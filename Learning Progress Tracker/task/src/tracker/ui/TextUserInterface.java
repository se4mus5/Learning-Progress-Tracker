package tracker.ui;

import tracker.app.AppLogic;
import tracker.app.Student;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static tracker.ui.InputValidators.*;

public class TextUserInterface {
    private final AppLogic app;
    private final Scanner scanner = new Scanner(System.in);
    public TextUserInterface(AppLogic app) {
        this.app = app;
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
                case "statistics" -> statistics();
                case "notify" -> notifyStudents();
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
                boolean success = app.addStudent(studentCredentialsEntered);
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
        Set<String> studentIds = app.listStudents();
        if (studentIds.isEmpty()) {
            System.out.println("No students found");
        } else {
            System.out.println("Students:");
            for (String id : app.listStudents()) {
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
                System.out.println(app.findStudent(studentIdEntered));
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
                String id = pointsEntered.substring(0, pointsEntered.indexOf(" "));
                boolean success = app.updatePointsOfStudent(pointsEntered);
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

    private void statistics() {
        System.out.println("Type the name of a course to see details or 'back' to quit:");
        String[] summaryStats = app.summaryStats();
        System.out.printf("""
                             Most popular: %s
                             Least popular: %s
                             Highest activity: %s
                             Lowest activity: %s
                             Easiest course: %s
                             Hardest course: %s
                             """, (Object[]) summaryStats); // casting to suppress warning about varargs vs single-arg
        while (true) {
            String courseNameEntered = scanner.nextLine();
            if (courseNameEntered.equals("back")) {
                break;
            } else {
                if (isCourseNameInputValid(courseNameEntered)) {
                    List<Student> topStudents = app.topStudents(courseNameEntered);

                    System.out.println(courseNameEntered);
                    System.out.println("id     points   completed");
                    if (!topStudents.isEmpty()) {
                        topStudents.forEach(s -> System.out.printf("%-6s %-8s %-2.1f%%\n", // %%: literal %
                                s.getId(), s.getPointsForCourse(courseNameEntered), s.getProgress(courseNameEntered)));
                    }
                } else {
                    System.out.println("Unknown course.");
                }
            }
        }
    }

    private void notifyStudents() {
        List<String[]> studentsToNotify = app.notifyStudents();
        for (String[] emailParameters : studentsToNotify) {
            System.out.printf("""
                    To: %s
                    Re: Your Learning Progress
                    Hello, %s You have accomplished our %s course!
                    """, emailParameters);
        }
        System.out.printf("Total %d students have been notified.",
                studentsToNotify.stream()
                        .map(s -> s[0])
                        .distinct()
                        .count());
    }
}
