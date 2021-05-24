package ch.usi.pf2.model;

import ch.usi.pf2.model.context.Context;
import ch.usi.pf2.model.parser.ParseException;
import ch.usi.pf2.model.parser.Parser;


/**
 * An interpreter for the untyped lambda calculus.
 */
public final class Interpreter {
    
    private static final String NAME = "Lambdac";
    private static final String VERSION = "Version 1.0";
    
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
     * Returns the name of this interpreter.
     * @return the name of this interpreter
     */
    public String getName() {
        return NAME;
    }
    
    /**
     * Returns the version of this interpreter.
     * @return the version of this interpreter
     */
    public String getVersion() {
        return VERSION;
    }
    
    /**
     * Interpret the specified source code.
     * 
     * @param sourceCode the source code to be interpreted
     * @return the interpretation of the specified source code
     */
    public String interpret(final String sourceCode) {
        try {
            return parser.parse(sourceCode, context).evaluate().toString(context);
        } catch (ParseException ex) {
            return ex.getMessage();
        }
    }
    
    /**
     * Returns the welcome message of this interpreter.
     * @return the welcome message of this interpreter
     */
    public String getWelcomeMessage() {
        return NAME + "\nType \"help\" for more information.";
    }
    
    private static Context getDefaultContext() {
        final Context context = new Context();
        // TODO: add environment variables
        return context;
    }
    
}
