package server.command;

import java.io.DataOutputStream;
import java.io.IOException;

public class ExitCommand implements Command {
    @Override
    public boolean execute() {
        System.out.println("Exiting...");
        return false;
    }

    @Override
    public boolean execute(DataOutputStream outputStream) throws IOException {
        outputStream.writeUTF("OK");
        return false;
    }
}
