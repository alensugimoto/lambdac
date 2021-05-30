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

    public static final String HELP = "\nThe lambda expressions that are written "
        + "in this application must follow the following grammar:\n\n"
        + "TERM        ::= ATOM\n"
        + "              | ABSTRACTION\n"
        + "              | APPLICATION\n"
        + "ATOM        ::= IDENTIFIER\n"
        + "              | \"(\" TERM \")\"\n"
        + "ABSTRACTION ::= \"\\\" IDENTIFIER \".\" TERM\n"
        + "APPLICATION ::= ATOM ATOM\n"
        + "              | APPLICATION ATOM\n"
        + "IDENTIFIER  ::= [a-zA-Z_][a-zA-Z_0-9]*\n\n"
        + "This means that an application is left associative "
        + "(i.e. `X Y Z` is the same as `(X Y) Z`) "
        + "and an abstraction extends as far to the right as possible "
        + "(i.e. `\\X.Y Z` is the same as `\\X.(Y Z)` but not `(\\X.Y) Z`).\n";
    
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
     * @param context the context to be used
     */
    public Interpreter(final Context context) {
        parser = new Parser();
        this.context = context;
    }
    
    /**
     * Interpret the specified text.
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
