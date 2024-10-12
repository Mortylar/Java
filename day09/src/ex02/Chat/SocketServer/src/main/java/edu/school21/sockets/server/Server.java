package edu.school21.sockets.server;

import com.google.gson.Gson;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("Server")
public class Server {

    protected static List<ServerLogic> logicList;
    protected static Map<String, List<String>> visitedRooms;
    private ServerSocket server;
    @Autowired private UsersService userService;
    @Autowired private MessagesService messageService;
    @Autowired private ChatroomsService chatroomService;

    public Server(UsersService userService, MessagesService messageService,
                  ChatroomsService chatroomService) {
        this.userService = userService;
        this.messageService = messageService;
        this.chatroomService = chatroomService;
        this.visitedRooms = new HashMap<String, List<String>>();
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
    private static final int LAST_MESSAGES_COUNT = 30;

    private static final int SIGN_IN = 1;
    private static final int CREATE_ROOM = 1;
    private static final int SIGN_UP = 2;
    private static final int CHOOSE_ROOM = 2;
    private static final int EXIT = 3;
    private static final String EXIT_MESSAGE = "Exit";

    private Socket client;
    private User user;
    private Chatroom room;
    private UsersService userService;
    private MessagesService messageService;
    private ChatroomsService chatroomService;

    private BufferedReader inStream;
    private PrintWriter outStream;
    private Gson gson;

    public ServerLogic(Socket client, UsersService userService,
                       MessagesService messageService,
                       ChatroomsService chatroomService) throws IOException {
        this.userService = userService;
        this.messageService = messageService;
        this.chatroomService = chatroomService;
        this.client = client;
        gson = new Gson();
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
            sendMessage(new String[] {"Hell0 form Server!!"});
            if (authorization()) {
                // setRoom();
                if (isAlreadyConnected()) {
                    String message = String.format(
                        "User %s is already connected.\n", user.getUserName());
                    sendMessage(new String[] {message});
                    throw new Exception(message);
                }
                setRoom();
                messaging();
            } else {
                sendMessage(new String[] {"Incorrect input data."});
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
        int answer = readAnswer(new int[] {SIGN_UP, SIGN_IN, EXIT});
        if (answer == EXIT) {
            sendMessage(new String[] {"Exiting.."});
            throw new Exception("Client exit");
        }
        if (answer == SIGN_UP) {
            return signUp();
        }
        return signIn();
    }

    private int readAnswer(int[] templates) throws IOException {
        while (true) {
            try {
                int answer = gson.fromJson(inStream.readLine(), int.class);
                for (int i = 0; i < templates.length; ++i) {
                    if (answer == templates[i]) {
                        return answer;
                    }
                }
            } catch (Exception e) {
                System.out.printf("\n%s\n", e.getMessage());
            }
            sendMessage(new String[] {"Unknown comand. Try again."});
        }
    }

    private String readAnswer() throws IOException {
        return gson.fromJson(inStream.readLine(), String.class);
    }

    private void sendMessage(String[] message) {
        outStream.println(gson.toJson(message, String[].class));
        outStream.flush();
    }

    private void notifyAll(String[] message) {
        for (ServerLogic current : Server.logicList) {
            if (this.room.getName().equals(current.room.getName())) {
                current.sendMessage(message);
            }
        }
    }

    private void close() throws IOException {
        System.out.printf("Closing client connection..\n");
        Server.logicList.remove(this);
        inStream.close();
        outStream.close();
        client.close();
        System.out.printf("Closed.\n");
    }

    private boolean signUp() throws IOException { // TODO reg
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

        sendMessage(new String[] {"Enter userName:"});
        userName = readAnswer();

        sendMessage(new String[] {"Enter password:"});
        password = readAnswer();

        if ((null == userName) || (null == password)) {
            return false;
        }
        user = new User(DEFAULT_ID, userName, password);
        return true;
    }

    private void messaging() throws IOException {
        sendMessage(new String[] {"Start messaging\n",
                                  (this.room.getName() + "---\n")});
        if (isVisitedRoom()) {
            printNMessages(LAST_MESSAGES_COUNT);
        }
        addToVisited();

        while (true) {
            String message = readAnswer();
            if (message.equalsIgnoreCase(EXIT_MESSAGE)) {
                sendMessage(new String[] {"You have left the chat."});
                return;
            }
            if (messageService.load(user.getUserName(), room.getName(),
                                    message)) {
                notifyAll(new String[] {
                    String.format("%s: %s", user.getUserName(), message)});
            }
        }
    }

    private void printAuthorizationMessage() {
        String[] message =
            new String[] {"1. SignIn\n", "2. SignUp\n", "3. Exit\n"};
        sendMessage(message);
    }

    private void printRoomMessage() {
        String[] message =
            new String[] {"1. Create room\n", "2. Choose room\n", "3. Exit"};
        sendMessage(message);
    }

    private boolean setRoom() throws IOException, Exception {
        printRoomMessage();
        int answer = readAnswer(new int[] {CREATE_ROOM, CHOOSE_ROOM, EXIT});
        if (answer == EXIT) {
            sendMessage(new String[] {"Exiting.."});
            throw new Exception("Client exit");
        }
        if (answer == CREATE_ROOM) {
            return createRoom();
        }
        return chooseRoom();
    }

    private boolean createRoom() throws IOException {
        sendMessage(new String[] {"Enter room name:"});
        String roomName = readAnswer();
        if (null == roomName) {
            return false;
        }
        this.room =
            new Chatroom(DEFAULT_ID, roomName, new ArrayList<Message>());
        return chatroomService.load(roomName);
    }

    private boolean chooseRoom() throws IOException, Exception {
        List<Chatroom> roomsList = chatroomService.findAll();
        String[] arr =
            Stream
                .concat(roomsList.stream().map(i -> String.format("%s\n", i)),
                        Stream.of(
                            String.format("%d. Exit\n", 1 + roomsList.size())))
                .toArray(String[] ::new);
        sendMessage(arr);
        String ans = readAnswer();
        if (ans.equals(String.valueOf(1 + roomsList.size()))) {
            sendMessage(new String[] {"You have left the chat."});
            throw new Exception("User have left the chat");
        }
        this.room = roomsList.get(Integer.parseInt(ans) - 1);
        return true;
    }

    private void addToVisited() {
        String name = this.user.getUserName();
        if (!Server.visitedRooms.containsKey(name)) {
            Server.visitedRooms.put(name, new ArrayList<String>());
            Server.visitedRooms.get(name).add(this.room.getName());
            return;
        }
        List<String> rooms = Server.visitedRooms.get(name);
        if (!rooms.contains(this.room.getName())) {
            rooms.add(this.room.getName());
        }
    }

    private boolean isVisitedRoom() {
        String name = this.user.getUserName();
        if (!Server.visitedRooms.containsKey(name)) {
            return false;
        }
        if (Server.visitedRooms.get(name).contains(this.room.getName())) {
            return true;
        }
        return false;
    }

    private void printNMessages(int count) {
        List<Message> mList =
            messageService.findNLastInRoom(room.getName(), count);
        String[] arr =
            mList.stream()
                .map(m
                     -> String.format("%s: %s\n", m.getSender().getUserName(),
                                      m.getText()))
                .toArray(String[] ::new);
        sendMessage(arr);
    }

    private boolean isAlreadyConnected() {
        for (ServerLogic current : Server.logicList) {
            if ((current != this) &&
                (current.user.getUserName().equals(this.user.getUserName()))) {
                return true;
            }
        }
        return false;
    }
}
