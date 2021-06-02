package ch.usi.pf2.model.lexer;


/**
 * A token is a contiguous sequence of characters in a text,
 * such as an identifier or an operator.
 * 
 * @author Alen Sugimoto
 * @version 03.06.2021
 */
public final class Token {

    private final TokenType type;
    private final String text;
    private final int startPosition;

    /**
     * Constructs a new Token.
     * @param type the kind of token
     * @param text the contents (characters) making up the token
     * @param startPosition the position in the text where the token starts
     */
    public Token(final TokenType type, final String text, final int startPosition) {
        this.type = type;
        this.text = text;
        this.startPosition = startPosition;
    }

    /**
     * Returns the type of this token.
     * @return the type of this token
     */
    public final TokenType getType() {
        return type;
    }

    /**
     * Returns the text making up this token.
     * @return the contents of this token
     */
    public final String getText() {
        return text;
    }

    /**
     * Returns the start position of this token in the text.
     * @return the position of the first character of this token
     */
    public final int getStartPosition() {
        return startPosition;
    }

    /**
     * Returns the end position of this token in the text.
     * @return the position just after the last character of this token
     */
    public final int getEndPosition() {
        return startPosition + text.length();
    }

}
