package tracker.ui;

public class InputValidators {
    static boolean isStudentDataInputStructureValid(String studentCredentialsEntered) {
        return studentCredentialsEntered.strip().split("\s").length >= 3;
    }

    static boolean isNameValid(String name) {
        String invalidFront = "^[-'].*$";
        String invalidTail = ".*[-']$";
        if ((name.matches(invalidFront) || name.matches(invalidTail))
                || (name.length() < 2)
                || name.contains("-'") || name.contains("'-")
                || name.matches(".*[-']{2,}.*$")) {
            return false;
        }
        return true;
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
        if (email.contains("\s") || !email.contains("@") || !email.contains(".")
            || email.lastIndexOf("@") > email.lastIndexOf(".")
            || email.indexOf('@') != email.lastIndexOf('@')
            || email.matches(".*[.]{2,}.*$")) return false;
        return true;
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

    public static String[] parseStudentDataInput(String studentCredentialsEntered) {
        String firstName = studentCredentialsEntered.substring(0, studentCredentialsEntered.indexOf("\s"));
        String lastName = studentCredentialsEntered.substring(studentCredentialsEntered.indexOf("\s") + 1,
                studentCredentialsEntered.lastIndexOf("\s"));
        String email = studentCredentialsEntered.substring(studentCredentialsEntered.lastIndexOf("\s") + 1);
        return new String[] {firstName, lastName, email};
    }

    public static String[] parsePointsInput(String pointsEntered) {
        return pointsEntered.split(" ");
    }
}
