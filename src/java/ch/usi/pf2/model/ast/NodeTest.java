package ch.usi.pf2.model.ast;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.usi.pf2.model.context.Context;


/**
 * Tests toString() of Node subclasses.
 */
public class NodeTest {
    
    @Test
    public void testVariable() {
        Node e = new Variable(0, 0, 1);
        Context context = new Context();
        context.add("x");
        assertEquals("x", e.toString(context));
    }
    
    @Test
    public void testAbstraction() {
        Node e = new Abstraction(0, "x", new Variable(3, 0, 1));
        Context context = new Context();
        assertEquals("(\\x.x)", e.toString(context));
    }
    
    @Test
    public void testApplication() {
        Node e = new Application(0, new Variable(0, 0, 2), new Variable(2, 1, 2));
        Context context = new Context();
        context.add("x");
        context.add("y");
        assertEquals("(x y)", e.toString(context));
    }
    
}
