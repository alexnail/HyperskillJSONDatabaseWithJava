package server;

import server.command.CommandPrompt;

public class Main {
    public static void main(String[] args) {

        CommandPrompt commandPrompt = new CommandPrompt();

        while (commandPrompt.command().execute()) {

        }
    }
}
