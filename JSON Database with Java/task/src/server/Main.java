package server;

import server.command.CommandPrompt;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class Main {
    private static final String ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 23456;

    public static void main(String[] args) {
        System.out.println("Server started!");

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT, 50, InetAddress.getByName(ADDRESS))) {
            CommandPrompt commandPrompt = new CommandPrompt(serverSocket);
            while (commandPrompt.getAndExecute()) {
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
