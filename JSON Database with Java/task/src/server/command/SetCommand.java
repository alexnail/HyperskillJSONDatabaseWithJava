package server.command;

import server.data.Response;

import java.io.DataOutputStream;
import java.io.IOException;

public class SetCommand extends DataAwareCommand {
    private final String key;
    private final String text;

    public SetCommand(DataOutputStream outputStream, String key, String text) {
        super(outputStream);
        this.key = key;
        this.text = text;
    }

    @Override
    public boolean execute() throws IOException {
        save(key, text);
        outputStream.writeUTF(gson.toJson(Response.ok()));
        return true;
    }
}
