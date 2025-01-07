package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Main {
    private static final String ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 23456;

    @Parameter(names = "-t")
    private String type;
    @Parameter(names = "-i")
    private Integer index;
    @Parameter(names = "-m")
    private String message;

    public static void main(String[] args) {
        System.out.println("Client started!");
        var main = new Main();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);
        main.run();
    }

    private void run() {
        try (Socket socket = new Socket(ADDRESS, SERVER_PORT);
             DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());) {
            var output = buildRequest();
            dataOutputStream.writeUTF(output);
            System.out.println("Sent: " + output);
            var response = dataInputStream.readUTF();
            System.out.println("Received: " + response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildRequest() {
        return new Gson().toJson(new Request(type, index, message));
    }

    record Request(String type, int key, String value) {
    }
}
