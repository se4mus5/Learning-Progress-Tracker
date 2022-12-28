package tracker.app;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppLogicTest {
    // TODO add @DisplayName, convert some to ParametrizedTest

    @Test
    void mostPopularCourseTestSingleCourseMostPopular() {
        AppLogic app = new AppLogic();
        app.addStudent("Kis Kata kiskata@cmail.com");
        app.addStudent("Kis Semi semike@cmail.com");
        app.addStudent("Kis Finni finnecske@cmail.com");
        app.updatePointsOfStudent("10000 4 0 0 0");
        app.updatePointsOfStudent("10001 6 0 0 0");
        app.updatePointsOfStudent("10002 10 0 0 0");

        assertEquals("Java", app.mostPopularCourse());
    }

    @Test
    void mostPopularCourseTestMultipleCoursesMostPopular() {
        AppLogic app = new AppLogic();
        app.addStudent("Kis Kata kiskata@cmail.com");
        app.addStudent("Kis Semi semike@cmail.com");
        app.addStudent("Kis Finni finnecske@cmail.com");
        app.updatePointsOfStudent("10000 4 0 6 0");
        app.updatePointsOfStudent("10001 6 0 10 0");
        app.updatePointsOfStudent("10002 10 0 4 0");

        assertEquals("Java, Databases", app.mostPopularCourse());
    }

    @Test
    void summaryStatsTestGeneral() {
        AppLogic app = new AppLogic();
        app.addStudent("Kis Kata kiskata@cmail.com");
        app.addStudent("Kis Semi semike@cmail.com");
        app.addStudent("Kis Finni finnecske@cmail.com");
        app.updatePointsOfStudent("10000 4 0 0 8");
        app.updatePointsOfStudent("10001 0 0 0 5");
        app.updatePointsOfStudent("10002 0 8 0 4");
        String[] summaryStats = app.summaryStats();

        assertEquals("Spring", summaryStats[0]);
        assertEquals("Databases", summaryStats[1]);
        assertEquals("Spring", summaryStats[2]);
        assertEquals("Databases", summaryStats[3]);
        assertEquals("DSA", summaryStats[4]);
        assertEquals("Java", summaryStats[5]);
    }

    @Test
    void summaryStatsTestAllPointsEntriesEqual() {
        AppLogic app = new AppLogic();
        app.addStudent("Kis Kata kiskata@cmail.com");
        app.addStudent("Kis Semi semike@cmail.com");
        app.addStudent("Kis Finni finnecske@cmail.com");
        app.updatePointsOfStudent("10000 5 4 3 1");
        app.updatePointsOfStudent("10001 5 4 3 1");
        app.updatePointsOfStudent("10002 5 4 3 1");
        String[] summaryStats = app.summaryStats();

        assertEquals("Java, DSA, Databases, Spring", summaryStats[0]);
        assertEquals("n/a", summaryStats[1]);
        assertEquals("Java, DSA, Databases, Spring", summaryStats[2]);
        assertEquals("n/a", summaryStats[3]);
        assertEquals("Java", summaryStats[4]);
        assertEquals("Spring", summaryStats[5]);
    }

    @Test
    void topStudentsTestWithTiesAndNoProgress() {
        AppLogic app = new AppLogic();
        app.addStudent("Kis Kata kiskata@cmail.com");
        app.addStudent("Kis Semi semike@cmail.com");
        app.addStudent("Kis Finni finnecske@cmail.com");
        app.addStudent("Kis Rozika rozika@dmail.com");
        app.updatePointsOfStudent("10000 100 4 3 2");
        app.updatePointsOfStudent("10001 50 4 0 1");
        app.updatePointsOfStudent("10002 5 4 3 2");
        app.updatePointsOfStudent("10003 5 4 3 3");
        List<Student> topJavaStudents = app.topStudents("Java");
        List<Student> topDBStudents = app.topStudents("Databases");
        List<Student> topSpringStudents = app.topStudents("Spring");

        assertArrayEquals(new String[] {"10000", "10001", "10002", "10003"}, topJavaStudents.stream().map(Student::getId).toArray()); // has ties at the end
        assertArrayEquals(new String[] {"10000", "10002", "10003"}, topDBStudents.stream().map(Student::getId).toArray()); // has no progress
        assertArrayEquals(new String[] {"10003", "10000", "10002", "10001"}, topSpringStudents.stream().map(Student::getId).toArray()); // has ties in the middle
    }
}