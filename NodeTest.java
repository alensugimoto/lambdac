import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * Tests toString() of Node subclasses.
 */
public class NodeTest {
    
    @Test
    public void testVariable() {
        Node e = new Variable("x");
        assertEquals("x", e.toString());
    }
    
    @Test
    public void testAbstraction() {
        Node e = new Abstraction(new Variable("x"), new Variable("y"));
        assertEquals("(\\x.y)", e.toString());
    }
    
    @Test
    public void testApplication() {
        Node e = new Application(new Variable("x"), new Variable("y"));
        assertEquals("(x y)", e.toString());
    }
    
}
