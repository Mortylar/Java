package statistic;

import java.util.ArrayList;
import java.util.Scanner;

public class Statistic {

    private static final int maxGrade_ = 9;
    private static final int minGrade_ = 1;
    private static final int gradeInWeek_ = 5;
    private static final int maxWeekNumber_ = 18;
    private static final int endInput_ = 42;

    private ArrayList<Integer> minimumGrade_;
    private Scanner scanner_;


    public Statistic() {
	    scanner_ = new Scanner(System.in);
      minimumGrade_ = new ArrayList<>();
    }


    private int readWeekPreamble(int weekId) {
        String targetStr = "Week " + weekId;
		    String endStr = String.valueOf(endInput_);
        String inputString = scanner_.nextLine();

		    if (inputString.equals(endStr)) {
					return endInput_;
				}

		    if ((!inputString.equals(targetStr)) || (weekId > maxWeekNumber_)) {
		        System.err.println("Illegal Argument");
			      System.exit(-1);
		    }
		    return 0;
	  }

    private int readGradeAndGetMinimum() {
        int minGrade = 9;
		    for (int i = 0; i < gradeInWeek_; ++i) {
		        int thisGrade = scanner_.nextInt();
			      if (thisGrade < minGrade) minGrade = thisGrade;
		    }
		    scanner_.nextLine();
		    return minGrade;
	}

    private void collectStatistic() {
        int weekId = 1;
	      while(readWeekPreamble(weekId) != endInput_) {
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
