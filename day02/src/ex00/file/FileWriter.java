package file;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWriter {

    private static final String fileName = "result.txt";
    private boolean isSavePreviousData_;

    public FileWriter() { isSavePreviousData_ = true; }

    public void write(String data) throws FileNotFoundException, IOException {
        FileOutputStream fileStream =
            new FileOutputStream(fileName, isSavePreviousData_);

        data += "\n";
        byte[] buffer = data.getBytes();
        fileStream.write(buffer, 0, buffer.length);
        fileStream.close();
    }
}
