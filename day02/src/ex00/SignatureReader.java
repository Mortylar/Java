import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SignatureReader {

    private static final String fileName_ = "signatures.txt";
    private Scanner fileScanner_;

		private String extension_;
		private String signature_;

		public SignatureReader() {
		    extension_ = new String();
				signature_ = new String();
		}


		public void startRead() throws FileNotFoundException {
        fileScanner_ = new Scanner(new File(fileName_));
		}

		public String getSignature() {
		    return signature_;
		}

		public String getExtension() {
		    return extension_;
		}

		public boolean readNext() {
		    if (!fileScanner_.hasNextLine()) {
				    return false;
				}
				Scanner lineScanner = new Scanner(fileScanner_.nextLine()).useDelimiter(" ");

				extension_ = lineScanner.next();
				extension_ = extension_.substring(0, extension_.length() - 1);

        signature_ = "";
				while (lineScanner.hasNext()) {
				    signature_ += lineScanner.next();
				}
				return true;
		}

}
