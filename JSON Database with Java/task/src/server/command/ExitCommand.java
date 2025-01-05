package server.command;

public class ExitCommand implements Command {
    @Override
    public boolean execute() {
        System.out.println("Exiting...");
        return false;
    }
}
