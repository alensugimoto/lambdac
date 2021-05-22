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
    public void testVariableToString() {
        // "x"
        final Node e = new Variable(0, 0, 1);
        final Context context = new Context();
        context.add("x");
        assertEquals("x", e.toString(context));
    }
    
    @Test
    public void testAbstractionToString() {
        // "\\x.x"
        final Node e = new Abstraction(0, "x", new Variable(3, 0, 1));
        assertEquals("(\\x.x)", e.toString(new Context()));
    }
    
    @Test
    public void testApplicationToString() {
        // "x y"
        final Node e = new Application(0, new Variable(0, 0, 2), new Variable(2, 1, 2));
        final Context context = new Context();
        context.add("x");
        context.add("y");
        assertEquals("(x y)", e.toString(context));
    }
    
    @Test
    public void testPickFreshNameToString() {
        // "\\x.\\x.x"
        final Node e = new Abstraction(0, "x", new Abstraction(3, "x", new Variable(6, 0, 2)));
        assertEquals("(\\x.(\\x'.x'))", e.toString(new Context()));
    }
    
    @Test
    public void testApplicationVarToVar() {
        // "x y"
        final Node e = new Application(0, new Variable(0, 0, 2), new Variable(2, 1, 2));
        assertEquals(e, e.evaluate());
    }
    
    @Test
    public void testInterpretApplyVarToAbs() {
        // "x (\\y.y)"
        final Node e = new Application(
            0,
            new Variable(0, 0, 1),
            new Abstraction(
                3,
                "y",
                new Variable(6, 0, 2)
            )
        );
        assertEquals(e, e.evaluate());
    }
    
    @Test
    public void testInterpretApplyAbsToVar() {
        // "(\\y.y) x"
        final Node e = new Application(
            0,
            new Abstraction(
                1,
                "y",
                new Variable(4, 0, 2)
            ),
            new Variable(7, 0, 1)
        );
        assertEquals(e, e.evaluate());
    }
    
    @Test
    public void testInterpretApplyAbsToAbs() {
        // "(\\y.x y) (\\z.z)"
        final Node leftTerm = new Abstraction(
            1,
            "y",
            new Application(
                4,
                new Variable(4, 1, 2),
                new Variable(6, 0, 2)
            )
        );
        final Node rightTerm = new Abstraction(
            8,
            "z",
            new Variable(11, 0, 2)
        );
        final Node e = new Application(0, leftTerm, rightTerm);
        final Node expected = new Application(
            4,
            new Variable(4, 0, 1),
            new Abstraction(
                8,
                "z",
                new Variable(11, 0, 2)
            )
        );
        assertEquals(expected, e.evaluate());
    }
    
}
