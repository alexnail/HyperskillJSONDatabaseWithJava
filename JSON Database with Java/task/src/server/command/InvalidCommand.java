package server.command;

import server.data.Response;

import java.io.DataOutputStream;
import java.io.IOException;

public class InvalidCommand extends BaseCommand {
    private final String msg;

    public InvalidCommand(DataOutputStream outputStream, String msg) {
        super(outputStream);
        this.msg = msg;
    }

    public boolean execute() throws IOException {
        outputStream.writeUTF(gson.toJson(Response.error(msg)));
        return true;
    }
}
