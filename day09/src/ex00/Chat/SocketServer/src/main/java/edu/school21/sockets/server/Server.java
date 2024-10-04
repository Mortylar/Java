package edu.school21.sockets.server;

import edu.school21.sockets.models.User;
import edu.school21.sockets.services.UsersService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("Server")
public class Server {

    private List<ServerSignUpLogic> signUpList;
    private ServerSocket server;
    @Autowired private UsersService service;

    public Server(UsersService service) { this.service = service; }

    public void run(int port) {
        try {
            server = new ServerSocket(port);
            System.out.printf("\nServer running in %d port.\n", port);
        } catch (IOException | SecurityException e) {
            System.err.printf("\n%s\nExiting..\n", e.getMessage());
            System.exit(-1);
        }
        signUpList = new ArrayList<ServerSignUpLogic>();
        run();
    }

    public void run() {
        try {
            while (true) {
                Socket client = server.accept();
                System.out.printf("Accepted client\n");
                try {
                    signUpList.add(new ServerSignUpLogic(client, service));
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
    private UsersService service;

    private BufferedReader inStream;
    private PrintWriter outStream;

    public ServerSignUpLogic(Socket client, UsersService service)
        throws IOException {
        this.service = service;
        this.client = client;
        inStream =
            new BufferedReader(new InputStreamReader(client.getInputStream()));
        outStream = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                                        client.getOutputStream())),
                                    true);
        start();
    }

    @Override
    public void run() {
        try {
            sendMessage("Hell0 form Server!!");
            String answer = readAnswer(SIGN_UP);
            if (signUp()) {
                sendMessage("Succesful!");
            } else {
                sendMessage("Failure.");
            }
        } catch (IOException e) {
            System.err.printf("\n%s\n", e.getMessage());
        } catch (Exception e) {
            System.err.printf("\n%s\n", e.getMessage());
        }
        try {
            close();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private String readAnswer(String template) throws IOException {
        while (true) {
            String answer = inStream.readLine();
            if (null == answer) {
                throw new IOException("Connection lost");
            }
            if (answer.equals(template)) {
                return answer;
            }
            sendMessage("Unknown comand. Try again.");
        }
    }

    private String readAnswer() throws IOException {
        return inStream.readLine();
    }

    private void sendMessage(String message) {
        outStream.println(message);
        outStream.flush();
    }

    private void close() throws IOException {
        System.out.printf("Closing client connection..\n");
        inStream.close();
        outStream.close();
        client.close();
        System.out.printf("Closed.\n");
    }

    private boolean signUp() throws IOException {
        String userName;
        String password;

        sendMessage("Enter userName:");
        userName = readAnswer();

        sendMessage("Enter password:");
        password = readAnswer();

        if ((null == userName) || (null == password)) {
            return false;
        }
        return service.signUp(userName, password);
    }
}
