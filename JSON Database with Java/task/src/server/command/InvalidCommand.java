package server.command;

public class InvalidCommand implements Command {
    private final String msg;

    public InvalidCommand(String msg) {
        this.msg = msg;
    }

    @Override
    public boolean execute() {
        System.out.println(msg);
        return true;
    }
}
