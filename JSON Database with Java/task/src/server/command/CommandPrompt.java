package server.command;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class CommandPrompt {

    private final ServerSocket serverSocket;
    private final ExecutorService executorService;

    public CommandPrompt(ServerSocket serverSocket, ExecutorService executorService) {
        this.serverSocket = serverSocket;
        this.executorService = executorService;
    }

    public Future<Boolean> getAndExecute() {
        return CommandFactory.getAndExecuteCommand(serverSocket, executorService);
    }

}
