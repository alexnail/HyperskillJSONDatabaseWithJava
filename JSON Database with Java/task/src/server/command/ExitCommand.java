package server.command;

import server.data.Response;

import java.io.DataOutputStream;
import java.io.IOException;

public class ExitCommand extends BaseCommand {

    public ExitCommand(DataOutputStream outputStream) {
        super(outputStream);
    }

    @Override
    public boolean execute() throws IOException {
        outputStream.writeUTF(gson.toJson(Response.ok()));
        return false;
    }
}
