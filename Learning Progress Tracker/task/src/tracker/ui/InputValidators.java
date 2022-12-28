package tracker.ui;

import tracker.app.Course;
import tracker.app.LogEntry;

import java.util.Arrays;

public class InputValidators {
    static boolean isStudentDataInputStructureValid(String studentCredentialsEntered) {
        return studentCredentialsEntered.strip().split(" ").length >= 3;
    }

    static boolean isNameValid(String name) {
        String invalidFront = "^[-'].*$";
        String invalidTail = ".*[-']$";
        return (!name.matches(invalidFront) && !name.matches(invalidTail))
                && (name.length() >= 2)
                && !name.contains("-'") && !name.contains("'-")
                && !name.matches(".*[-']{2,}.*$");
    }

    static boolean isValidFirstName(String firstName) {
        if (!isNameValid(firstName))
            return false;
        return firstName.matches("^[A-Za-z'-]*$");
    }

    static boolean isValidLastName(String lastName) {
        if (!isNameValid(lastName))
            return false;
        return lastName.matches("^[A-Za-z' -]*$");
    }

    static boolean isValidEmail(String email) {
        return !email.contains(" ") && email.contains("@") && email.contains(".")
                && email.lastIndexOf("@") <= email.lastIndexOf(".")
                && email.indexOf('@') == email.lastIndexOf('@')
                && !email.matches(".*[.]{2,}.*$");
    }

    static boolean isStudentDataInputValid(String studentCredentialsEntered) {
        String firstName;
        String lastName;
        String email;

        String[] credentialsEntered;
        if (!isStudentDataInputStructureValid(studentCredentialsEntered)) {
            System.out.println("Incorrect credentials.");
            return false;
        }

        credentialsEntered = parseStudentDataInput(studentCredentialsEntered);
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

    static boolean isPointsInputValid(String pointsEntered) {
        return pointsEntered.matches("^\\w+(?:\\s+\\d+){4}$");
    }

    static boolean isCourseNameInputValid(String courseNameEntered) {
        return Arrays.stream(Course.values()).anyMatch((t) -> t.name().equals(courseNameEntered));
    }

    public static String[] parseStudentDataInput(String studentCredentialsEntered) {
        String firstName = studentCredentialsEntered.substring(0, studentCredentialsEntered.indexOf(" "));
        String lastName = studentCredentialsEntered.substring(studentCredentialsEntered.indexOf(" ") + 1,
                studentCredentialsEntered.lastIndexOf(" "));
        String email = studentCredentialsEntered.substring(studentCredentialsEntered.lastIndexOf(" ") + 1);
        return new String[] {firstName, lastName, email};
    }

    public static LogEntry parsePointsInput(String pointsEntered) {
        String[] pointsData = pointsEntered.split(" ");
        String studentName = pointsData[0];
        int[] studentPoints = new int[pointsData.length - 1];
        Arrays.setAll(studentPoints, i -> studentPoints[i] = Integer.parseInt(pointsData[i + 1]));
        return new LogEntry(studentName, studentPoints);
    }
}
