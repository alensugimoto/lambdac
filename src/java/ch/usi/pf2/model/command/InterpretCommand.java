package ch.usi.pf2.model.command;

import ch.usi.pf2.model.LambdaText;

public class InterpretCommand implements Command {

    private final EditHistory history;
    private final LambdaText text;

    public InterpretCommand(final LambdaText text) {
        history = null;
        this.text = text;
    }

    public InterpretCommand(final EditHistory history, final LambdaText text) {
        this.history = history;
        this.text = text;
    }

    public String getName() {
        return "Interpret";
    }

    public String getShortDescription() {
        return "Interpret the text to be interpreted";
    }

    public void execute() {
        if (history == null) {
            text.interpret();
        } else {
            final Edit edit = new InterpretEdit(text);
            edit.execute();
            history.add(edit);
        }
    }

}
