package file;

import java.util.ArrayList;
import java.util.Collections;
import java.io.FileNotFoundException;
import java.io.IOException;


public class FileAnalizator {

    private String firstFileName_;
		private String secondFileName_;
    private ArrayList<String> dictionary_;
    private ArrayList<Integer> firstFrequency_;
		private ArrayList<Integer> secondFrequency_;

		{
        dictionary_ = new ArrayList<String>();
		}

		public FileAnalizator(String file1, String file2) {
		    firstFileName_ = new String(file1);
				secondFileName_ = new String(file2);
		}

		public void analyze() throws FileNotFoundException, IOException {
		    upgradeDictionary(firstFileName_);
				upgradeDictionary(secondFileName_);
				computeWordsFrequency();
		}

		private void upgradeDictionary(String file) throws FileNotFoundException, IOException {
		    WordsReader reader = new WordsReader(file);
				reader.initReader();

				String word = reader.readNextWordOrReturnNull();

				while(word != null) {
				    if ((word.length() > 0) && (!dictionary_.contains(word))) {
						    dictionary_.add(word);
						}
						word = reader.readNextWordOrReturnNull();
				}
		}

		private void computeWordsFrequency() throws FileNotFoundException, IOException {
		    firstFrequency_ = new ArrayList<Integer>(Collections.nCopies(dictionary_.size(), 0));
		    secondFrequency_ = new ArrayList<Integer>(Collections.nCopies(dictionary_.size(), 0));
				computeWordsFrequencyInOneFile(firstFileName_, firstFrequency_);
				computeWordsFrequencyInOneFile(secondFileName_, secondFrequency_);
    }

		private void computeWordsFrequencyInOneFile(String file, ArrayList<Integer> array) throws FileNotFoundException, IOException {
		    WordsReader reader = new WordsReader(file);
				reader.initReader();

				String word = reader.readNextWordOrReturnNull();
				while (word != null) {
				  if (word.length() > 0) {
              int index = dictionary_.indexOf(word);
					    array.set(index, 1 + array.get(index));
					}
					word = reader.readNextWordOrReturnNull();
				}
		}

		public void print() {
		    for (int i = 0; i < dictionary_.size(); ++i) {
				    System.out.printf("%s <-> %d <-> %d\n", dictionary_.get(i), firstFrequency_.get(i), secondFrequency_.get(i));
				}
		}

}


