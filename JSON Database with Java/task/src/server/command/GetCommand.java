package server.command;

import server.data.InMemoryData;

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
}
