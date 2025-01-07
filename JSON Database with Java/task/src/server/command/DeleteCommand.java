package server.command;

import server.data.Response;

import java.io.DataOutputStream;
import java.io.IOException;

public class DeleteCommand extends DataAwareCommand {

    private final String key;

    public DeleteCommand(DataOutputStream outputStream, String key) {
        super(outputStream);
        this.key = key;
    }

    @Override
    public boolean execute() throws IOException {
        var removed = data.remove(key);
        var response = removed != null
                ? Response.ok()
                : Response.error("No such key");

        outputStream.writeUTF(gson.toJson(response));
        return true;
    }
}
