package org.example.Exe5;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class PriorityQueueTests {

    @Test
    void testNullOrEmptyArrays() {
        assertNull(PriorityQueueSort.sort(null));
        assertArrayEquals(new int[0], PriorityQueueSort.sort(new int[0]));
    }

    @ParameterizedTest
    @MethodSource("provideBasicArrays")
    void testBasicSorting(int[] input, int[] expected) {
        assertArrayEquals(expected, PriorityQueueSort.sort(input));
    }

    static Stream<org.junit.jupiter.params.provider.Arguments> provideBasicArrays() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of(new int[]{5}, new int[]{5}),
                org.junit.jupiter.params.provider.Arguments.of(new int[]{2, 1}, new int[]{1, 2}),
                org.junit.jupiter.params.provider.Arguments.of(
                        new int[]{7, 2, 9, 4, 1}, new int[]{1, 2, 4, 7, 9}
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideEdgeCases")
    void testEdgeCases(int[] input, int[] expected) {
        assertArrayEquals(expected, PriorityQueueSort.sort(input));
    }

    static Stream<org.junit.jupiter.params.provider.Arguments> provideEdgeCases() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of(
                        new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE, 0},
                        new int[]{Integer.MIN_VALUE, 0, Integer.MAX_VALUE}
                ),
                org.junit.jupiter.params.provider.Arguments.of(
                        new int[]{3, 1, 2, 1, 3},
                        new int[]{1, 1, 2, 3, 3}
                ),
                org.junit.jupiter.params.provider.Arguments.of(
                        new int[]{-5, 0, 5, -1},
                        new int[]{-5, -1, 0, 5}
                ),
                org.junit.jupiter.params.provider.Arguments.of(
                        new int[]{1, 2, 3, 4},
                        new int[]{1, 2, 3, 4}
                ),
                org.junit.jupiter.params.provider.Arguments.of(
                        new int[]{4, 3, 2, 1},
                        new int[]{1, 2, 3, 4}
                ));}

    @Test
    void testLargeRandomArray() {

        int size = 500;
        int[] input = new int[size];
        for (int i = 0; i < size; i++) {
            input[i] = (size - i) % 50;
        }
        int[] result = PriorityQueueSort.sort(input);
        assertEquals(size, result.length);
        for (int i = 0; i < size - 1; i++) {
            assertTrue(result[i] <= result[i + 1]);
        }
    }
}
