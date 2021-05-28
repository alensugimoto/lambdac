package ch.usi.pf2.model.command;

import java.io.IOException;

public interface Command {

    public abstract String getName();

    public abstract String getShortDescription();
    
    public abstract void execute() throws IOException;

}
