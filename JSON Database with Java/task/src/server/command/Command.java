package server.command;

import java.io.DataOutputStream;
import java.io.IOException;

public interface Command {
    boolean execute();

    boolean execute(DataOutputStream outputStream) throws IOException;
}
