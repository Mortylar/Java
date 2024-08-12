import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileInputStream;

class Program {

    public static void main(String[] args) throws FileNotFoundException, IOException {
		    String file_name = "Program.class";

				SignatureReader signatureReader = new SignatureReader();
				signatureReader.startRead();

				while (signatureReader.readNext()) {
				    String extension = signatureReader.getExtension();
						String signature = signatureReader.getSignature();
						System.out.printf("%s <====> %s\n", extension, signature);
				}

        FileHeaderSaver headerSaver = new FileHeaderSaver(file_name);
				headerSaver.readFileHeader();
				System.out.printf("\n**%s**\n", headerSaver.getHeader());
 

		}
}
