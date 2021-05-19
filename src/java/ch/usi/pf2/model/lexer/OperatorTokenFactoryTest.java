package ch.usi.pf2.model.lexer;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class OperatorTokenFactoryTest {
    
    @Test
    public void testMatchOne() {
        StringTokenFactory f = new OperatorTokenFactory("a", TokenType.LAMBDA);
        f.setText("XXaXX");
        boolean found = f.find(2);
        assertTrue(found);
        assertEquals(2, f.getTokenStartPosition());
        assertEquals(1, f.getTokenLength());
        assertEquals("a", f.getTokenText());
    }
    
    @Test
    public void testMatchTwo() {
        StringTokenFactory f = new OperatorTokenFactory("ha", TokenType.LAMBDA);
        f.setText("XhaX");
        boolean found = f.find(1);
        assertTrue(found);
        assertEquals(1, f.getTokenStartPosition());
        assertEquals(2, f.getTokenLength());
        assertEquals("ha", f.getTokenText());
    }
    
    @Test
    public void testMatchThereAndNotEarlier() {
        StringTokenFactory f = new OperatorTokenFactory("hi", TokenType.LAMBDA);
        f.setText("XhiXhiX");
        boolean found = f.find(4);
        assertTrue(found);
        assertEquals(4, f.getTokenStartPosition());
    }
    
    @Test
    public void testMatchThereAndNotLater() {
        StringTokenFactory f = new OperatorTokenFactory("hi", TokenType.LAMBDA);
        f.setText("XhiXhiX");
        boolean found = f.find(1);
        assertTrue(found);
        assertEquals(1, f.getTokenStartPosition());
    }
    
    @Test
    public void testNoMatchNowhere() {
        StringTokenFactory f = new OperatorTokenFactory("hi", TokenType.LAMBDA);
        f.setText("abc");
        assertFalse(f.find(0));
        assertFalse(f.find(1));
        assertFalse(f.find(2));
    }
    
    @Test
    public void testStartOutOfBounds() {
        StringTokenFactory f = new OperatorTokenFactory("hi", TokenType.LAMBDA);
        f.setText("abc");
        assertFalse(f.find(4)); // this call does not throw an IOOB exception
    }

    @Test
    public void testLambdaFound() {
        OperatorTokenFactory f = new OperatorTokenFactory("\\", TokenType.LAMBDA);
        f.setText("ab\\(x)");
        boolean found = f.find(2);
        assertTrue(found);
        assertEquals(2, f.getTokenStartPosition());
        assertEquals("\\", f.getTokenText());
        assertEquals(1, f.getTokenLength());
        assertEquals(2, f.getToken().getStartPosition());
        assertEquals(TokenType.LAMBDA, f.getToken().getType());
        assertEquals("\\", f.getToken().getText());
    }

    @Test
    public void testLambdaNotFound() {
        OperatorTokenFactory f = new OperatorTokenFactory("\\", TokenType.LAMBDA);
        f.setText("ab.(x)");
        boolean found = f.find(2);
        assertFalse(found);
    }

}
