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

    public Client(String ip, int port)
        throws UnknownHostException, IOException {
        socket = new Socket(ip, port);
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
            // while (!socket.isClosed()) {
            /*  String message = inStream.readLine();
              if (null == message) {
                  break;
              }
              System.out.println(message);
              System.out.flush();
              outStream.print(console.readLine() + "\n");
              outStream.flush();*/
            // }
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
        while (!client.isClosed()) {
            try {
                String message = server.readLine();
                if (null == message) {
                    return;
                }
                System.out.println(message);
            } catch (IOException e) {
                System.err.printf("\nError: %s\n", e.getMessage());
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
        while (!client.isClosed()) {
            try {
                System.out.print("\n");
                String message = console.readLine();
                if (null == message) {
                    break;
                }
                server.println(message);
                server.flush();
                if (message.equals(EXIT)) {
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
