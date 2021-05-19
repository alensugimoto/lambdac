package ch.usi.pf2.model.lexer;

/**
 * A TokenFactory can produce Tokens.
 */
public interface TokenFactory {

    /**
     * Set the text in which to look for tokens.
     * @param text The text to scan for tokens
     */
    public abstract void setText(final String text);

    /**
     * Look for tokens in the text, starting from the given position.
     * @param startFrom The position in the text at which to start looking
     * @return true of the factory can produce a token from the given position
     */
    public abstract boolean find(final int startFrom);

    /**
     * Get the length of the token we found at the start position in the text.
     * @return The length (in number of characters)
     */
    public abstract int getTokenLength();

    /**
     * Get the token we found at the start position in the text.
     * @return The token
     */
    public abstract Token getToken();
    
}
