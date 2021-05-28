package ch.usi.pf2.model.command;

import ch.usi.pf2.model.LambdaText;

public class InterpretEdit implements Edit {

    private final LambdaText text;
    private String previousTextToInterpret;

    public InterpretEdit(final LambdaText text) {
        this.text = text;
    }

    public void execute() {
        previousTextToInterpret = text.getTextToInterpret();
        text.interpret();
        text.setTextToInterpret("");
    }

    public void unexecute() {
        text.setTextToInterpret(previousTextToInterpret);
    }

    public void reexecute() {
        text.setTextToInterpret("");
    }

}
