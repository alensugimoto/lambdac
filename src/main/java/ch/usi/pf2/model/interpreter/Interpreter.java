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

    public static final String HELP = "\n"
        + "The lambda expressions that are written "
        + "in this application must follow the following grammar:\n"
        + "\n"
        + "TERM        ::= ATOM\n"
        + "              | ABSTRACTION\n"
        + "              | APPLICATION\n"
        + "ATOM        ::= IDENTIFIER\n"
        + "              | \"(\" TERM \")\"\n"
        + "ABSTRACTION ::= \"\\\" IDENTIFIER \".\" TERM\n"
        + "APPLICATION ::= ATOM ATOM\n"
        + "              | APPLICATION ATOM\n"
        + "IDENTIFIER  ::= [a-zA-Z_][a-zA-Z_0-9]*\n"
        + "\n"
        + "This means that an application is left associative "
        + "(i.e. `x y z` is the same as `(x y) z`) "
        + "and an abstraction extends as far to the right as possible "
        + "(i.e. `\\x.x x` is the same as `\\x.(x x)` but not `(\\x.x) x`).\n"
        + "\n"
        + "Examples:\n"
        + " - (\\x.x x) (\\y.y)\n"
        + " - (\\b.\\c.b c (\\t.\\f.f)) (\\t.\\f.t) (\\t.\\f.f)              # and true false\n"
        + " - (\\l.\\m.\\n.l m n) (\\t.\\f.f) (\\x.x) (\\y.y)"
        + "               # ifthenelse false (\\x.x) (\\y.y)\n"
        + " - (\\n.\\s.\\z.s (n s z)) (\\s.\\z.s z)                       # succ 1\n"
        + " - (\\m.\\n.\\s.\\z.m s (n s z)) (\\s.\\z.s z) (\\s.\\z.s (s z))  # plus 1 2\n";
    
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
