package number;

import static java.lang.Math.sqrt;

import java.util.Scanner;

public class Number {

    private static final int DEFAULT_VALUE = 0;
    private int number_;
    private int iterationCount_;

    public Number() { setNumber(DEFAULT_VALUE); }

    public Number(int number) { setNumber(number); }

    public void setNumber(int number) {
        number_ = number;
        iterationCount_ = 0;
    }

    public int readNumber() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public void writeNumber(int number) { System.out.println(number); }

    public int getIterationCount() { return iterationCount_; }

    public boolean isPrime() {
        boolean isPrime = true;
        int div = 2;
        iterationCount_ = 0;

        while ((div <= sqrt(number_)) && (isPrime)) {
            if ((number_ % div) == 0) {
                isPrime = false;
            }
            ++div;
            ++iterationCount_;
        }
        return isPrime;
    }

    public int digitsSum() {
        int tmp = number_;
        int result = 0;

        while (tmp > 0) {
            result += tmp % 10;
            tmp = tmp / 10;
        }
        return result;
    }
}
