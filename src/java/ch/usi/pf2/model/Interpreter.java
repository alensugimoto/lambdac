package ch.usi.pf2.model;

import ch.usi.pf2.model.context.Context;
import ch.usi.pf2.model.parser.ParseException;
import ch.usi.pf2.model.parser.Parser;


/**
 * An interpreter for the untyped lambda calculus.
 */
public final class Interpreter {
    
    private final Parser parser;
    private final Context context;
    
    /**
     * Constructs an interpreter with the default context.
     */
    public Interpreter() {
        this(getDefaultContext());
    }
    
    /**
     * Constructs an interpreter with the specified context.
     * 
     * @param context the context to be used
     */
    public Interpreter(final Context context) {
        parser = new Parser();
        this.context = context;
    }
    
    /**
     * Interpret the specified text.
     * 
     * @param text the text to be interpreted
     * @return the interpretation of the specified text
     * @throws ParseException if the text contains a syntactic error
     *     or an undefined variable
     */
    public String interpret(final String text) throws ParseException {
        return parser.parse(text, context).evaluate().toString(context);
    }
    
    private static Context getDefaultContext() {
        final Context context = new Context();
        // TODO: add environment variables
        return context;
    }
    
}
