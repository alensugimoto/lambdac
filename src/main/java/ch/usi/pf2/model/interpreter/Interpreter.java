package ch.usi.pf2.model.interpreter;

import ch.usi.pf2.model.parser.ParseException;
import ch.usi.pf2.model.parser.Parser;

/**
 * An interpreter for the untyped lambda calculus.
 * 
 * @author Alen Sugimoto
 * @version 03.06.2021
 */
public final class Interpreter {

    public static final String HELP = "Grammar:\n"
            + "The interperter recognizes the following grammar written in EBNF:\n"
            + "\n"
            + "\n"
            + "\n"
            + "\n"
            + "\n"
            + "\n"
            + "\n"
            + "\n"
            + "\n";
    
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
    public final String interpret(final String text) throws ParseException {
        return parser.parse(text, context).evaluate().toString(context);
    }
    
    private static final Context getDefaultContext() {
        return new Context();
    }
    
}
