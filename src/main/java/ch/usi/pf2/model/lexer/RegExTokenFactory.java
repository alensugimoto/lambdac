package ch.usi.pf2.model.lexer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A TokenFactory that uses regular expressions 
 * to specify the kinds of tokens it produces.
 */
public abstract class RegExTokenFactory implements TokenFactory {
    
    private final Matcher matcher;
    
    /**
     * Create a factory that produces tokens that match the given regular expression.
     * @param regEx The regular expression to use for identifying tokens
     */
    protected RegExTokenFactory(final String regEx) {
        super();
        final Pattern pattern = Pattern.compile(regEx);
        matcher = pattern.matcher("");
    }

    @Override
    public final void setText(final String text) {
        matcher.reset(text);
    }

    @Override
    public final boolean find(final int startFrom) {
        final boolean found = matcher.find(startFrom);
        return found && startFrom == matcher.start();
    }

    @Override
    public final int getTokenLength() {
        return matcher.end() - matcher.start();
    }

    /**
     * Get the position at which we last tried to find a token.
     * @return The start position of the last call to find(...)
     */
    protected final int getTokenStartPosition() {
        return matcher.start();
    }

    /**
     * Returns the text of the token.
     * @return The text of the token we found.
     */
    protected final String getTokenText() {
        return matcher.group();
    }

}
