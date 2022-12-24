package tracker.app;

import java.util.Objects;

public class Student {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String email;  // Student identity = email, reflected by hashCode() and equals()
    private int[] points;

    public Student(String id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.points = new int[4];
    }

    public String getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public int[] getPoints() {
        return points;
    }
    public void setPoints(String[] points) {
        for (int i = 0; i < points.length; i++) {
            this.points[i] += Integer.parseInt(points[i]); // InputValidators.isPointsInputValid() guarantees no exception
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return email.equals(student.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
