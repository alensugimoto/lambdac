package ch.usi.pf2.model.lexer;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class IdentifierTokenFactoryTest {
    
    @Test
    public void testMatchFixed() {
        RegExTokenFactory f = new IdentifierTokenFactory();
        f.setText("$$hi$$");
        boolean found = f.find(2);
        assertTrue(found);
        assertEquals(2, f.getTokenStartPosition());
        assertEquals(2, f.getTokenLength());
        assertEquals("hi", f.getTokenText());
    }
    
    @Test
    public void testMatchFlexible() {
        RegExTokenFactory f = new IdentifierTokenFactory();
        f.setText("$haaa$");
        boolean found = f.find(1);
        assertTrue(found);
        assertEquals(1, f.getTokenStartPosition());
        assertEquals(4, f.getTokenLength());
        assertEquals("haaa", f.getTokenText());
    }
    
    @Test
    public void testMatchThereAndNotEarlier() {
        RegExTokenFactory f = new IdentifierTokenFactory();
        f.setText("$hi$hi$");
        boolean found = f.find(4);
        assertTrue(found);
        assertEquals(4, f.getTokenStartPosition());
    }
    
    @Test
    public void testMatchThereAndNotLater() {
        RegExTokenFactory f = new IdentifierTokenFactory();
        f.setText("$hi$hi$");
        boolean found = f.find(1);
        assertTrue(found);
        assertEquals(1, f.getTokenStartPosition());
    }
    
    @Test
    public void testNoMatchNowhere() {
        RegExTokenFactory f = new IdentifierTokenFactory();
        f.setText("1-*");
        assertFalse(f.find(0));
        assertFalse(f.find(1));
        assertFalse(f.find(2));
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testStartOutOfBounds() {
        RegExTokenFactory f = new IdentifierTokenFactory();
        f.setText("abc");
        f.find(4); // this call must throw an IOOB exception
    }
    
    @Test
    public void testFoundLength1() {
        IdentifierTokenFactory f = new IdentifierTokenFactory();
        f.setText("a=9x");
        boolean found = f.find(3);
        assertTrue(found);
        assertEquals(1, f.getTokenLength());
        assertEquals(3, f.getTokenStartPosition());
        assertEquals("x", f.getTokenText());
        assertEquals(TokenType.IDENTIFIER, f.getToken().getType());
    }
    
    @Test
    public void testFoundLength3() {
        IdentifierTokenFactory f = new IdentifierTokenFactory();
        f.setText("abc=1x1a+");
        boolean found = f.find(5);
        assertTrue(found);
        assertEquals(3, f.getTokenLength());
        assertEquals(5, f.getTokenStartPosition());
        assertEquals("x1a", f.getTokenText());
        assertEquals(TokenType.IDENTIFIER, f.getToken().getType());
    }
    
    @Test
    public void testNoMatchNotFound() {
        IdentifierTokenFactory f = new IdentifierTokenFactory();
        f.setText("123=456");
        boolean found = f.find(4);
        assertFalse(found);
    }

}
