package statistic;

import java.util.ArrayList;
import java.util.Scanner;

public class Statistic {
  public Statistic() {
	  scanner_ = new Scanner(System.in);
		minimum_grade_ = new ArrayList<>();
	}

  private ArrayList<Integer> minimum_grade_;
	private static final int max_grade_ = 9;
  private static final int min_grade_ = 1;
	private static final int grade_in_week_ = 5;
	private static final int max_week_number_ = 18;
	private static final int end_input_ = 42;

  private Scanner scanner_;

	private int ReadWeekPreamble(int week_id) {
		String target_str = "Week " + week_id;
		String end_str = String.valueOf(end_input_);
    String input_string = scanner_.nextLine();
		if (input_string.equals(end_str)) return end_input_;
		if ((!input_string.equals(target_str)) || (week_id > max_week_number_)) {
		  System.err.println("Illegal Argument");
			System.exit(-1);
		}
		return 0;
	}

	private int ReadGradeAndGetMinimum() {
	  int min_grade = 9;
		for (int i = 0; i < grade_in_week_; ++i) {
		  int this_grade = scanner_.nextInt();
			if (this_grade < min_grade) min_grade = this_grade;
		}
		scanner_.nextLine();
		return min_grade;
	}

	private void CollectStatistic() {
		int week_id = 1;
	  while(ReadWeekPreamble(week_id) != end_input_) {
		  minimum_grade_.add(ReadGradeAndGetMinimum());
			++week_id;
		}
	}

	public void PrintStatistic() {
		CollectStatistic();
	  for (int i = 0; i < minimum_grade_.size(); ++i) {
			System.out.printf("Week %d ", i + 1);
			for (int j = 0; j < minimum_grade_.get(i); ++j) {
		    System.out.print("=");
		  }
		System.out.println(">");
		}
	}
}
