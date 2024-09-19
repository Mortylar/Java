package edu.school21.data;

import java.util.Scanner;

public class DataReader {

    private Scanner scanner = new Scanner(System.in);

    public Object readData(Class type) {
        if (type.equals(String.class)) {
            return readString();
        }

        if (type.equals(int.class)) {
            return readInt();
        }

        if (type.equals(double.class)) {
            return readDouble();
        }

        if (type.equals(boolean.class)) {
            return readBoolean();
        }

        if (type.equals(long.class)) {
            // return readLong();
        }
        return null;
    }

    public String readString() {
        if (this.scanner.hasNextLine()) {
            return this.scanner.nextLine();
        }
        return null;
    }

    public int readInt() {
        while (!this.scanner.hasNextInt()) {
            System.err.printf("\nIncorrect type - try again\n");
            this.scanner.next();
        }
        int number = this.scanner.nextInt();
        return number;
    }

    public double readDouble() {
        while (!this.scanner.hasNextDouble()) {
            System.err.printf("\nIncorrect type - try again\n");
            this.scanner.next();
        }
        return this.scanner.nextDouble();
    }

    public boolean readBoolean() {
        while (!this.scanner.hasNextBoolean()) {
            System.err.printf("\nIncorrect type - try again\n");
            this.scanner.next();
        }
        return this.scanner.nextBoolean();
    }

    public long readLong() {
        while (!this.scanner.hasNextLong()) {
            System.err.printf("\nIncorrect type - try again\n");
            this.scanner.next();
        }
        return this.scanner.nextLong();
    }
}
