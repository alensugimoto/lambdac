package ch.usi.pf2.model.parser;

import ch.usi.pf2.model.lexer.Token;
import ch.usi.pf2.model.lexer.TokenType;

/**
 * ParseException is an Exception that can be thrown during parsing.
 * 
 * @author Alen Sugimoto
 * @version 03.06.2021
 */
public final class ParseException extends Exception {

    /**
     * Constructs a new parse exception with the specified detail message.
     * @param message the detail message
     */
    public ParseException(final String message) {
        super(message);
    }

    /**
     * Constructs a new parse exception with a detail message of the
     * following form given the arguments:
     * {@code Expected _, ... or _, got _}.
     * @param actual the actual Token
     * @param expectedType one of the expected TokenTypes
     * @param expectedTypes the rest of the expected TokenTypes
     */
    public ParseException(final Token actual, final TokenType expectedType,
                          final TokenType... expectedTypes) {
        this("Expected " + getExpected(expectedType, expectedTypes)
             + ", but got '" + actual.getText() + "'");
    }

    private static final String getExpected(final TokenType expectedType,
                                            final TokenType... expectedTypes) {
        final StringBuilder builder = new StringBuilder();
        builder.append(expectedType.getName());
        for (int i = 0; i < expectedTypes.length; i++) {
            builder.append((i == expectedTypes.length - 1 ? " or " : ", ")
                           + expectedTypes[i].getName());
        }
        return builder.toString();
    }
    
}
