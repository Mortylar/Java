package edu.school21.sockets.app;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class Main {

    @Parameter(names = {"--port", "-p"}, description = "Port", required = true,
               validateWith = PortValidator.class)
    private static int port;

    @Parameter(names = {"--help", "-h"}, help = true)
    private static boolean help;

    public static void main(String[] args) {
        Main main = new Main();
        // System.out.printf("\n\n%s\n\n", args[0]);
        JCommander jc = JCommander.newBuilder().addObject(main).build();
        try {
            // jc = JCommander.newBuilder().addObject(main).build();
            jc.parse(args);
            if (help) {
                jc.usage();
            }
        } catch (ParameterException e) {
            System.err.printf("\n%s\n", e.getMessage());
            jc.usage();
            System.exit(-1);
        }
        System.out.printf("\nPort = %d\n", port);
    }
}
