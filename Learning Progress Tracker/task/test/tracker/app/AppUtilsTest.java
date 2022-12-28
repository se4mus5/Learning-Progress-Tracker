package tracker.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static tracker.app.AppUtils.*;

class AppUtilsTest {

    @Test
    void arrayAggregatorAsSumTest() {
        int[] aggregatorArray = new int[] {1, 2, 3, 4, 5};
        int[] incrementArray = new int[] {2, 2, 2, 2, 2};
        int[] expectedArray = new int[] {3, 4, 5, 6, 7};
        arrayAggregatorAsSum(aggregatorArray, incrementArray);
        assertArrayEquals(expectedArray, aggregatorArray);
    }

    @Test
    void arrayAggregatorAsCountAdd() {
        int[] aggregatorArray = new int[] {1, 2, 3, 4, 5};
        int[] incrementArray = new int[] {2, 2, 2, 2, 2};
        int[] expectedArray = new int[] {2, 3, 4, 5, 6};
        arrayAggregatorAsCount(aggregatorArray, incrementArray);
        assertArrayEquals(expectedArray, aggregatorArray);
    }

    @Test
    void arrayAggregatorAsCountDoNotAdd() {
        int[] aggregatorArray = new int[] {1, 2, 3, 4, 5};
        int[] incrementArray = new int[] {0, 0, 0, 0, 0};
        int[] expectedArray = new int[] {1, 2, 3, 4, 5};
        arrayAggregatorAsCount(aggregatorArray, incrementArray);
        assertArrayEquals(expectedArray, aggregatorArray);
    }

    @Test
    void maxPositionsOfArraySingleMax() {
        int[] array = new int[] {1, 17, 3, 1, 4, 9};
        int[] maxPositions = maxPositionsOfArray(array);
        int[] expectedMaxPositions = new int[] {1};
        assertArrayEquals(expectedMaxPositions, maxPositions);
    }

    @Test
    void maxPositionsOfArrayMultipleMax() {
        int[] array = new int[] {1, 17, 17, 1, 4, 17};
        int[] maxPositions = maxPositionsOfArray(array);
        int[] expectedMaxPositions = new int[] {1, 2, 5};
        assertArrayEquals(expectedMaxPositions, maxPositions);
    }

    @Test
    void minPositionsOfArraySingleMin() {
        int[] array = new int[] {4, 7, 2, 512, 31};
        int[] minPositions = minPositionsOfArray(array);
        int[] expectedMinPositions = new int[] {2};
        assertArrayEquals(expectedMinPositions, minPositions);
    }

    @Test
    void minPositionsOfArrayMultipleMin() {
        int[] array = new int[] {4, 1, 12, 1, 31};
        int[] minPositions = minPositionsOfArray(array);
        int[] expectedMinPositions = new int[] {1, 3};
        assertArrayEquals(expectedMinPositions, minPositions);
    }
    @Test
    void positionToCourseNameTest() {
        assertEquals("Java", positionToCourseName(0));
    }
}