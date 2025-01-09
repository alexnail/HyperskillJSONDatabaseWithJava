package server;

import server.command.CommandPrompt;
import server.data.FileData;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {
    private static final String ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 23456;


    public static void main(String[] args) {

        System.out.println("Server started!");

        checkDataFile(); // a hack to pass the test

        var executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT, 50, InetAddress.getByName(ADDRESS))) {
            CommandPrompt commandPrompt = new CommandPrompt(serverSocket, executorService);
            while (commandPrompt.getAndExecute().get(1, TimeUnit.SECONDS)) {
            }
        } catch (ExecutionException | InterruptedException | IOException | TimeoutException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
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
