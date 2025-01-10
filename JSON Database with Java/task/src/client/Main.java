package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.time.Duration;

public class Main implements Runnable {
    private static final String ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 23456;
    private static final String PATH = System.getProperty("user.dir") + "/src/client/data/%s";

    @Parameter(names = "-t")
    private String type;
    @Parameter(names = "-k")
    private String key;
    @Parameter(names = "-v")
    private String value;
    @Parameter(names = "-in")
    private String input;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Client started!");
        var main = new Main();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);
        Thread thread = new Thread(main);
        thread.start();
        thread.join(Duration.ofSeconds(5));
    }

    public void run() {
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
        if (input != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(PATH.formatted(input)))) {
                return br.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new Gson().toJson(new Request(type, key, value));
    }

    record Request(String type, String key, String value) {
    }
}
