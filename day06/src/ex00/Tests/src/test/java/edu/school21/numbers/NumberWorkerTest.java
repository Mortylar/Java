package edu.school21.numbers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.school21.numbers.exception.IllegalNumberException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Unit test for simple App.
 */
public class NumberWorkerTest {

    private NumberWorker worker_ = new NumberWorker();

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 19, 31, 3559, 27644437})
    public void isPrimeForPrimes(int number) {
        assertEquals(worker_.isPrime(number), true);
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 6, 10, 21, 33, 22341, 524289, 11111111})
    public void isPrimeForNotPrimes(int number) {
        assertEquals(worker_.isPrime(number), false);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 0, -1, -2, -3, -4, -22341, -3559})
    public void isPrimeForIncorrectNumbers(int number) {
        assertThrows(IllegalNumberException.class,
                     () -> worker_.isPrime(number));
    }

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    // public AppTest(String testName) { super(testName); }

    /**
     * @return the suite of tests being tested
     */
    // public static Test suite() { return new TestSuite(AppTest.class); }

    /**
     * Rigourous Test :-)
     */
    // public void testApp() { assertTrue(true); }
}
