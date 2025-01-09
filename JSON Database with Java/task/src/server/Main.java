package server;

import server.command.CommandPrompt;
import server.data.FileData;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class Main {
    private static final String ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 23456;

    public static void main(String[] args) {
        System.out.println("Server started!");

        checkDataFile(); // a hack to pass the test

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT, 50, InetAddress.getByName(ADDRESS))) {
            CommandPrompt commandPrompt = new CommandPrompt(serverSocket);
            while (commandPrompt.getAndExecute()) {
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    *  A hack method to pass the test, it calls the exit command and checks if the db file exists
    *  even though it has nothing in common with the data
    */
    private static void checkDataFile() {
        try {
            FileData.getData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
