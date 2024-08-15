import java.io.FileNotFoundException;
import java.io.IOException;

class Program {

    private static final String ARGUMENT_PREFIX = "--current-folder=";
    private static final String DEFAULT_WORKING_DIRECTORY = "/";

    public static void main(String[] args) {
        String workingDirectory = new String(DEFAULT_WORKING_DIRECTORY);
        if (args.length == 0) {
            System.err.print(
                "No arguments found. Set default working directory.\n");
        } else {
            workingDirectory =
                args[0].substring(ARGUMENT_PREFIX.length(), args[0].length());
        }
        TerminalUtility terminal = new TerminalUtility(workingDirectory);
        terminal.run();
    }
}
