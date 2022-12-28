package tracker.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void getPoints() {
        Student s = new Student("99999", "Kata", "Kis", "kiskata@cmail.com");
        s.setPoints(new int[] {4, 5, 6, 7});
        assertEquals(5, s.getPointsForCourse("DSA"));
    }

    @Test
    void getProgress() {
        Student s = new Student("99999", "Kata", "Kis", "kiskata@cmail.com");
        s.setPoints(new int[] {150, 400, 0, 0});
        assertEquals(100.0, s.getProgress("DSA"));
        assertEquals(25.0, s.getProgress("Java"));
        assertEquals(0.0, s.getProgress("Spring"));
    }
}