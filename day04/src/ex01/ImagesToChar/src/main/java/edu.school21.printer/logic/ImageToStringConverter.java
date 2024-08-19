package edu.school21.printer.logic;

public class ImageToStringConverter {

    private static final char DEFAULT_WHITE = ' ';
    private static final char DEFAULT_AFRO = 'X';
    private static final int MAX_BYTE = 0xff;

    private char whiteColor_;
    private char afroColor_;
    private String data_;

    { data_ = new String(); }
    public ImageToStringConverter() {
        whiteColor_ = DEFAULT_WHITE;
        afroColor_ = DEFAULT_AFRO;
    }

    public ImageToStringConverter(char white, char notQuiteWhite) {
        whiteColor_ = white;
        afroColor_ = notQuiteWhite;
    }

    public String convertByteToString(BMPStore image) {
        int height = image.getHeight();
        int width = image.getWidth();
        int size = image.size();
        int dataSize = height * width + height;
        byte[] array = image.getImage();

        int bytesInLine = array.length / width;

        for (int i = 0; i < array.length; i += bytesInLine) {
            for (int j = 0; j < bytesInLine; ++j) {
                data_ += convertByteToString(array[i + j]);
            }
            data_ += '\n';
        }
        return data_;
    }

    private String convertByteToString(byte number) {
        String result = new String();
        int flag = 0x80;
        for (int i = 0; i < 8; ++i) {
            if ((number & flag) != 0) {
                result += whiteColor_;
            } else {
                result += afroColor_;
            }
            flag >>= 1;
        }
        return result;
    }
}
