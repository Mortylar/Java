package edu.school21.printer.app;

import edu.school21.printer.logic.BMPReader;
import edu.school21.printer.logic.BMPStore;
import edu.school21.printer.logic.ImageToStringConverter;

public class Program {

    private static final String WHITE_PREFIX = "--white-pixels=";
    private static final String BLACK_PREFIX = "--black-pixels=";
    private static final String FILE_PREFIX = "--file=";

    private static final int WHITE_ARG_IND = 0;
    private static final int BLACK_ARG_IND = 1;
    private static final int FILE_ARG_IND = 2;

    private static final int MIN_ARGS_COUNT = 2;
    private static final int MAX_ARGS_COUNT = 3;

    private static final int INVALID_ARGS = -1;

    private static final String DEFAULT_FILE = "it.bmp";

    public static void main(String[] args) {
        Program program = new Program();

        BMPReader reader;
        ImageToStringConverter converter;

        int argsCount = program.checkArgs(args);
        if (INVALID_ARGS == argsCount) {
            reader = new BMPReader(DEFAULT_FILE);
            converter = new ImageToStringConverter();
        } else if (MIN_ARGS_COUNT == argsCount) {
            reader = new BMPReader(DEFAULT_FILE);
            converter = new ImageToStringConverter(
                program.extractWhite(args[WHITE_ARG_IND]),
                program.extractBlack(args[BLACK_ARG_IND]));
        } else if (MAX_ARGS_COUNT == argsCount) {
            reader = new BMPReader(program.extractFile(args[FILE_ARG_IND]));
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

    private String extractFile(String file) {
        return file.substring(FILE_PREFIX.length());
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
        if (argsCount < MIN_ARGS_COUNT) {
            error("Too few argument found - expected 3");
            return INVALID_ARGS;
        } else if (argsCount == MIN_ARGS_COUNT) {
            error("2 argument found - expected 3");
            return MIN_ARGS_COUNT;
        } else if (argsCount > MAX_ARGS_COUNT) {
            error("Too many arguments - expected 3");
            return INVALID_ARGS;
        }
        return MAX_ARGS_COUNT;
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
        if (argsCount == MAX_ARGS_COUNT) {
            if (args[FILE_ARG_IND].length() <= FILE_PREFIX.length()) {
                error("Incorrect file argument");
                return INVALID_ARGS;
            }
            if (!(FILE_PREFIX.equals(
                    args[FILE_ARG_IND].substring(0, FILE_PREFIX.length())))) {
                error(String.format("%s - invalid argument.",
                                    args[FILE_ARG_IND]));
                return INVALID_ARGS;
            }
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
        if (MAX_ARGS_COUNT == argsCount) {
            thisArg = args[FILE_ARG_IND].substring(FILE_PREFIX.length());
            if (thisArg.length() == 0) {
                error(String.format("%s - no file.", thisArg));
                return INVALID_ARGS;
            }
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
        System.out.printf("    %s<path>\n", FILE_PREFIX);
    }
}
