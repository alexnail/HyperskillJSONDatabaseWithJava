package server.command;

import server.data.InMemoryData;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class GetCommand implements Command {

    private final String idx;

    private final Map<Integer, String> data = InMemoryData.getData();

    public GetCommand(String idx) {
        this.idx = idx;
    }

    @Override
    public boolean execute() {
        var key = Integer.parseInt(this.idx);
        var data = this.data.get(key);
        System.out.println(data == null || data.isEmpty() ? "ERROR" : data);
        return true;
    }

    @Override
    public boolean execute(DataOutputStream outputStream) throws IOException {
        var key = Integer.parseInt(this.idx);
        var data = this.data.get(key);
        outputStream.writeUTF(data == null || data.isEmpty() ? "ERROR" : data);
        return true;
    }
}
