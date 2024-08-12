//import file.FileService;
import java.io.FileNotFoundException;
import java.io.IOException;
//import java.util.Scanner;

import file.FileAnalizator;

class Program {

    private static final int FILES_COUNT = 2;

    public static void main(String[] args) throws IOException, FileNotFoundException {
        if (args.length != FILES_COUNT) {
				    System.err.printf("Illegal arguments count - expected 2 files\n");
						System.exit(-1);
				}

				FileAnalizator fa = new FileAnalizator(args[0], args[1]);
				fa.analyze();
				fa.print();

    }
}
