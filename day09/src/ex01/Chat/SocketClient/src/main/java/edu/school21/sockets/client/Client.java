package edu.school21.sockets.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private Socket socket;
    private BufferedReader console;
    private BufferedReader inStream;
    private PrintWriter outStream;
    protected static boolean isActiveConnection = false;

    public Client(String ip, int port)
        throws UnknownHostException, IOException {
        socket = new Socket(ip, port);
        if (socket.isConnected()) {
            isActiveConnection = true;
        }
    }

    public void run() throws IOException, InterruptedException {
        try {
            console = new BufferedReader(new InputStreamReader(System.in));
            inStream = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            outStream =
                new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                                    socket.getOutputStream())),
                                true);
            ServerListener listener = new ServerListener(socket, inStream);
            ServerWriter writer = new ServerWriter(socket, outStream);
            listener.join();
            writer.join();
            close();
        } catch (IOException e) {
            System.out.printf("\n%s\n", e.getMessage());
            socket.close();
        }
    }
    private void close() throws IOException {
        console.close();
        inStream.close();
        outStream.close();
    }
}

class ServerListener extends Thread {

    private Socket client;
    private BufferedReader server;

    public ServerListener(Socket client, BufferedReader server) {
        this.client = client;
        this.server = server;
        start();
    }

    @Override
    public void run() {
        while (!client.isClosed() || Client.isActiveConnection) {
            try {
                String message = server.readLine();
                if (null == message) {
                    Client.isActiveConnection = false;
                    return;
                }
                System.out.println(message);
            } catch (IOException e) {
                System.err.printf("\nError: %s\n", e.getMessage());
                Client.isActiveConnection = false;
                break;
            }
        }
    }
}

class ServerWriter extends Thread {

    private static final String EXIT = "exit";

    private Socket client;
    private BufferedReader console;
    private PrintWriter server;

    public ServerWriter(Socket client, PrintWriter server) {
        this.client = client;
        this.server = server;
        this.console = new BufferedReader(new InputStreamReader(System.in));
        start();
    }

    @Override
    public void run() {
        while (Client.isActiveConnection) {
            try {
                String message = console.readLine();
                if (null == message) {
                    break;
                }
                server.println(message);
                server.flush();
                if (!Client.isActiveConnection) {
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.err.printf("\nError: %s\n", e.getMessage());
                break;
            }
        }
    }
}
