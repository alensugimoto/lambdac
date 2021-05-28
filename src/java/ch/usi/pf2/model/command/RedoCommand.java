package ch.usi.pf2.model.command;

public class RedoCommand implements Command {

    private final EditHistory history;

    public RedoCommand(final EditHistory history) {
        this.history = history;
    }

    @Override
    public String getName() {
        return "Redo";
    }

    @Override
    public String getShortDescription() {
        return "Redo";
    }

    @Override
    public void execute() {
        final Edit edit = history.getEdit();
        if (edit != null) {
            history.incrementPointer();
            edit.reexecute();
        }
    }
    
}
