package file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class FileAnalizator {

    private static final String DICTIONARY_FILE = "dictionary.txt";

    private String firstFileName_;
    private String secondFileName_;
    private ArrayList<String> dictionary_;
    private ArrayList<Integer> firstFrequency_;
    private ArrayList<Integer> secondFrequency_;

    { dictionary_ = new ArrayList<String>(); }

    public FileAnalizator(String file1, String file2) {
        firstFileName_ = new String(file1);
        secondFileName_ = new String(file2);
    }

    public ArrayList<Integer> getFirstVector() { return firstFrequency_; }

    public ArrayList<Integer> getSecondVector() { return secondFrequency_; }

    public void analyze() throws FileNotFoundException, IOException {
        upgradeDictionary(firstFileName_);
        upgradeDictionary(secondFileName_);
        try {
            saveDictionary();
        } catch (IOException e2) {
            System.out.printf("Warning - can't write dictionary file %s\n",
                              DICTIONARY_FILE);
        }
        computeWordsFrequency();
    }

    private void upgradeDictionary(String file)
        throws FileNotFoundException, IOException {
        WordsReader reader = new WordsReader(file);
        reader.initReader();

        String word = reader.readNextWordOrReturnNull();

        while (word != null) {
            if ((word.length() > 0) && (!dictionary_.contains(word))) {
                dictionary_.add(word);
            }
            word = reader.readNextWordOrReturnNull();
        }
        reader.close();
    }

    private void computeWordsFrequency()
        throws FileNotFoundException, IOException {
        firstFrequency_ =
            new ArrayList<Integer>(Collections.nCopies(dictionary_.size(), 0));
        secondFrequency_ =
            new ArrayList<Integer>(Collections.nCopies(dictionary_.size(), 0));
        computeWordsFrequencyInOneFile(firstFileName_, firstFrequency_);
        computeWordsFrequencyInOneFile(secondFileName_, secondFrequency_);
    }

    private void computeWordsFrequencyInOneFile(String file,
                                                ArrayList<Integer> array)
        throws FileNotFoundException, IOException {
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
        reader.close();
    }

    public void saveDictionary() throws FileNotFoundException, IOException {
        File file = new File(DICTIONARY_FILE);
        file.delete();
        FileWriter writer = new FileWriter(DICTIONARY_FILE);

        for (int i = 0; i < dictionary_.size(); ++i) {
            writer.write(dictionary_.get(i));
        }
    }
}
