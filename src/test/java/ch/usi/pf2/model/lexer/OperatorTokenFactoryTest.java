package ch.usi.pf2.model.lexer;

import static org.junit.Assert.*;

import org.junit.Test;


/**
 * This class tests all methods and constructors of OperatorTokenFactory.
 * 
 * @author Alen Sugimoto
 * @version 03.06.2021
 */
public class OperatorTokenFactoryTest {
    
    @Test
    public void testMatchOne() {
        final StringTokenFactory f = new OperatorTokenFactory("a", TokenType.LAMBDA);
        f.setText("XXaXX");
        assertTrue(f.find(2));
        assertEquals(2, f.getTokenStartPosition());
        assertEquals(1, f.getTokenLength());
        assertEquals("a", f.getTokenText());
    }
    
    @Test
    public void testMatchTwo() {
        final StringTokenFactory f = new OperatorTokenFactory("ha", TokenType.LAMBDA);
        f.setText("XhaX");
        assertTrue(f.find(1));
        assertEquals(1, f.getTokenStartPosition());
        assertEquals(2, f.getTokenLength());
        assertEquals("ha", f.getTokenText());
    }
    
    @Test
    public void testMatchThereAndNotEarlier() {
        final StringTokenFactory f = new OperatorTokenFactory("hi", TokenType.LAMBDA);
        f.setText("XhiXhiX");
        assertTrue(f.find(4));
        assertEquals(4, f.getTokenStartPosition());
    }
    
    @Test
    public void testMatchThereAndNotLater() {
        final StringTokenFactory f = new OperatorTokenFactory("hi", TokenType.LAMBDA);
        f.setText("XhiXhiX");
        assertTrue(f.find(1));
        assertEquals(1, f.getTokenStartPosition());
    }
    
    @Test
    public void testNoMatchNowhere() {
        final StringTokenFactory f = new OperatorTokenFactory("hi", TokenType.LAMBDA);
        f.setText("abc");
        assertFalse(f.find(0));
        assertFalse(f.find(1));
        assertFalse(f.find(2));
    }
    
    @Test
    public void testStartOutOfBounds() {
        final StringTokenFactory f = new OperatorTokenFactory("hi", TokenType.LAMBDA);
        f.setText("abc");
        assertFalse(f.find(4)); // this call does not throw an IOOB exception
    }

    @Test
    public void testLambdaFound() {
        final OperatorTokenFactory f = new OperatorTokenFactory("\\", TokenType.LAMBDA);
        f.setText("ab\\(x)");
        assertTrue(f.find(2));
        assertEquals(2, f.getTokenStartPosition());
        assertEquals("\\", f.getTokenText());
        assertEquals(1, f.getTokenLength());
        assertEquals(2, f.getToken().getStartPosition());
        assertEquals(TokenType.LAMBDA, f.getToken().getType());
        assertEquals("\\", f.getToken().getText());
    }

    @Test
    public void testLambdaNotFound() {
        final OperatorTokenFactory f = new OperatorTokenFactory("\\", TokenType.LAMBDA);
        f.setText("ab.(x)");
        assertFalse(f.find(2));
    }

}
