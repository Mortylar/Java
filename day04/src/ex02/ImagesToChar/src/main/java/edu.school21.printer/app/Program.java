package edu.school21.printer.app;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.diogonunes.jcolor.Attribute;
import edu.school21.printer.logic.BMPReader;
import edu.school21.printer.logic.BMPStore;
import edu.school21.printer.logic.ImageToStringConverter;
import java.util.ArrayList;

public class Program {

    @Parameter(description = "Colors")
    private ArrayList<String> colors_ = new ArrayList<>();

    @Parameter(names = {"--white"}, description = "White pixels color")
    private Integer white = 0;

    @Parameter(names = {"--black"}, description = "Black pixels color")
    private Integer black = 1;

    private static final int ARGS_COUNT = 2;
    private static final String FILE_NAME = "/image.bmp";

    private static final char WHITE = 'w';
    private static final char BLACK = 'b';

    private static final String WHITE_PREFIX = "--white=";
    private static final String BLACK_PREFIX = "--black=";

    private static final String WHITE_COLOR = "WHITE";
    private static final String BLACK_COLOR = "BLACK";
    private static final String GREEN_COLOR = "GREEN";
    private static final String BLUE_COLOR = "BLUE";
    private static final String RED_COLOR = "RED";
    private static final String YELLOW_COLOR = "YELLOW";
    private static final String CYAN_COLOR = "CYAN";
    private static final String MAGENTA_COLOR = "MAGENTA";

    private static Attribute whiteAttrib_;
    private static Attribute blackAttrib_;

    public static void main(String[] args) {
        Program program = new Program();

        if (ARGS_COUNT != args.length) {
            System.err.printf("Incorrect argument count - expected 2.\n");
            System.err.printf(
                "Usage:\n ... --white=<String> --black=<String>\n");
            System.exit(-1);
        }
        try {
            JCommander.newBuilder().addObject(program).build().parse(args);
        } catch (Exception e) {
            System.err.printf("%s.\n", e.getMessage());
            System.exit(-1);
        }

        whiteAttrib_ =
            program.getAttribute(program.colors_.get(program.white)
                                     .substring(WHITE_PREFIX.length()));
        blackAttrib_ =
            program.getAttribute(program.colors_.get(program.black)
                                     .substring(BLACK_PREFIX.length()));

        BMPReader reader = new BMPReader(FILE_NAME);
        ImageToStringConverter converter =
            new ImageToStringConverter(WHITE, BLACK);
        BMPStore store = reader.read();
        program.babyPrint(converter.convertByteToString(store));
    }

    private void babyPrint(String string) {
        for (int i = 0; i < string.length(); ++i) {
            if (string.charAt(i) == WHITE) {
                System.out.print(colorize(" ", whiteAttrib_));
            } else if (string.charAt(i) == BLACK) {
                System.out.print(colorize(" ", blackAttrib_));
            } else {
                System.out.print(string.charAt(i));
            }
        }
    }

    private Attribute getAttribute(String color) {
        if (WHITE_COLOR.equals(color)) {
            return WHITE_BACK();
        }
        if (BLACK_COLOR.equals(color)) {
            return BLACK_BACK();
        }
        if (GREEN_COLOR.equals(color)) {
            return GREEN_BACK();
        }
        if (BLUE_COLOR.equals(color)) {
            return BLUE_BACK();
        }
        if (RED_COLOR.equals(color)) {
            return RED_BACK();
        }
        if (YELLOW_COLOR.equals(color)) {
            return YELLOW_BACK();
        }
        if (CYAN_COLOR.equals(color)) {
            return CYAN_BACK();
        }
        if (MAGENTA_COLOR.equals(color)) {
            return MAGENTA_BACK();
        }
        System.err.printf("color %s do not supported.\n", color);
        System.err.printf("Use one of then:\n");
        System.err.printf(
            "WHITE\nBLACK\nGREEN\nBLUE\nRED\nYELLOW\nCYAN\nMAGENTA\n");
        System.exit(-1);
        return null;
    }
}
