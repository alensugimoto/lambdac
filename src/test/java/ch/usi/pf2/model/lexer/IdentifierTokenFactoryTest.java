package ch.usi.pf2.model.lexer;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;


/**
 * This class tests all methods and constructors of IdentifierTokenFactory.
 * 
 * @author Alen Sugimoto
 * @version 03.06.2021
 */
public class IdentifierTokenFactoryTest {
    
    private static RegExTokenFactory f;
    
    @BeforeClass
    public static void setup() {
        f = new IdentifierTokenFactory();
    }
    
    @Test
    public void testMatchFixed() {
        f.setText("$$hi$$");
        assertTrue(f.find(2));
        assertEquals(2, f.getTokenStartPosition());
        assertEquals(2, f.getTokenLength());
        assertEquals("hi", f.getTokenText());
    }
    
    @Test
    public void testMatchFlexible() {
        f.setText("$haaa$");
        assertTrue(f.find(1));
        assertEquals(1, f.getTokenStartPosition());
        assertEquals(4, f.getTokenLength());
        assertEquals("haaa", f.getTokenText());
    }
    
    @Test
    public void testMatchThereAndNotEarlier() {
        f.setText("$hi$hi$");
        assertTrue(f.find(4));
        assertEquals(4, f.getTokenStartPosition());
    }
    
    @Test
    public void testMatchThereAndNotLater() {
        f.setText("$hi$hi$");
        assertTrue(f.find(1));
        assertEquals(1, f.getTokenStartPosition());
    }
    
    @Test
    public void testNoMatchNowhere() {
        f.setText("1-*");
        assertFalse(f.find(0));
        assertFalse(f.find(1));
        assertFalse(f.find(2));
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testStartOutOfBounds() {
        f.setText("abc");
        f.find(4); // this call must throw an IOOB exception
    }
    
    @Test
    public void testFoundLength1() {
        f.setText("a=9x");
        assertTrue(f.find(3));
        assertEquals(1, f.getTokenLength());
        assertEquals(3, f.getTokenStartPosition());
        assertEquals("x", f.getTokenText());
        assertEquals(TokenType.IDENTIFIER, f.getToken().getType());
    }
    
    @Test
    public void testFoundLength3() {
        f.setText("abc=1x1a+");
        assertTrue(f.find(5));
        assertEquals(3, f.getTokenLength());
        assertEquals(5, f.getTokenStartPosition());
        assertEquals("x1a", f.getTokenText());
        assertEquals(TokenType.IDENTIFIER, f.getToken().getType());
    }
    
    @Test
    public void testNoMatchNotFound() {
        f.setText("123=456");
        assertFalse(f.find(4));
    }

}
