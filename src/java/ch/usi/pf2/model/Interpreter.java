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
     * Constructs an interpreter with a new context.
     */
    public Interpreter() {
        this(new Context());
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
     * @param sourceCode the source code
     */
    public String interpret(final String sourceCode) {
        return parser.parse(sourceCode).evaluate().toString(context);
    }
    
}
