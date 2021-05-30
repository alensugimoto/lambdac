package ch.usi.pf2.model.lexer;

import ch.usi.pf2.model.parser.ParseException;

import java.util.Arrays;


/**
 * A LexicalAnalyzer breaks a String into Tokens.
 * 
 * <pre>
 * lexer.fetchNextToken();
 * Token t1 = lexer.getCurrentToken();
 * </pre> 
 */
public final class LexicalAnalyzer {

    private Token token;
    private String text;
    private int position;
    private final TokenFactory[] tokenFactories;
    
    /**
     * Create an analyzer for the given text, 
     * using the given factories to recognize and create tokens.
     * @param expression The text to analyze
     * @param factories The token factories to use
     */
    public LexicalAnalyzer(final String expression, final TokenFactory[] factories) {
        tokenFactories = Arrays.copyOf(factories, factories.length);
        setText(expression);
    }

    /**
     * Create an analyzer for the given text.
     * @param expression The text to analyze
     */
    public LexicalAnalyzer(final String expression) {
        this(expression, new TokenFactory[] {
            new IdentifierTokenFactory(),
            new OperatorTokenFactory("\\", TokenType.LAMBDA),
            new OperatorTokenFactory(".", TokenType.DOT),
            new OperatorTokenFactory("(", TokenType.OPEN_PAREN),
            new OperatorTokenFactory(")", TokenType.CLOSED_PAREN),
        });
    }
    
    /**
     * Create an analyzer for an empty string.
     */
    public LexicalAnalyzer() {
        this("");
    }

    /**
     * Provide a new text to analyze.
     * @param expression The text to analyze
     */
    public void setText(final String expression) {
        token = null;
        text = expression;
        position = 0;
        for (final TokenFactory factory : tokenFactories) {
            factory.setText(expression);
        }
    }

    /**
     * Ask the analyzer to move to the next token in the text.
     * @throws ParseException if this text contains a syntax error
     */
    public void fetchNextToken() throws ParseException {
        token = scanToken();
    }

    /**
     * Scan the text and extract the next token.
     * @return the next token
     * @throws ParseException if this text contains a syntax error
     */
    private Token scanToken() throws ParseException {
        // Ignore whitespaces
        while (position < text.length() && Character.isWhitespace(text.charAt(position))) {
            position++;
        }
        
        if (position == text.length()) {
            return new Token(TokenType.END_OF_FILE, "", position);
        } else {
            int maxLength = 0;
            TokenFactory factoryWithLongestMatch = null;

            // Utilize the tokenFactories to find a factory has the longest match
            for (final TokenFactory factory : tokenFactories) {
                if (factory.find(position) && factory.getTokenLength() > maxLength) {
                    factoryWithLongestMatch = factory;
                    maxLength = factory.getTokenLength();
                }
            }

            // if no match is found then throw, otherwise produce a token
            if (factoryWithLongestMatch == null) {
                throw new ParseException("Invalid syntax", position);
            } else {
                position += factoryWithLongestMatch.getTokenLength();
                return factoryWithLongestMatch.getToken();
            }
        }
    }

    /**
     * Get the current token.
     * @return the current token
     */
    public Token getCurrentToken() {
        return token;
    }

}