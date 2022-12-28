package tracker.app;

import tracker.ui.TextUserInterface;

import java.util.*;

import static tracker.app.AppLogic.Difficulty.*;
import static tracker.app.AppUtils.*;
import static tracker.app.AppUtils.ZeroHandling.*;
import static tracker.ui.InputValidators.parsePointsInput;
import static tracker.ui.InputValidators.parseStudentDataInput;

public class AppLogic {
    private final Map<String, Student> studentDB;
    private final List<LogEntry> pointsEntryLog;
    private static int id;

    public AppLogic() {
        id = 10000;
        studentDB = new HashMap<>();
        pointsEntryLog = new ArrayList<>();
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
        LogEntry logEntry = parsePointsInput(pointsEntered);

        if (studentDB.containsKey(logEntry.studentId())) {
            pointsEntryLog.add(logEntry);
            Student student = studentDB.get(logEntry.studentId());
            student.setPoints(logEntry.points());
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

    public String[] summaryStats() {
        // Most popular course has the biggest number of enrolled students: count non-0 in studentDB.values()
        // Higher student activity means a bigger number of completed tasks: count non-0 in pointsEntryLog
        // Easiest course has the highest average grade per task: sum non-0 / count non-0 in pointsEntryLog

        String[] summaryStats = new String[6];
        summaryStats[0] = mostPopularCourse();
        summaryStats[1] = leastPopularCourse();
        summaryStats[2] = highestActivityCourse();
        summaryStats[3] = lowestActivityCourse();
        summaryStats[4] = courseByDifficulty(EASIEST);
        summaryStats[5] = courseByDifficulty(HARDEST);

        // highest cannot be lowest: correct as per guidance in spec
        if (summaryStats[0].equals(summaryStats[1])) summaryStats[1] = "n/a";
        if (summaryStats[2].equals(summaryStats[3])) summaryStats[3] = "n/a";
        if (summaryStats[4].equals(summaryStats[5])) summaryStats[5] = "n/a";

        return summaryStats;
    }

    enum Difficulty { EASIEST, HARDEST }

    String courseByDifficulty (Difficulty difficulty) {
        if (pointsEntryLog.isEmpty()) return "n/a";

        int[] aggregatedPoints = new int[pointsEntryLog.get(0).points().length]; // at this point we know that element 0 exists
        int[] aggregatedCounts = new int[aggregatedPoints.length];
        pointsEntryLog.forEach(entry -> { arrayAggregatorAsSum(aggregatedPoints, entry.points());
            arrayAggregatorAsCount(aggregatedCounts, entry.points()); });

        double[] averagePointsForEachCourse = new double[aggregatedPoints.length];
        // model: Arrays.setAll(int[] array, IntUnaryOperator); // keep variable immutable
        Arrays.setAll(averagePointsForEachCourse,
                i -> averagePointsForEachCourse[i] =
                        aggregatedCounts[i] == 0 ? 0 : aggregatedPoints[i] / (double) aggregatedCounts[i]); // avoid division by 0

        int[] courseIds = difficulty == EASIEST ? maxPositionsOfArray(averagePointsForEachCourse) // call for producing EASIEST course IDs
                : minPositionsOfArray(averagePointsForEachCourse, EXCLUDE_ZEROS); // call for producing HARDEST course IDs
        return courseIdsToCommaSeparatedCourseNames(courseIds);
    }

    String highestActivityCourse() {
        if (pointsEntryLog.isEmpty()) return "n/a";

        int[] courseActivityCount = new int[pointsEntryLog.get(0).points().length]; // at this point we know that element 0 exists
        pointsEntryLog.forEach(entry -> arrayAggregatorAsCount(courseActivityCount, entry.points()));

        int[] mostActiveCourseIds = maxPositionsOfArray(courseActivityCount);
        return courseIdsToCommaSeparatedCourseNames(mostActiveCourseIds);
    }

    String lowestActivityCourse() {
        if (pointsEntryLog.isEmpty()) return "n/a";

        int[] courseActivityCount = new int[pointsEntryLog.get(0).points().length]; // at this point we know that element 0 exists
        pointsEntryLog.forEach(entry -> arrayAggregatorAsCount(courseActivityCount, entry.points()));

        int[] leastActiveCourseIds = minPositionsOfArray(courseActivityCount);
        return courseIdsToCommaSeparatedCourseNames(leastActiveCourseIds);
    }

    String mostPopularCourse() {
        if (studentDB.isEmpty()) return "n/a";

        int[] coursePopularityCount = new int[pointsEntryLog.get(0).points().length]; // at this point we know that element 0 exists
        studentDB.values().forEach(student -> arrayAggregatorAsCount(coursePopularityCount, student.getPoints()));

        int[] mostPopularCourseIds = maxPositionsOfArray(coursePopularityCount);
        return courseIdsToCommaSeparatedCourseNames(mostPopularCourseIds);
    }

    String leastPopularCourse() {
        if (studentDB.isEmpty()) return "n/a";

        int[] coursePopularityCount = new int[pointsEntryLog.get(0).points().length]; // at this point we know that element 0 exists
        studentDB.values().forEach(student -> arrayAggregatorAsCount(coursePopularityCount, student.getPoints()));

        int[] leastPopularCourseIds = minPositionsOfArray(coursePopularityCount);
        return courseIdsToCommaSeparatedCourseNames(leastPopularCourseIds);
    }

    String courseIdsToCommaSeparatedCourseNames(int[] courseIds) {
        StringBuilder mostPopularCourse = new StringBuilder();
        for (int courseId : courseIds) {
            mostPopularCourse.append(positionToCourseName(courseId)).append(", ");
        }
        return mostPopularCourse.substring(0, mostPopularCourse.lastIndexOf(","));
    }

    // Create dynamic comparator to sort on multiple criteria:
    // https://stackoverflow.com/questions/50432418/passing-an-argument-to-a-comparator-java
    // https://javarevisited.blogspot.com/2021/09/comparator-comparing-thenComparing-example-java-.html
    Comparator<Student> getComparator(String course) {
        return Comparator
                .comparing((Student s) -> s.getPointsForCourse(course), Comparator.reverseOrder()) // course points descending
                .thenComparing(Student::getId); // student ID ascending
    }

    public List<Student> topStudents(String courseName) {
        if (studentDB.isEmpty()) return new ArrayList<>();

        List<Student> topStudentCandidates = studentDB.values()
                .stream()
                .sorted(getComparator(courseName))
                // .limit(3) // this doesn't work: disregards ties
                .toList();

        // tie handling logic is not pretty, reverse-engineered submission tests as specs were unclear
        int stopCounter = 0;
        List<Student> topStudents = new ArrayList<>();
        for (Student s : topStudentCandidates) {
            boolean haltSignal = false;
            if (s.getPointsForCourse(courseName) == 0) { // no progress should not be counted as top
                break;
            }
            if (topStudents.isEmpty()) {
                topStudents.add(s);
                stopCounter++;
            } else if (s.getPointsForCourse(courseName) == topStudents.get(topStudents.size() - 1).getPointsForCourse(courseName)) { // compare with last element
                topStudents.add(s);
                haltSignal = true;
            } else {
                topStudents.add(s);
                stopCounter++;
            }
            if (stopCounter >= 3 && haltSignal) break;
        }

        return topStudents;
    }
}
