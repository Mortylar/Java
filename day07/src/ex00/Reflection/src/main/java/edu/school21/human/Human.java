package edu.school21.human;

public class Human {

    private static final String DEF_COLOR = "GREEN";
    private static final double DEF_PRICE = 100.0;
    private static final int DEF_AGE = 21;
    private static final int comingOfAge = 18;

    private String color;
    private int age;
    private double price;

    {
        this.color = DEF_COLOR;
        this.age = DEF_AGE;
        this.price = DEF_PRICE;
    }

    public Human() {}

    public Human(double price) { this.price = price; }

    public boolean isPossibilityToBuy(double price) {
        if (age < comingOfAge) {
            return false;
        }
        return (price >= this.price);
    }

    @Override
    public String toString() {
        return String.format("Human %s, %d years old, price = %f", this.color,
                             this.age, this.price);
    }
}
