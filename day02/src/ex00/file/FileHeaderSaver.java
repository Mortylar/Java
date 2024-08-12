package file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileHeaderSaver {

    private static final int DATA_SIZE = 255;

    private String fileName_;
    private String header_;
    private FileInputStream fileStream_;

    public FileHeaderSaver(String fileName) {
        fileName_ = new String(fileName);
        header_ = new String();
    }

    private void initFileStream() throws FileNotFoundException {
        fileStream_ = new FileInputStream(fileName_);
    }
    public void readFileHeader() throws IOException {
        initFileStream();
        header_ = "";
        byte[] buffer = new byte[DATA_SIZE];
        fileStream_.read(buffer);

        for (int i = 0; i < buffer.length; ++i) {
            header_ += String.format("%02x", buffer[i]);
        }
    }

    public String getHeader() { return header_; }
}
