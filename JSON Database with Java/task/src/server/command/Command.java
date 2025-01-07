package server.command;

import java.io.DataOutputStream;
import java.io.IOException;

public interface Command {

    boolean execute(DataOutputStream outputStream) throws IOException;
}
