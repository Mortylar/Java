package edu.school21.sockets.app;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import edu.school21.sockets.client.Client;
import java.net.UnknownHostException;

@Parameters(separators = "=")
public class Main {

    private static final String PROGRAM_NAME = "SocketClient";
    private static final String SERVER_ADDRESS = "localhost";

    @Parameter(names = {"--port", "-p"}, description = "Port", required = true,
               validateWith = PortValidator.class, order = 0)
    private static int port;

    @Parameter(names = {"--help", "-h"}, help = true, order = 2)
    private static boolean help;

    public static void main(String[] args) {
        Main main = new Main();
        JCommander jc = JCommander.newBuilder().addObject(main).build();
        jc.setProgramName(PROGRAM_NAME);
        try {
            jc.parse(args);
            if (help) {
                jc.usage();
            }
        } catch (ParameterException e) {
            System.err.printf("\n%s\n", e.getMessage());
            jc.usage();
            System.exit(-1);
        }
        try {
            Client client = new Client(SERVER_ADDRESS, port);
            client.run();
        } catch (Exception e) {
            System.err.printf("\n%s\n", e.getMessage());
        }
    }
}
