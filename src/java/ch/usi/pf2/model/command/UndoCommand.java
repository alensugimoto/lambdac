package ch.usi.pf2.model.command;

public class UndoCommand implements Command {

    private final EditHistory history;

    public UndoCommand(final EditHistory history) {
        this.history = history;
    }

    @Override
    public String getName() {
        return "Undo";
    }

    @Override
    public String getShortDescription() {
        return "Undo";
    }

    @Override
    public void execute() {
        history.undo();
    }
    
}
