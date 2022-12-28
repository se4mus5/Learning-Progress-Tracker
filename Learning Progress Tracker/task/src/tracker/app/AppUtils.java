package tracker.app;

import java.util.Arrays;

import static tracker.app.AppUtils.ZeroHandling.*;

public class AppUtils {
    // static utility methods

    // aggregates values of incrementArray into aggregatorArray
    static void arrayAggregatorAsSum(int[] aggregatorArray, int[] incrementArray) {
        // Arrays.setAll(int[] array, IntUnaryOperator);
        Arrays.setAll(aggregatorArray, i -> aggregatorArray[i] + incrementArray[i]);
    }

    // aggregates counts of nonzero values of incrementArray into aggregatorArray
    static void arrayAggregatorAsCount(int[] aggregatorArray, int[] incrementArray) {
        // Arrays.setAll(int[] array, IntUnaryOperator);
        Arrays.setAll(aggregatorArray, i -> aggregatorArray[i] + (incrementArray[i] > 0 ? 1 : 0));
    }

    // returns positions of maximum value of an array
    static int[] maxPositionsOfArray (double[] array) {
        double max = Double.MIN_VALUE;
        int maxCount = 0;
        for (double n : array) {
            if (n > max) {
                max = n;
                maxCount = 1;
            } else if (n == max) {
                maxCount++;
            }
        }

        int[] positionsOfMaxValue = new int[maxCount];
        int cursor = 0; // points to next item position of positionsOfMaxValue
        for (int i = 0; i < array.length; i++) {
            if (array[i] == max) {
                positionsOfMaxValue[cursor] = i;
                cursor++;
            }
        }

        return positionsOfMaxValue;
    }

    static int[] maxPositionsOfArray (int[] array) {
        double[] doubleArray = Arrays.stream(array).asDoubleStream().toArray();
        return maxPositionsOfArray(doubleArray);
    }

    enum ZeroHandling { EXCLUDE_ZEROS, INCLUDE_ZEROS }
    // returns positions of minimum value of an array
    static int[] minPositionsOfArray (double[] array, ZeroHandling zeroHandling) {
        double min = Double.MAX_VALUE;
        int minCount = 0;
        if (zeroHandling == EXCLUDE_ZEROS) {
            for (double n : array) {
                if (n < min && n > 0) {
                    min = n;
                    minCount = 1;
                } else if (n == min) {
                    minCount++;
                }
            }
        } else {  // zeroHandling == INCLUDE_ZEROS
            for (double n : array) {
                if (n < min && n >= 0) {
                    min = n;
                    minCount = 1;
                } else if (n == min) {
                    minCount++;
                }
            }
        }

        int[] positionsOfMinValue = new int[minCount];
        int cursor = 0; // points to next item position of positionsOfMinValue
        for (int i = 0; i < array.length; i++) {
            if (array[i] == min) {
                positionsOfMinValue[cursor] = i;
                cursor++;
            }
        }

        return positionsOfMinValue;
    }

    static int[] minPositionsOfArray (int[] array) {
        double[] doubleArray = Arrays.stream(array).asDoubleStream().toArray();
        return minPositionsOfArray(doubleArray, INCLUDE_ZEROS); // for all usages of (int[]) signature, INCLUDE_ZEROS behavior is expected
    }

    static String positionToCourseName(int courseId) {
        return switch (courseId) {
            case 0 -> "Java";
            case 1 -> "DSA";
            case 2 -> "Databases";
            case 3 -> "Spring";
            default -> "Unknown course.";
        };
    }
}
