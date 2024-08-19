package edu.school21.printer.logic;

import java.io.FileInputStream;
import java.io.IOException;

public class BMPReader {

    private static final int WEIGHT_OFFSET = 18;
    private static final int HEIGHT_OFFSET = 22;

    private static final int WEIGHT_SIZE = 4;
    private static final int HEIGHT_SIZE = 4;

    private static final int HEADER_SIZE = 54;

    private static final int IMAGE_OFFSET = 10;
    private static final int IMAGE_OFFSET_SIZE = 4;

    private static final int BYTE_SIZE = 8;

    private String file_;
    private BMPStore store_;

    public BMPReader(String fileName) {
        file_ = fileName;
        store_ = new BMPStore();
    }

    public BMPStore read() {
        byte[] header = readHeader();

        store_.addSize(convertBytesToInt(header, WEIGHT_OFFSET, WEIGHT_SIZE),
                       convertBytesToInt(header, HEIGHT_OFFSET, HEIGHT_SIZE));
        byte[] image = readImage(
            convertBytesToInt(header, IMAGE_OFFSET, IMAGE_OFFSET_SIZE));
        store_.setImage(image);
        return store_;
    }

    private byte[] readHeader() {
        byte[] header = new byte[HEADER_SIZE];
        try (FileInputStream reader = new FileInputStream(file_)) {
            if (reader.read(header) != HEADER_SIZE) {
                System.err.printf("File %s is invalid.", file_);
                reader.close();
                System.exit(-1);
            }
        } catch (IOException e) {
            System.err.printf("%s.\n", e.getMessage());
            System.exit(-1);
        }

        return header;
    }

    private byte[] readImage(int imageOffset) {
        int imageSize = store_.size() / BYTE_SIZE;
        byte[] image = new byte[imageSize];
        try (FileInputStream reader = new FileInputStream(file_)) {
            reader.skip(imageOffset);
            final byte VALID_BYTES = 2;
            final byte INVALID_BYTES = 2;
            for (int i = 0; i < imageSize; i += VALID_BYTES) {
                reader.read(image, i, VALID_BYTES);
                reader.skip(INVALID_BYTES);
            }
        } catch (IOException e) {
            System.err.printf("%s.\n", e.getMessage());
            System.exit(-1);
        }
        return image;
    }

    private int convertBytesToInt(byte[] array, int startPosition, int length) {
        int result = 0;
        for (int i = startPosition + length - 1; i >= startPosition; --i) {
            result <<= BYTE_SIZE;
            result += array[i];
        }
        return result;
    }
}
