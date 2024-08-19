package edu.school21.printer.app;

import edu.school21.printer.logic.BMPReader;
import edu.school21.printer.logic.BMPStore;
import edu.school21.printer.logic.ImageToStringConverter;

public class Program {

    public static void main(String[] args) {
        String file = "/Users/mortylar/Downloads/it.bmp";
        BMPReader reader = new BMPReader(file);
        BMPStore store = reader.read();
        ImageToStringConverter converter = new ImageToStringConverter();
        String image = converter.convertByteToString(store);
        System.out.print(image);
    }
}
