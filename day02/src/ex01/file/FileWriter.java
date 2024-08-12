package file;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWriter {

    private String fileName_;

    public FileWriter(String fileName) { fileName_ = new String(fileName); }

    public void write(String data) throws FileNotFoundException, IOException {
        FileOutputStream fileStream = new FileOutputStream(fileName_, true);

        data += "\n";
        byte[] buffer = data.getBytes();
        fileStream.write(buffer, 0, buffer.length);
    }
}
