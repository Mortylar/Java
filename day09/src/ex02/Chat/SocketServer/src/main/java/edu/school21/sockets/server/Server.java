package edu.school21.sockets.server;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.services.ChatroomsService;
import edu.school21.sockets.services.MessagesService;
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

    protected static List<ServerLogic> logicList;
    private ServerSocket server;
    @Autowired private UsersService userService;
    @Autowired private MessagesService messageService;
    @Autowired private ChatroomsService chatroomService;

    public Server(UsersService userService, MessagesService messageService,
                  ChatroomsService chatroomService) {
        this.userService = userService;
        this.messageService = messageService;
        this.chatroomService = chatroomService;
    }

    public void run(int port) {
        try {
            server = new ServerSocket(port);
            System.out.printf("\nServer running in %d port.\n", port);
        } catch (IOException | SecurityException e) {
            System.err.printf("\n%s\nExiting..\n", e.getMessage());
            System.exit(-1);
        }
        logicList = new ArrayList<ServerLogic>();
        run();
    }

    private void run() {
        try {
            while (true) {
                Socket client = server.accept();
                System.out.printf("Accepted client\n");
                try {
                    logicList.add(new ServerLogic(
                        client, userService, messageService, chatroomService));
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

class ServerLogic extends Thread {

    private static final Long DEFAULT_ID = 1L;

    private static final String SIGN_UP = "2";
    private static final String SIGN_IN = "1";
    private static final String EXIT = "3";

    private Socket client;
    private User user;
    private UsersService userService;
    private MessagesService messageService;
    private ChatroomsService chatroomService;

    private List<Chatroom> roomList = new ArrayList<Chatroom>();

    private BufferedReader inStream;
    private PrintWriter outStream;

    public ServerLogic(Socket client, UsersService userService,
                       MessagesService messageService,
                       ChatroomsService chatroomService) throws IOException {
        this.userService = userService;
        this.messageService = messageService;
        this.chatroomService = chatroomService;
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
            if (authorization()) {
                messaging();
            } else {
                sendMessage("Incorrect input data.");
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

    private boolean authorization() throws Exception {
        printAuthorizationMessage();
        String answer = readAnswer(new String[] {SIGN_UP, SIGN_IN, EXIT});
        if (answer.equals(EXIT)) {
            sendMessage("Exiting..");
            throw new Exception("Client exit");
        }
        if (answer.equals(SIGN_UP)) {
            return signUp();
        }
        return signIn();
    }

    private String readAnswer(String[] templates) throws IOException {
        while (true) {
            String answer = inStream.readLine();
            if (null == answer) {
                throw new IOException("Connection lost");
            }
            for (int i = 0; i < templates.length; ++i) {
                if (answer.equals(templates[i])) {
                    return answer;
                }
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

    private void notifyAll(String message) {
        for (ServerLogic current : Server.logicList) {
            current.sendMessage(message);
        }
    }

    private void close() throws IOException {
        System.out.printf("Closing client connection..\n");
        inStream.close();
        outStream.close();
        client.close();
        System.out.printf("Closed.\n");
    }

    private boolean signUp() throws IOException {
        if (!readUser()) {
            return false;
        }
        return userService.signUp(user.getUserName(), user.getPassword());
    }

    private boolean signIn() throws IOException {
        if (!readUser()) {
            return false;
        }
        return userService.signIn(user.getUserName(), user.getPassword());
    }

    private boolean readUser() throws IOException {
        String userName;
        String password;

        sendMessage("Enter userName:");
        userName = readAnswer();

        sendMessage("Enter password:");
        password = readAnswer();

        if ((null == userName) || (null == password)) {
            return false;
        }
        user = new User(DEFAULT_ID, userName, password);
        return true;
    }

    private void messaging() throws IOException {
        sendMessage("Start messaging");
        while (true) {
            String message = readAnswer();
            if (message.equals(EXIT)) {
                sendMessage("You have left the chat.");
                return;
            }
            if (messageService.load(user.getUserName(), "def",
                                    message)) { // TODO
                notifyAll(String.format("%s: %s", user.getUserName(), message));
            }
        }
    }

    private void printAuthorizationMessage() {
        outStream.println("1. SingIn");
        outStream.println("2. SingUp");
        outStream.println("3. Exit");
        outStream.flush();
    }
}
