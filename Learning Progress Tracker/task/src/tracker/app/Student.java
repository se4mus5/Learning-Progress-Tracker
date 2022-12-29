package tracker.app;

import java.util.Arrays;
import java.util.Objects;

public class Student {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String email;  // Student identity = email, reflected by hashCode() and equals()
    private final int[] points;
    private boolean notified;

    public Student(String id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.points = new int[4];
    }

    public String getEmail() { return email; }

    public String getFullName() { return firstName + " " + lastName; }

    public String getId() { return id; }

    public int[] getPoints() { return points; }

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    public int getPointsForCourse(String courseName) { // courseName is validated, will not trigger to exception
        return points[Course.valueOf(courseName).ordinal()];
    }

    public double getProgress(String courseName) { // courseName is validated, will not trigger to exception
        if (getPointsForCourse(courseName) == 0) return 0; // avoid div by 0
        return 100 * getPointsForCourse(courseName) / (double) Course.valueOf(courseName).getPointsRequired();
    }
    public void setPoints(int[] points) {
        // InputValidators.isPointsInputValid() guarantees no exception
        // setting each element instead of reassigning array to keep the 'points' variable immutable
        Arrays.setAll(this.points, i -> this.points[i] + points[i]); // model: Arrays.setAll(int[] array, IntUnaryOperator);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return email.equals(student.email); // student identity is email
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    } // student identity is email
}
