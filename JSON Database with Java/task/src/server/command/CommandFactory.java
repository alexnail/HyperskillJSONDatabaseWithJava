package server.command;

import com.google.gson.Gson;
import server.data.Request;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;

public class CommandFactory {

    public static boolean getAndExecuteCommand(ServerSocket serverSocket) throws IOException {
        try(var socket = serverSocket.accept();
            var inputStream = new DataInputStream(socket.getInputStream());
            var outputStream = new DataOutputStream(socket.getOutputStream());) {

            var request = new Gson().fromJson(inputStream.readUTF(), Request.class);
            var invalidCommand = isValidRequest(request, outputStream);
            if (null != invalidCommand) {
                return invalidCommand.execute();
            }

            return switch (request.type()) {
                case "set" -> new SetCommand(outputStream, request.key(), request.value()).execute();
                case "get" -> new GetCommand(outputStream, request.key()).execute();
                case "delete" -> new DeleteCommand(outputStream, request.key()).execute();
                case "exit" -> new ExitCommand(outputStream).execute();
                default -> new InvalidCommand(outputStream, "Unknown command").execute();
            };
        }
    }

    private static InvalidCommand isValidRequest(Request request, DataOutputStream outputStream) {
        if (request.type() == null || request.type().isEmpty()) {
            return new InvalidCommand(outputStream, "Empty command");
        }

        return switch (request.type()) {
            case "set" -> {
                if (request.key() == null || request.value() == null) {
                    yield new InvalidCommand(outputStream, "ERROR");
                }
                yield null;
            }
            case "get", "delete" -> {
                if (request.key() == null) {
                    yield new InvalidCommand(outputStream, "ERROR");
                }
                yield null;
            }
            default -> null;
        };
    }
}
