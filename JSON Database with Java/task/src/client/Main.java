package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Main {
    private static final String ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 23456;

    public static void main(String[] args) {
        try (
                Socket socket = new Socket(ADDRESS, SERVER_PORT);
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                ){
            var record = 12;
            System.out.println("Client started!");
            var output = "Give me a record # %d".formatted(record);
            dataOutputStream.writeUTF(output);
            System.out.println("Sent: " + output);
            var input = dataInputStream.readUTF();
            System.out.println("Received: " + input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
