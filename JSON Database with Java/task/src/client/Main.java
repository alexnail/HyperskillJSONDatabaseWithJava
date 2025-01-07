package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

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
            var output = buildOutput();
            dataOutputStream.writeUTF(output.toString());
            System.out.println("Sent: " + output);
            var input = dataInputStream.readUTF();
            System.out.println("Received: " + input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private StringBuilder buildOutput() {
        var sb = new StringBuilder();
        if (Objects.nonNull(type)) {
            sb.append(type);
        }
        if (Objects.nonNull(index)) {
            sb.append(" ").append(index);
        }
        if (Objects.nonNull(message)) {
            sb.append(" ").append(message);
        }
        return sb;
    }
}
