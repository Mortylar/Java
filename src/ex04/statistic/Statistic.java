package statistic;

import java.util.ArrayList;
import java.util.Scanner;
import cursor.Cursor;

public class Statistic {


    private static final int asciiMaxCapasity_ = 127;
    private static final int maxFrequency_ = 999;
    private static final int topNumber_ = 10;

    private Scanner scanner_;
    private Cursor cursor_;
    private ArrayList<Integer> letterFrequency_;
    private ArrayList<Integer> lettersOrder_;
    private ArrayList<Integer> frequencyOrder_;


    public Statistic() {
	      scanner_ = new Scanner(System.in);
		    letterFrequency_ = new ArrayList<>(asciiMaxCapasity_);
		    lettersOrder_ = new ArrayList<>(asciiMaxCapasity_);
		    frequencyOrder_ = new ArrayList<>(asciiMaxCapasity_);
		    cursor_ = new Cursor();
	  }


    private String readString() {
	      return scanner_.nextLine();
	  }

	  private void calculateFrequency(String str) {
	      for(int i = 0; i < str.length(); ++i) {
		        incLetterFrequency(str.charAt(i));
		    }
	  }

	  private void incLetterFrequency(int letter) {
	      while (letterFrequency_.size() <= letter) {
          letterFrequency_.add(0);
		    }
		    letterFrequency_.set(letter, 1 + letterFrequency_.get(letter));
	  }

    private int getMaxFrequencyBelowTheBorder(int border) {
	      int max = 0;
		    for (int i = 0; i < letterFrequency_.size(); ++i) {
		        int cur = letterFrequency_.get(i);
			      if ((cur > max) && (cur < border)) {
              max = cur;
            }
		    }
		    return max;
	  }


    private void sort() {
		    int thisMax = maxFrequency_ + 1;

		    for (int top = 0; top < topNumber_; ++top) {
		        thisMax = getMaxFrequencyBelowTheBorder(thisMax);
		        for (int i = 0; i < letterFrequency_.size(); ++i) {
		            if (thisMax == letterFrequency_.get(i)) {
			              lettersOrder_.add(i);
				            frequencyOrder_.add(thisMax);
			          }
		        }
		    }
	  }

	  public void printStatistic() {
		    calculateFrequency(readString());
		    sort();

		    int topLength = (lettersOrder_.size() < topNumber_) ? lettersOrder_.size() 
                                                            : topNumber_;
		    float scale = (10.f / (frequencyOrder_.get(0)));
		    int graphHeight = 10 + 1 + 1;

        for (int i = 0; i < topLength; ++i) {
			      if (frequencyOrder_.get(i) != 0) {
                cursor_.moveDown(graphHeight);
			          char[] letter = Character.toChars(lettersOrder_.get(i));
			          System.out.print(letter[0]);
				        cursor_.moveBackward(1);

                graphHeight = 1;
			          for (int j = 1; j <= (scale * frequencyOrder_.get(i)); ++j) {
					          cursor_.moveUp(1);
			              System.out.print("#");
				            cursor_.moveBackward(1);
					          ++graphHeight;
			          }
                cursor_.moveUp(1);
			          System.out.print(frequencyOrder_.get(i));
				        cursor_.moveForward(2);
			      }

            while ((0 == frequencyOrder_.get(i)) && (i+1 < frequencyOrder_.size())) {
              ++i;
            }
		    }
		    cursor_.moveDown(graphHeight);
	  }

}
