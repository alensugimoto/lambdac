package ch.usi.pf2.model;

import ch.usi.pf2.model.context.Context;
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
        parser = new Parser();
        this.context = getDefaultContext();
    }
    
    /**
     * Constructs an interpreter with the specified context.
     */
    public Interpreter(final Context context) {
        parser = new Parser();
        this.context = context;
    }
    
    /**
     * Interpret the specified source code.
     * 
     * @param sourceCode the source code to be interpreted
     * @return the interpretation of the specified source code
     */
    public String interpret(final String sourceCode) {
        return parser.parse(sourceCode, context).evaluate().toString(context);
    }
    
    private Context getDefaultContext() {
        final Context context = new Context();
        // TODO: add environment variables
        return context;
    }
    
}
