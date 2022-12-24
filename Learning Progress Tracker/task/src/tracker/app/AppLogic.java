package tracker.app;

import tracker.ui.TextUserInterface;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static tracker.ui.InputValidators.parsePointsInput;
import static tracker.ui.InputValidators.parseStudentDataInput;

public class AppLogic {
    private final Map<String, Student> studentDB;
    private static int id = 10000;

    public AppLogic() {
        studentDB = new HashMap<>();
    }

    public void start() {
        TextUserInterface ui = new TextUserInterface(this);
        ui.start();
    }

    private String getNextStudentId() {
        return String.valueOf(id++);
    }

    public boolean addStudent(String studentCredentialsEntered) {
        String[] studentPersonalData = parseStudentDataInput(studentCredentialsEntered);
        String nextStudentId = getNextStudentId();
        Student student = new Student(nextStudentId, studentPersonalData[0], studentPersonalData[1], studentPersonalData[2]);
        if (studentDB.containsValue(student)) {
            return false;
        } else {
            studentDB.put(nextStudentId, student);
            return true;
        }
    }

    public Set<String> listStudents() {
        return studentDB.keySet();
    }

    public boolean updatePointsOfStudent(String pointsEntered) {
        String[] pointsData = parsePointsInput(pointsEntered);
        if (studentDB.containsKey(pointsData[0])) {
            Student student = studentDB.get(pointsData[0]);
            student.setPoints(Arrays.copyOfRange(pointsData, 1, 5));
            return true;
        } else {
            return false;
        }
    }

    public String findStudent(String studentId) {
        if (studentDB.containsKey(studentId)) {
            Student s = studentDB.get(studentId);
            int[] points = s.getPoints();
            return String.format("%s points: Java=%d; DSA=%d; Databases=%d; Spring=%d",
                    studentId, points[0], points[1], points[2], points[3]);
        } else {
            return String.format("No student is found for id=%s", studentId);
        }
    }
}
