package ch.usi.pf2.model;

import ch.usi.pf2.model.context.Context;
import ch.usi.pf2.model.parser.Parser;
import ch.usi.pf2.model.parser.ParseException;

import java.util.Stack;


/**
 * An interpreter for the untyped lambda calculus.
 */
public final class Interpreter {
    
    private static final String NAME = "Lambdac";
    private static final String VERSION = "Version 1.0";
    
    private final Parser parser;
    private final Stack<String> history;
    private final Context context;
    
    /**
     * Constructs an interpreter with the default context.
     */
    public Interpreter() {
        this(getDefaultContext());
    }
    
    public String getName() {
        return NAME;
    }
    
    public String getVersion() {
        return VERSION;
    }
    
    /**
     * Constructs an interpreter with the specified context.
     */
    public Interpreter(final Context context) {
        parser = new Parser();
        history = new Stack<>();
        this.context = context;
    }
    
    /**
     * Interpret the specified source code.
     * 
     * @param sourceCode the source code to be interpreted
     * @return the interpretation of the specified source code
     */
    public String interpret(final String sourceCode) {
        try {
            history.push(sourceCode);
            return parser.parse(sourceCode, context).evaluate().toString(context);
        } catch (ParseException ex) {
            return ex.getMessage();
        }
    }
    
    private static Context getDefaultContext() {
        final Context context = new Context();
        // TODO: add environment variables
        return context;
    }
    
}
