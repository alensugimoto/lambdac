package ch.usi.pf2.model.lexer;

import static org.junit.Assert.*;

import org.junit.Test;


/**
 * This class tests all methods and constructors of the Token class.
 * 
 * @author Alen Sugimoto
 * @version 03.06.2021
 */
public class TokenTest {
    
    @Test
    public void testTokenTextLength0() {
        Token t = new Token(TokenType.END_OF_FILE, "", 5);
        assertEquals(TokenType.END_OF_FILE, t.getType());
        assertEquals("", t.getText());
        assertEquals(5, t.getStartPosition());
        assertEquals(5, t.getEndPosition());
    }
    
    @Test
    public void testTokenTextLength1() {
        Token t = new Token(TokenType.LAMBDA, "\\", 0);
        assertEquals(TokenType.LAMBDA, t.getType());
        assertEquals("\\", t.getText());
        assertEquals(0, t.getStartPosition());
        assertEquals(1, t.getEndPosition());
    }
    
    @Test
    public void testTokenTextLength2() {
        Token t = new Token(TokenType.IDENTIFIER, "id", 3);
        assertEquals(TokenType.IDENTIFIER, t.getType());
        assertEquals("id", t.getText());
        assertEquals(3, t.getStartPosition());
        assertEquals(5, t.getEndPosition());
    }
    
}
