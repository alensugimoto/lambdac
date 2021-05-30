package ch.usi.pf2.model.lexer;


/**
 * A special kind of TokenFactory
 * that looks for tokens that exactly match a given string.
 */
public abstract class StringTokenFactory implements TokenFactory {

    private final String tokenText;
    private String text;
    private int startFrom;
    
    /**
     * Create a factory that looks for the given tokenText.
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
     * Get the position at which we last tried to find a token.
     * @return The start position of the last call to find(...)
     */
    protected final int getTokenStartPosition() {
        return startFrom;
    }
    
    /**
     * Get the text of the tokens we can produce.
     * @return The exact text of the kind of token we can produce
     */
    protected final String getTokenText() {
        return tokenText;
    }

}
