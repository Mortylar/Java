package statistic;

import java.util.ArrayList;
import java.util.Scanner;

public class Statistic {
  public Statistic() {
	  scanner_ = new Scanner(System.in);
		letter_frequency_ = new ArrayList<>(ascii_max_capasity_);
		letters_order_ = new ArrayList<>(ascii_max_capasity_);
		frequency_order_ = new ArrayList<>(ascii_max_capasity_);
		//TODO
	}

  private ArrayList<Integer> letter_frequency_; //size() <= 65535
	private ArrayList<Integer> letters_order_;
	private ArrayList<Integer> frequency_order_;
	private static final int ascii_max_capasity_ = 127;
	private static final int max_frequency_ = 999;
	private static final int top_number_ = 10;
  
	private Scanner scanner_;

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
		
		for (int i = 0; (i < top_number_) && (i < letters_order_.size()); ++i) {
		  System.out.printf("\n letter = %c frequency = %d\n",
			                    letters_order_.get(i), frequency_order_.get(i));
		}
	}

}
