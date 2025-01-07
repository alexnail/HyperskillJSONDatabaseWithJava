package server.command;

import server.data.InMemoryData;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class DeleteCommand implements Command {
    private final String idx;
    private final Map<Integer, String> data = InMemoryData.getData();

    public DeleteCommand(String idx) {
        this.idx = idx;
    }

    @Override
    public boolean execute() {
        var key = Integer.parseInt(this.idx);
        data.put(key, "");
        System.out.println("OK");
        return true;
    }

    @Override
    public boolean execute(DataOutputStream outputStream) throws IOException {
        var key = Integer.parseInt(this.idx);
        data.put(key, "");
        outputStream.writeUTF("OK");
        return true;
    }
}
