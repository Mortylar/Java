package edu.school21.sockets.server;

import edu.school21.sockets.models.User;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private List<ServerSignUpLogic> signUpList;
    private ServerSocket server;

    public Server(int port) {
        try {
            server = new ServerSocket(port);
            System.out.printf("\nServer running in %d port.\n", port);
        } catch (IOException | SecurityException e) {
            System.err.printf("\n%s\nExiting..\n", e.getMessage());
            System.exit(-1);
        }
        signUpList = new ArrayList<ServerSignUpLogic>();
    }

    public void run() {
        try {
            while (true) {
                Socket client = server.accept();
                try {
                    signUpList.add(new ServerSignUpLogic(client));
                } catch (Exception e) {
                    System.err.printf("\n%s\n", e.getMessage());
                    client.close();
                }
            }
        } catch (Exception e) {
            System.err.printf("\n%s\n", e.getMessage());
        }
        try {
            server.close();
        } catch (IOException e) {
            System.err.printf("\n%s\n", e.getMessage());
        }
    }
}

class ServerSignUpLogic extends Thread {

    private static final String SIGN_UP = "signUp";

    private Socket client;

    private BufferedReader inStream;
    private PrintWriter outStream;

    public ServerSignUpLogic(Socket client) throws IOException {
        this.client = client;
        inStream =
            new BufferedReader(new InputStreamReader(client.getInputStream()));
        outStream = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                                        client.getOutputStream())),
                                    true);
        start();
    } // TODO Exception -> catch out and close client socket

    @Override
    public void run() {
        User user = new User();

        try {
            outStream.printf("\nHell0 form Server!!\n");
            String answer = readAnswer(SIGN_UP);

            outStream.printf("\nEnter userName:\n");
            user.setUserName(readAnswer());

            outStream.printf("\nEnter password:\n");
            user.setPassword(readAnswer());

            outStream.printf("\nSuccesful!\n");
        } catch (IOException e) {
            System.err.printf("\n%s\n", e.getMessage());
        }
        try {
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private String readAnswer(String template) throws IOException {
        while (true) {
            String answer = inStream.readLine();
            if (answer.equals(template)) {
                return answer;
            }
            outStream.printf("\nUnknown comand. Try again.\n");
        }
    }

    private String readAnswer() throws IOException {
        return inStream.readLine();
    }
}
