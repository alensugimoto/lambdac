package ch.usi.pf2.model.command;

public interface Edit {

    public abstract void execute();
    public abstract void unexecute();
    public abstract void reexecute();
    
}
