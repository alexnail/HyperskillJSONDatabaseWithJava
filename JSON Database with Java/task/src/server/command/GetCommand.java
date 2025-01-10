package server.command;

import server.data.Response;

import java.io.DataOutputStream;
import java.io.IOException;

public class GetCommand extends DataAwareCommand {

    private final Object key;

    public GetCommand(DataOutputStream outputStream, Object key) {
        super(outputStream);
        this.key = key;
    }

    @Override
    public boolean execute() throws IOException {
        var value = get(key);
        Response response = value == null
                ? Response.error("No such key")
                : Response.ok(value);
        outputStream.writeUTF(gson.toJson(response));
        return true;
    }
}
