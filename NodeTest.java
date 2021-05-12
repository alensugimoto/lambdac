import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;


/**
 * Tests toString() of Node subclasses.
 */
public class NodeTest {
    
    @Test
    public void testVariable() {
        Node e = new Variable(0, 0, 1);
        List<String> context;
        context.add(0, "x");
        assertEquals("x", e.toString());
    }
    
    @Test
    public void testAbstraction() {
        Node e = new Abstraction(new Variable("x"), new Variable("x"));
        assertEquals("(\\x.y)", e.toString());
    }
    
    @Test
    public void testApplication() {
        Node e = new Application(new Variable("x"), new Variable("y"));
        assertEquals("(x y)", e.toString());
    }
    
}
