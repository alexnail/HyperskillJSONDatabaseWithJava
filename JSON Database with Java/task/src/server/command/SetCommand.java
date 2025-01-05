package server.command;

import server.data.InMemoryData;

import java.util.Map;

public class SetCommand implements Command {
    private final String idx;
    private final String text;

    private final Map<Integer, String> data = InMemoryData.getData();

    public SetCommand(String idx, String text) {
        this.idx = idx;
        this.text = text;
    }

    @Override
    public boolean execute() {
        Integer key = Integer.parseInt(idx);
        data.put(key, text);
        System.out.println("OK");
        return true;
    }
}
