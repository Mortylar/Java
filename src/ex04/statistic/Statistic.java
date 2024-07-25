package statistic;

import java.util.ArrayList;
import java.util.Scanner;
import cursor.Cursor;

public class Statistic {
  public Statistic() {
	  scanner_ = new Scanner(System.in);
		letter_frequency_ = new ArrayList<>(ascii_max_capasity_);
		letters_order_ = new ArrayList<>(ascii_max_capasity_);
		frequency_order_ = new ArrayList<>(ascii_max_capasity_);
		cursor_ = new Cursor();
	}

  private ArrayList<Integer> letter_frequency_; //size() <= 65535
	private ArrayList<Integer> letters_order_;
	private ArrayList<Integer> frequency_order_;
	private static final int ascii_max_capasity_ = 127;
	private static final int max_frequency_ = 999;
	private static final int top_number_ = 10;
  
	private Scanner scanner_;
	private Cursor cursor_;

  private String ReadString() {
	  return scanner_.nextLine();
	}

	private void CalculateFrequency(String str) {
	  for(int i = 0; i < str.length(); ++i) {
		  IncLetterFrequency(str.charAt(i));
		}
	}

	private void IncLetterFrequency(int letter) {
	  while (letter_frequency_.size() <= letter) {
      letter_frequency_.add(0);
		}
		letter_frequency_.set(letter, 1 + letter_frequency_.get(letter));
	}

  private int GetMaxFrequencyBelowTheBorder(int border) {
	  int max = 0;
		for (int i = 0; i < letter_frequency_.size(); ++i) {
		  int cur = letter_frequency_.get(i);
			if ((cur > max) && (cur < border)) max = cur;
		}
		return max;
	}


	private void Sort() {
		int this_max = max_frequency_ + 1;

		for (int top = 0; top < top_number_; ++top) {
		  this_max = GetMaxFrequencyBelowTheBorder(this_max);
		  for (int i = 0; i < letter_frequency_.size(); ++i) {
		    if (this_max == letter_frequency_.get(i)) {
			    letters_order_.add(i);
				  frequency_order_.add(this_max);
			  }
		  }
		}
	}

	public void PrintStatistic() {
		CalculateFrequency(ReadString());
		Sort();

		int top_length = (letters_order_.size() < top_number_) ? letters_order_.size() : top_number_;
		float scale = (10.f / (frequency_order_.get(0)));
		int graph_height = 10 + 1 + 1;

		for (int i = 0; i < top_length; ++i) {
			if (frequency_order_.get(i) != 0) {
        cursor_.MoveDown(graph_height);
			  char[] letter = Character.toChars(letters_order_.get(i));
			  System.out.print(letter[0]);
				cursor_.MoveBackward(1);

        graph_height = 1;
			  for (int j = 1; j <= (scale * frequency_order_.get(i)); ++j) {
					cursor_.MoveUp(1);
			    System.out.print("#");
				  cursor_.MoveBackward(1);
					++graph_height;
			  }
        cursor_.MoveUp(1);
			  System.out.print(frequency_order_.get(i));
				cursor_.MoveForward(2);
			}

      while((0 == frequency_order_.get(i)) && (i+1 < frequency_order_.size())) {++i;}
		}
		cursor_.MoveDown(graph_height);
	}

}
