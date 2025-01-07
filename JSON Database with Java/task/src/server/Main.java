package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private static final String ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 23456;

    public static void main(String[] args) {
        System.out.println("Server started!");
        try (
                ServerSocket serverSocket = new ServerSocket(SERVER_PORT, 50, InetAddress.getByName(ADDRESS));
                Socket socket = serverSocket.accept();
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        ) {
            var input = dataInputStream.readUTF();
            System.out.println("Received: " + input);
            var record = Integer.parseInt(input.substring("Give me a record # ".length()));
            var output = "A record # %d was sent!".formatted(record);
            dataOutputStream.writeUTF(output);
            System.out.println("Sent: " + output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /*CommandPrompt commandPrompt = new CommandPrompt();
        while (commandPrompt.command().execute()) {
        }*/
    }
}
