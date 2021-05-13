import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.LinkedList;


/**
 * Tests toString() of Node subclasses.
 */
public class NodeTest {
    
    @Test
    public void testVariable() {
        Node e = new Variable(0, 0, 1);
        List<String> context = new LinkedList<>();
        context.add("x");
        assertEquals("x", e.toString(context));
    }
    
    @Test
    public void testAbstraction() {
        Node e = new Abstraction(0, "x", new Variable(1, 0, 1));
        List<String> context = new LinkedList<>();
        assertEquals("(lambda x. x)", e.toString(context));
    }
    
    @Test
    public void testApplication() {
        Node e = new Application(0, new Variable(1, 0, 2), new Variable(2, 1, 2));
        List<String> context = new LinkedList<>();
        context.add("x");
        context.add("y");
        assertEquals("(x y)", e.toString(context));
    }
    
}
