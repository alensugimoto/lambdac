package ch.usi.pf2.model.parser;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ch.usi.pf2.model.lexer.Token;
import ch.usi.pf2.model.lexer.TokenType;


/**
 * This class tests the constructors in ParseException.
 * 
 * @author Alen Sugimoto
 * @version 03.06.2021
 */
public class ParseExceptionTest {

    private static Token token;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    
    @BeforeClass
    public static void setUp() {
        token = new Token(TokenType.DOT, "text", 0);
    }

    @Test
    public void testParseExceptionMessage() throws ParseException {
        expectedEx.expect(ParseException.class);
        expectedEx.expectMessage("message");
        throw new ParseException("message");
    }

    @Test
    public void testParseExceptionExpectOne() throws ParseException {
        expectedEx.expect(ParseException.class);
        expectedEx.expectMessage("Expected end of file, but got 'text'");
        throw new ParseException(token, TokenType.END_OF_FILE);
    }

    @Test
    public void testParseExceptionExpectTwo() throws ParseException {
        expectedEx.expect(ParseException.class);
        expectedEx.expectMessage("Expected dot or lambda, but got 'text'");
        throw new ParseException(token, TokenType.DOT, TokenType.LAMBDA);
    }

    @Test
    public void testParseExceptionExpectThree() throws ParseException {
        expectedEx.expect(ParseException.class);
        expectedEx.expectMessage("Expected dot, lambda or identifier, but got 'text'");
        throw new ParseException(token, TokenType.DOT, TokenType.LAMBDA,
                                 TokenType.IDENTIFIER);
    }
    
}
