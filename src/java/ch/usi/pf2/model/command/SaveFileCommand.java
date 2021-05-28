package ch.usi.pf2.model.command;

import java.io.IOException;

import ch.usi.pf2.model.LambdaFile;

public class SaveFileCommand implements Command {

    private final LambdaFile file;

    public SaveFileCommand(final LambdaFile file) {
        this.file = file;
    }

    public String getName() {
        return "Save";
    }

    public String getShortDescription() {
        return "Saves the currently opened file";
    }

    public void execute() throws IOException {
        file.save();
    }

}
