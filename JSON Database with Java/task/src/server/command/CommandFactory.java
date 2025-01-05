package server.command;

public class CommandFactory {
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
                if (args.length != 3 || Integer.parseInt(args[1]) < 1 || Integer.parseInt(args[1]) > 1000) {
                    yield new InvalidCommand("ERROR");
                }
                yield null;
            }
            case "get", "delete" -> {
                if (args.length != 2 || Integer.parseInt(args[1]) < 1 || Integer.parseInt(args[1]) > 1000) {
                    yield new InvalidCommand("ERROR");
                }
                yield null;
            }
            default -> null;
        };
    }
}
