package ch.usi.pf2.model.parser;


/**
 * ParseException is an Exception that can be thrown during parsing.
 */
public final class ParseException extends Exception {
    
    private final int position;
    
    /**
     * Constructs a new parse exception with the specified detail message.
     * 
     * @param message the detail message
     * @param position the position where the exception occurred
     */
    public ParseException(final String message, final int position) {
        super(message);
        this.position = position;
    }
    
    /**
     * Returns the position of this parse exception.
     * 
     * @return the position of this parse exception
     */
    public int getPosition() {
        return position;
    }
    
}
