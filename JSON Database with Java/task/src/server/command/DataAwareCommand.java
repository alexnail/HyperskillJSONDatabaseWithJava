package server.command;

import server.data.InMemoryData;

import java.io.DataOutputStream;
import java.util.Map;

public abstract class DataAwareCommand extends BaseCommand {
    protected final Map<String, String> data = InMemoryData.getData();

    public DataAwareCommand(DataOutputStream outputStream) {
        super(outputStream);
    }
}

