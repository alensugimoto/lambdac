package ch.usi.pf2.model.parser;


/**
 * ParseException is an Exception that can be thrown during parsing.
 */
public final class ParseException extends Exception {
    
    /**
     * Constructs a new parse exception with the specified detail message.
     * @param message the detail message
     */
    public ParseException(final String message) {
        super(message);
    }
    
}
