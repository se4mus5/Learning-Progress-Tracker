package tracker.ui;

public class InputValidators {
    static boolean isInputStructureValid(String studentCredentialsEntered) {
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
}
