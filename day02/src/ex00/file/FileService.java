package file;

import java.io.FileNotFoundException;
import java.io.IOException;

public class FileService {

    private SignatureReader signatureReader_;
    private FileHeaderSaver header_;
    private FileWriter fileWriter_;
    private String fileName_;
    private String fileType_;

    public FileService(String fileName) {
        fileType_ = null;
        fileName_ = fileName;
        signatureReader_ = new SignatureReader();
        header_ = new FileHeaderSaver(fileName_);
        fileWriter_ = new FileWriter();
    }

    public void findFileType() throws IOException, FileNotFoundException {
        header_.readFileHeader();
        String head = header_.getHeader();

        signatureReader_.startRead();

        while (signatureReader_.readNext()) {
            String sampleSignature = signatureReader_.getSignature();
            String actualSignature =
                head.substring(0, sampleSignature.length());
            if (sampleSignature.equalsIgnoreCase(actualSignature)) {
                fileType_ = new String(signatureReader_.getExtension());
                return;
            }
        }
    }

    public String getFileTypeOrReturnNull() { return fileType_; }

    public void writeFileType() throws FileNotFoundException, IOException {
        fileWriter_.write(fileType_);
    }
}
