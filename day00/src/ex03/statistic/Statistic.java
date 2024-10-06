package statistic;

import java.util.ArrayList;
import java.util.Scanner;

public class Statistic {

    private static final int MAX_GRADE = 9;
    private static final int MIN_GRADE = 1;
    private static final int GRADE_IN_WEEK = 5;
    private static final int MAX_WEEK_NUMBER = 18;
    private static final int END_INPUT = 42;

    private ArrayList<Integer> minimumGrade_;
    private Scanner scanner_;

    public Statistic() {
        scanner_ = new Scanner(System.in);
        minimumGrade_ = new ArrayList<>();
    }

    private int readWeekPreamble(int weekId) {
        String targetStr = "Week " + weekId;
        String endStr = String.valueOf(END_INPUT);
        String inputString = scanner_.nextLine();

        if (inputString.equals(endStr)) {
            return END_INPUT;
        }

        if ((!inputString.equals(targetStr)) || (weekId > MAX_WEEK_NUMBER)) {
            System.err.println("Illegal Argument");
            System.exit(-1);
        }
        return 0;
    }

    private int readGradeAndGetMinimum() {
        int minGrade = 9;
        for (int i = 0; i < GRADE_IN_WEEK; ++i) {
            int thisGrade = scanner_.nextInt();
            if (thisGrade < minGrade)
                minGrade = thisGrade;
        }
        scanner_.nextLine();
        return minGrade;
    }

    private void collectStatistic() {
        int weekId = 1;
        while (readWeekPreamble(weekId) != END_INPUT) {
            minimumGrade_.add(readGradeAndGetMinimum());
            ++weekId;
        }
    }

    public void printStatistic() {
        collectStatistic();
        for (int i = 0; i < minimumGrade_.size(); ++i) {
            System.out.printf("Week %d ", i + 1);
            for (int j = 0; j < minimumGrade_.get(i); ++j) {
                System.out.print("=");
            }
            System.out.println(">");
        }
    }
}
