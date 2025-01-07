package server.command;

import java.io.DataOutputStream;
import java.io.IOException;

public class InvalidCommand implements Command {
    private final String msg;

    public InvalidCommand(String msg) {
        this.msg = msg;
    }

    @Override
    public boolean execute() {
        System.out.println(msg);
        return true;
    }

    public boolean execute(DataOutputStream outputStream) throws IOException {
        outputStream.writeUTF(msg);
        return true;
    }
}
