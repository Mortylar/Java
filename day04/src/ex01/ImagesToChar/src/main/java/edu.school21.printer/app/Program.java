package edu.school21.printer.app;

import edu.school21.printer.logic.BMPReader;
import edu.school21.printer.logic.BMPStore;
import edu.school21.printer.logic.ImageToStringConverter;

public class Program {

    private static final String WHITE_PREFIX = "--white-pixels=";
    private static final String BLACK_PREFIX = "--black-pixels=";

    private static final int WHITE_ARG_IND = 0;
    private static final int BLACK_ARG_IND = 1;

    private static final int ARGS_COUNT = 2;

    private static final int INVALID_ARGS = -1;

    private static final String FILE_NAME = "/image.bmp";

    public static void main(String[] args) {
        Program program = new Program();

        BMPReader reader = new BMPReader(FILE_NAME);
        ImageToStringConverter converter;

        int argsCount = program.checkArgs(args);
        if (INVALID_ARGS == argsCount) {
            converter = new ImageToStringConverter();
        } else if (ARGS_COUNT == argsCount) {
            converter = new ImageToStringConverter(
                program.extractWhite(args[WHITE_ARG_IND]),
                program.extractBlack(args[BLACK_ARG_IND]));
        } else {
            System.err.printf("Some unknown error.\n");
            return;
        }

        BMPStore store = reader.read();
        System.out.print(converter.convertByteToString(store));
    }

    private char extractWhite(String whiteString) {
        return whiteString.charAt(WHITE_PREFIX.length());
    }

    private char extractBlack(String afroString) {
        return afroString.charAt(BLACK_PREFIX.length());
    }

    private int checkArgs(String[] args) {
        int validArgsCount = checkArgsCount(args.length);
        if (validArgsCount != INVALID_ARGS) {
            validArgsCount = checkArgsLength(args, validArgsCount);
        }
        if (validArgsCount != INVALID_ARGS) {
            validArgsCount = checkArgsValidity(args, validArgsCount);
        }
        return validArgsCount;
    }

    private int checkArgsCount(int argsCount) {
        if (argsCount < ARGS_COUNT) {
            error("Too few argument found - expected 2");
            return INVALID_ARGS;
        } else if (argsCount > ARGS_COUNT) {
            error("Too many arguments - expected 2");
            return INVALID_ARGS;
        }
        return ARGS_COUNT;
    }

    private int checkArgsLength(String[] args, int argsCount) {
        if ((args[WHITE_ARG_IND].length() <= WHITE_PREFIX.length()) ||
            (args[BLACK_ARG_IND].length() <= BLACK_PREFIX.length())) {
            error("Incorrect argument length");
            return INVALID_ARGS;
        }
        if (!(WHITE_PREFIX.equals(
                args[WHITE_ARG_IND].substring(0, WHITE_PREFIX.length())))) {
            error(String.format("%s - invalid argument.",
                                args[WHITE_ARG_IND]));
            return INVALID_ARGS;
        }
        if (!(BLACK_PREFIX.equals(
                args[BLACK_ARG_IND].substring(0, BLACK_PREFIX.length())))) {
            error(String.format("%s - invalid argument.", args[BLACK_ARG_IND]));
            return INVALID_ARGS;
        }
        return argsCount;
    }

    private int checkArgsValidity(String[] args, int argsCount) {
        String sample = "э";
        String thisArg = args[WHITE_ARG_IND].substring(WHITE_PREFIX.length());
        if (thisArg.length() != sample.length()) {
            error(String.format("%s - invalid argument.", thisArg));
            return INVALID_ARGS;
        }
        thisArg = args[BLACK_ARG_IND].substring(BLACK_PREFIX.length());
        if (thisArg.length() != sample.length()) {
            error(String.format("%s - invalid argument.", thisArg));
            return INVALID_ARGS;
        }
        return argsCount;
    }

    private void error(String message) {
        System.out.printf("%s\n", message);
        printHelp();
    }

    private void printHelp() {
        System.out.printf("Usage:\n");
        System.out.printf("    %s<c>\n", WHITE_PREFIX);
        System.out.printf("    %s<c>\n", BLACK_PREFIX);
    }
}
