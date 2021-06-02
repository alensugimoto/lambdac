package ch.usi.pf2.model.lexer;


/**
 * A TokenFactory can produce Tokens.
 * 
 * @author Alen Sugimoto
 * @version 03.06.2021
 */
public interface TokenFactory {

    /**
     * Sets the text in which to look for tokens.
     * @param text the text to scan for tokens
     */
    public abstract void setText(final String text);

    /**
     * Looks for tokens in the text, starting from the given position.
     * @param startFrom the position in the text at which to start looking
     * @return true if the factory can produce a token from the given position and false otherwise
     */
    public abstract boolean find(final int startFrom);

    /**
     * Returns the length of the token we found at the start position in the text.
     * @return the length (in number of characters)
     */
    public abstract int getTokenLength();

    /**
     * Returns the token we found at the start position in the text.
     * @return the token we found at the start position in the text
     */
    public abstract Token getToken();
    
}
