package server;

import server.command.CommandPrompt;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class Main {
    private static final String ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 23456;


    public static void main(String[] args) {

        System.out.println("Server started!");

        var executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT, 50, InetAddress.getByName(ADDRESS))) {
            CommandPrompt commandPrompt = new CommandPrompt(serverSocket, executorService);
            while (commandPrompt.getAndExecute().get()) {
            }
        } catch (ExecutionException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }
    }
}
