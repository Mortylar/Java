package file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FReader {

    private String fileName_;
    private BufferedReader reader_;

    public FReader(String fileName) { fileName_ = fileName; }

    public void openFile() {
        try {
            reader_ = new BufferedReader(new FileReader(fileName_));
        } catch (FileNotFoundException e) {
            System.err.printf("file %s not found. Goodbye.\n", fileName_);
            System.exit(-1);
        }
    }

    public String getNextLineOrReturnNull() {
        String line = null;
        try {
            line = reader_.readLine();
        } catch (IOException e) {
            System.out.printf("%s.\n", e.getMessage());
        }
        return line;
    }

    public void close() {
        try {
            reader_.close();
        } catch (IOException e) {
            System.err.printf("%s.\n", e.getMessage());
        }
    }
}
