package file;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class WordsReader {

    private static final int END_OF_FILE = -1;
	  private BufferedReader reader_;
	  private String fileName_;

    public WordsReader(String fileName) {
		    fileName_ = new String(fileName);
		}
    
		public void initReader() throws FileNotFoundException {
		    reader_ = new BufferedReader(new FileReader(fileName_));
		}

    /*
		** null <-> file ended
		** empty string <-> no wors in this position
		** not empty string <-> word
		*/
    public String readNextWordOrReturnNull() throws IOException {
        int letter = reader_.read();
				if (letter == END_OF_FILE) {
					return null;
				}
				String word = new String();
				while((letter != END_OF_FILE) && (isLetter(letter))) {
				    word += (char) letter;
						letter = reader_.read();
				}
				return word;
		}

		private boolean isLetter(int ch) {
		    return (((ch >= 'a') && (ch <= 'z')) ||
				        ((ch >= 'A') && (ch <= 'Z')));
		}
    
}
