package ch.usi.pf2.model.command;

import java.io.IOException;

import ch.usi.pf2.model.LambdaFile;

public class OpenFileCommand implements Command {

    private final LambdaFile file;

    public OpenFileCommand(final LambdaFile file) {
        this.file = file;
    }

    public String getName() {
        return "Open...";
    }

    public String getShortDescription() {
        return "Opens the specified file";
    }

    public void execute() throws IOException {
        file.open();
    }

}
