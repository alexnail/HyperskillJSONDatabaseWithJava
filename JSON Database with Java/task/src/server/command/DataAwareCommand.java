package server.command;

import server.data.FileData;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public abstract class DataAwareCommand extends BaseCommand {
    protected final static Map<String, String> data;

    static {
        try {
            data = FileData.getData();
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize data", e);
        }
    }

    public DataAwareCommand(DataOutputStream outputStream) {
        super(outputStream);
    }
}

