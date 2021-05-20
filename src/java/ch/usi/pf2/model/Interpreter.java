package ch.usi.pf2.model;

import ch.usi.pf2.model.context.Context;
import ch.usi.pf2.model.parser.Parser;

/**
 * An interpreter for the untyped lambda calculus.
 */
public final class Interpreter {
    
    private final Parser parser;
    
    /**
     * Constructs an interpreter.
     */
    public Interpreter() {
        parser = new Parser();
    }
    
    /**
     * Interpret the specified source code.
     * 
     * @param sourceCode the source code to be interpreted
     * @return the interpretation of the specified source code
     */
    public String interpret(final String sourceCode) {
        return interpret(sourceCode, new Context());
    }
    
    /**
     * Interpret the specified source code given the specified context.
     * 
     * @param sourceCode the source code to be interpreted
     * @param context the context of the source code
     * @return the interpretation of the specified source code
     */
    public String interpret(final String sourceCode, final Context context) {
        return parser.parse(sourceCode, context).evaluate().toString(context);
    }
    
}
