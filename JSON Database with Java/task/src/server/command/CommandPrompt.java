package server.command;

import java.io.IOException;
import java.net.ServerSocket;

public class CommandPrompt {

    private final ServerSocket serverSocket;

    public CommandPrompt(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public boolean getAndExecute() throws IOException {
        return CommandFactory.getAndExecuteCommand(serverSocket);
    }

}
