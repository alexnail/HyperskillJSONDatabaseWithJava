package server.command;

import server.data.Response;

import java.io.DataOutputStream;
import java.io.IOException;

public class GetCommand extends DataAwareCommand {

    private final String key;

    public GetCommand(DataOutputStream outputStream, String key) {
        super(outputStream);
        this.key = key;
    }

    @Override
    public boolean execute() throws IOException {
        var value = get(key);
        Response response = value == null || value.isEmpty()
                ? Response.error("No such key")
                : Response.ok(value);
        outputStream.writeUTF(gson.toJson(response));
        return true;
    }
}
