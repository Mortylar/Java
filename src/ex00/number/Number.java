package number;

import java.util.Scanner;

public class Number {

    private int number_;

    public Number() {
        setNumber(0);
    }

    public Number(int number) {
        setNumber(number);
    }

    public void setNumber(int number) {
        number_ = number;
    }

    public int readNumber() {
        Scanner scanner = new Scanner(System.in);

        return scanner.nextInt();
    }

    public void writeNumber(int number) {
        System.out.println(number);
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
