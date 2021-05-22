package ch.usi.pf2.model.parser;


/**
 * ParseException is an Exception that can be thrown during parsing.
 */
public class ParseException extends Exception {
    
    private int position;
    
    /**
     * Constructs a new parse exception with the specified detail message.
     * 
     * @param message the detail message
     */
    public ParseException(final String message, final int position) {
        super(message);
        this.position = position;
    }
    
}
