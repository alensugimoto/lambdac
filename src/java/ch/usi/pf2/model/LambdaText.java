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
    private String expression;
    
    /**
     * Create a LambdaText based on an empty expression.
     */
    public LambdaText() {
        this("");
    }

    /**
     * Create a LambdaText based on the given expression.
     * @param expression the expression
     */
    public LambdaText(final String expression) {
        listeners = new ArrayList<>();
        interpreter = new Interpreter();
        this.expression = expression;
    }
    
    /**
     * Change the expression underlying this LambdaText.
     * @param expression the new expression
     */
    public final void setExpression(final String expression) {
        this.expression = expression;
        fireLambdaTextChanged();
    }
    
    /**
     * Get the expression defining this LambdaText.
     * @return the expression
     */
    public final String getExpression() {
        return expression;
    }
    
    /**
     * Interpret this LambdaText.
     * @return the interpretation of this LambdaText
     */
    public final String interpret() throws ParseException {
        return interpreter.interpret(expression);
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
    private void fireLambdaTextChanged() {
        for (final LambdaTextListener li : listeners) {
            li.lambdaTextChanged(this);
        }
    }
    
}
