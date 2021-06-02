package ch.usi.pf2.model.lexer;


/**
 * A special kind of TokenFactory
 * that looks for tokens that exactly match a given string.
 * 
 * @author Alen Sugimoto
 * @version 03.06.2021
 */
public abstract class StringTokenFactory implements TokenFactory {

    private final String tokenText;
    private String text;
    private int startFrom;
    
    /**
     * Constructs a factory that looks for the given tokenText.
     * @param tokenText the exact text of the kind of token we can produce
     */
    protected StringTokenFactory(final String tokenText) {
        super();
        this.tokenText = tokenText;
    }

    @Override
    public final void setText(final String text) {
        this.text = text;
    }

    @Override
    public final boolean find(final int startFrom) {
        this.startFrom = startFrom;
        return text.regionMatches(startFrom, tokenText, 0, tokenText.length());
    }
    
    @Override
    public final int getTokenLength() { 
        return tokenText.length();
    }

    /**
     * Returns the position at which we last tried to find a token.
     * @return the start position of the last call to find(...)
     */
    protected final int getTokenStartPosition() {
        return startFrom;
    }
    
    /**
     * Returns the text of the tokens we can produce.
     * @return the exact text of the kind of token we can produce
     */
    protected final String getTokenText() {
        return tokenText;
    }

}
