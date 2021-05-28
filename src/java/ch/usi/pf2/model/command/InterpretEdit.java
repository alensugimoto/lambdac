package ch.usi.pf2.model.command;

import ch.usi.pf2.model.LambdaText;

public class InterpretEdit implements Edit {

    private final LambdaText text;
    private String previousTextToInterpret;
    private String nextTextToInterpret;

    public InterpretEdit(final LambdaText text) {
        this.text = text;
    }

    public void execute() {
        previousTextToInterpret = text.getTextToInterpret();
        text.interpret();
        nextTextToInterpret = "";
        text.setTextToInterpret(nextTextToInterpret);
    }

    public void unexecute() {
        nextTextToInterpret = text.getTextToInterpret();
        text.setTextToInterpret(previousTextToInterpret);
    }

    public void reexecute() {
        previousTextToInterpret = text.getTextToInterpret();
        text.setTextToInterpret(nextTextToInterpret);
    }

}
