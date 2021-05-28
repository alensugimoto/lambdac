package ch.usi.pf2.model;

import ch.usi.pf2.model.parser.ParseException;

import java.util.ArrayList;


/**
 * The LambdaText is the most important part of the "model" in this application.
 * The "model" does not know anything about the "GUI".
 * It could exist without "GUI" (e.g., for a command-line interface).
 */
public final class LambdaText {

    private final ArrayList<LambdaTextListener> listeners;
    private final Interpreter interpreter;
    private String textToInterpret;
    private String interpretedText;
    
    /**
     * Create a LambdaText based on an empty expression.
     */
    public LambdaText() {
        this("", null);
    }

    /**
     * Create a LambdaText based on the given expression.
     * @param expression the expression
     */
    public LambdaText(final String textToInterpret, final String interpretedText) {
        listeners = new ArrayList<>();
        interpreter = new Interpreter();
        this.textToInterpret = textToInterpret;
        this.interpretedText = interpretedText;
    }
    
    /**
     * Change the expression underlying this LambdaText.
     * @param expression the new expression
     */
    public final void setTextToInterpret(final String textToInterpret) {
        if (!this.textToInterpret.equals(textToInterpret)) {
            this.textToInterpret = textToInterpret;
            fireTextToInterpretChanged();
        }
    }
    
    /**
     * Get the expression defining this LambdaText.
     * @return the expression
     */
    public final String getTextToInterpret() {
        return textToInterpret;
    }

    /**
     * Change the expression underlying this LambdaText.
     * @param expression the new expression
     */
    private final void setInterpretedText(final String interpretedText) {
        if (this.interpretedText == null || !this.interpretedText.equals(interpretedText)) {
            this.interpretedText = interpretedText;
            fireInterpretedTextChanged();
        }
    }
    
    /**
     * Get the expression defining this LambdaText.
     * @return the expression
     */
    public final String getInterpretedText() {
        return interpretedText;
    }
    
    /**
     * Interpret this LambdaText.
     * @return the interpretation of this LambdaText
     */
    public final void interpret() {
        try {
            setInterpretedText(interpreter.interpret(textToInterpret));
        } catch (ParseException e) {
            setInterpretedText(e.getMessage());
        }
    }
    
    //--- listener management
    /**
     * Register the given LambdaTextListener,
     * so it will be notified whenever this LambdaText changes.
     * @param li The LambdaTextListener to register
     */
    public final void addLambdaTextListener(final LambdaTextListener li) {
        listeners.add(li);
    }
    
    /**
     * Unregister the given LambdaTextListener,
     * so it will not be notified anymore whenever this LambdaText changes.
     * @param li The LambdaTextListener to unregister
     */
    public final void removeLambdaTextListener(final LambdaTextListener li) {
        listeners.remove(li);
    }
    
    /**
     * Notify all registered LambdaTextListeners that this LambdaText has changed.
     */
    private void fireTextToInterpretChanged() {
        for (final LambdaTextListener li : listeners) {
            li.textToInterpretChanged(textToInterpret);
        }
    }

    private void fireInterpretedTextChanged() {
        for (final LambdaTextListener li : listeners) {
            li.interpretedTextChanged(interpretedText);
        }
    }
    
}
