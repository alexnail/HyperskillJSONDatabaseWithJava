package server.command;

import java.util.Scanner;

public class CommandPrompt {

    private final Scanner scanner = new Scanner(System.in);

    public Command command() {
        var line = scanner.nextLine();

        return CommandFactory.getCommand(line);
    }
}
