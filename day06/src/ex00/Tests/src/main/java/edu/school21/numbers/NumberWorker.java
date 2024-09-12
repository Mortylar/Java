package edu.school21.numbers;

import static java.lang.Math.sqrt;

import edu.school21.numbers.exception.IllegalNumberException;

public class NumberWorker {

    private static final int MAX_ILLEGAL_VALUE = 1;

    public boolean isPrime(int number) {
        if (number <= MAX_ILLEGAL_VALUE) {
            throw new IllegalNumberException();
        }

        boolean isPrime = true;
        int div = 2;

        while ((div <= sqrt(number)) && (isPrime)) {
            if ((number % div) == 0) {
                isPrime = false;
            }
            ++div;
        }
        return isPrime;
    }

    public int digitsSum(int number) {
        if (number < 0) {
            number *= -1;
        }
        int result = 0;

        while (number > 0) {
            result += number % 10;
            number = number / 10;
        }

        return result;
    }
}
