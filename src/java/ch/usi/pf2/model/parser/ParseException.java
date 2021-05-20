package ch.usi.pf2.model.parser;


/**
 * ParseException is a RuntimeException that can be thrown during parsing.
 */
public class ParseException extends RuntimeException {
    
    /**
     * Constructs a new parse exception with the specified detail message.
     * 
     * @param message the detail message
     */
    public ParseException(final String message) {
        super(message);
    }
    
}
