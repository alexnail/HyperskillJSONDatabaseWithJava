package server.command;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;

public class CommandFactory {

    public static boolean getAndExecuteCommand(ServerSocket serverSocket) throws IOException {
        try(var socket = serverSocket.accept();
            var inputStream = new DataInputStream(socket.getInputStream());
            var outputStream = new DataOutputStream(socket.getOutputStream());) {

            var input = inputStream.readUTF();
            var args = parseArgs(input);
            var invalidCommand = validInput(args);
            if (null != invalidCommand) {
                return invalidCommand.execute(outputStream);
            }

            return switch (args[0]) {
                case "set" -> new SetCommand(args[1], args[2]).execute(outputStream);
                case "get" -> new GetCommand(args[1]).execute(outputStream);
                case "delete" -> new DeleteCommand(args[1]).execute(outputStream);
                case "exit" -> new ExitCommand().execute(outputStream);
                default -> new InvalidCommand("Unknown command").execute(outputStream);
            };
        }
    }

    static Command getCommand(String input) {
        var args = parseArgs(input);
        var invalidCommand = validInput(args);
        if (null != invalidCommand) {
            return invalidCommand;
        };
        return switch (args[0]) {
            case "set" -> new SetCommand(args[1], args[2]);
            case "get" -> new GetCommand(args[1]);
            case "delete" -> new DeleteCommand(args[1]);
            case "exit" -> new ExitCommand();
            default -> new InvalidCommand("Unknown command");
        };
    }

    private static String[] parseArgs(String input) {
        return input.split("\\s+", 3);
    }

    private static InvalidCommand validInput(String[] args) {
        if (args.length < 1) {
            return new InvalidCommand("Empty command");
        }
        args[0] = args[0].toLowerCase();
        return switch (args[0]) {
            case "set" -> {
                if (args.length != 3 || indexIsOutOfBounds(args)) {
                    yield new InvalidCommand("ERROR");
                }
                yield null;
            }
            case "get", "delete" -> {
                if (args.length != 2 || indexIsOutOfBounds(args)) {
                    yield new InvalidCommand("ERROR");
                }
                yield null;
            }
            default -> null;
        };
    }

    private static boolean indexIsOutOfBounds(String[] args) {
        return Integer.parseInt(args[1]) < 1 || Integer.parseInt(args[1]) > 1000;
    }
}
