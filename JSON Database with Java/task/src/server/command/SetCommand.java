package server.command;

import server.data.Response;

import java.io.DataOutputStream;
import java.io.IOException;

public class SetCommand extends DataAwareCommand {
    private final Object key;
    private final Object value;

    public SetCommand(DataOutputStream outputStream, Object key, Object value) {
        super(outputStream);
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean execute() throws IOException {
        save(key, value);
        outputStream.writeUTF(gson.toJson(Response.ok()));
        return true;
    }
}
