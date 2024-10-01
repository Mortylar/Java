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

    public void run() throws IOException {
        try {
            console = new BufferedReader(new InputStreamReader(System.in));
            inStream = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            outStream =
                new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                                    socket.getOutputStream())),
                                true);
            while (socket.isConnected()) {
                System.out.printf("\nIn while\n");
                System.out.println(inStream.readLine());
                System.out.flush();
                if (socket.isClosed()) {
                    continue;
                }
                outStream.print(console.readLine() + "\n");
                outStream.flush();
            }
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
