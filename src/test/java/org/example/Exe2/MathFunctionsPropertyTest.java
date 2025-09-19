package org.example.Exe2;

import net.jqwik.api.*;
import net.jqwik.api.constraints.*;
import net.jqwik.api.lifecycle.BeforeTry;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

class MathFunctionsPropertyTest {

    @Mock
    private MathLogger mockLogger;

    private MathFunctions mathFunctions;

    @BeforeTry
    void setUp() {
        MockitoAnnotations.openMocks(this);
        doNothing().when(mockLogger).log(anyString(), any(int[].class));
        mathFunctions = new MathFunctions(mockLogger);
    }

    // 1.a. MultiplyByTwo: valide que o resultado é sempre par
    @Property
    void multiplyByTwoAlwaysReturnEvenNumber(@ForAll int number) {
        int result = mathFunctions.multiplyByTwo(number);
        assertEquals(0, result % 2, "Result should always be even");
    }

    // 1.b. GenerateMultiplicationTable: valide que todos os elementos são múltiplos do número original
    @Property
    void generateMultiplicationTableAllElementsAreMultiples(
            @ForAll @IntRange(min = 1, max = 50) int number,
            @ForAll @IntRange(min = 1, max = 10) int limit) {

        int[] table = mathFunctions.generateMultiplicationTable(number, limit);

        assertEquals(limit, table.length, "Table should have correct size");

        for (int i = 0; i < table.length; i++) {
            assertEquals(0, table[i] % number, "Element " + i + " should be multiple of " + number);
            assertEquals(number * (i + 1), table[i], "Element " + i + " should equal number * (i+1)");
        }
    }

    // 1.c. IsPrime: valide que para qualquer número primo, não há divisores além de 1 e ele mesmo
    @Property
    void primeNumbersHaveOnlyTwoDivisors(@ForAll("knownPrimes") int prime) {
        boolean result = mathFunctions.isPrime(prime);
        assertTrue(result, prime + " should be identified as prime");

        // Verifica que não há divisores além de 1 e ele mesmo
        for (int i = 2; i < prime; i++) {
            assertNotEquals(0, prime % i, prime + " should not be divisible by " + i);
        }
    }

    // 1.d. CalculateAverage: verifique se o resultado está sempre entre o menor e o maior valor do array
    @Property
    void calculateAverageIsBetweenMinAndMax(@ForAll("nonEmptyIntArrays") int[] numbers) {
        double average = mathFunctions.calculateAverage(numbers);

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int num : numbers) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }

        assertTrue(average >= min, "Average should be >= minimum value");
        assertTrue(average <= max, "Average should be <= maximum value");
    }

    // Geradores personalizados
    @Provide
    Arbitrary<Integer> knownPrimes() {
        return Arbitraries.of(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47);
    }

    @Provide
    Arbitrary<Integer> compositeNumbers() {
        return Arbitraries.of(4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20, 21, 22, 24, 25, 26, 27, 28);
    }

    @Provide
    Arbitrary<int[]> nonEmptyIntArrays() {
        return Arbitraries.integers().between(-100, 100)
                .array(int[].class)
                .ofMinSize(1)
                .ofMaxSize(10);
    }
}
