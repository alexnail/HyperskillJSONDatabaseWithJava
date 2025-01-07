package server.command;

import com.google.gson.Gson;

import java.io.DataOutputStream;

public abstract class BaseCommand implements Command {
    protected DataOutputStream outputStream;

    protected Gson gson = new Gson();

    public BaseCommand(DataOutputStream outputStream) {
        this.outputStream = outputStream;
    }
}
