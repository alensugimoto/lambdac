package ch.usi.pf2.model.ast;

import ch.usi.pf2.model.context.Context;

import static org.junit.Assert.*;
import org.junit.Test;


/**
 * Tests toString() and evaluate() of Node subclasses.
 */
public class NodeTest {
    
    @Test
    public void testVariableToString() {
        // "x"
        final Node root = new Variable(0, 0, 1);
        final Context context = new Context();
        context.add("x");
        assertEquals("x", root.toString(context));
    }
    
    @Test
    public void testAbstractionToString() {
        // "\\x.x"
        final Node root = new Abstraction(0, "x", new Variable(3, 0, 1));
        assertEquals("(\\x.x)", root.toString(new Context()));
    }
    
    @Test
    public void testApplicationToString() {
        // "x y"
        final Node root = new Application(0, new Variable(0, 0, 2), new Variable(2, 1, 2));
        final Context context = new Context();
        context.add("x");
        context.add("y");
        assertEquals("(x y)", root.toString(context));
    }
    
    @Test
    public void testPickFreshNameToString() {
        // "\\x.\\x.x"
        final Node root = new Abstraction(0, "x", new Abstraction(3, "x", new Variable(6, 0, 2)));
        assertEquals("(\\x.(\\x'.x'))", root.toString(new Context()));
    }
    
    @Test
    public void testApplicationVarToVar() {
        // "x y"
        final Node root = new Application(0, new Variable(0, 0, 2), new Variable(2, 1, 2));
        assertEquals(root, root.evaluate());
    }
    
    @Test
    public void testApplicationVarToAbs() {
        // "x (\\y.y)"
        final Node root = new Application(
            0,
            new Variable(0, 0, 1),
            new Abstraction(
                3,
                "y",
                new Variable(6, 0, 2)
            )
        );
        assertEquals(root, root.evaluate());
    }
    
    @Test
    public void testApplicationAbsToVar() {
        // "(\\y.y) x"
        final Node root = new Application(
            0,
            new Abstraction(
                1,
                "y",
                new Variable(4, 0, 2)
            ),
            new Variable(7, 0, 1)
        );
        assertEquals(root, root.evaluate());
    }
    
    @Test
    public void testApplicationAbsToAbs() {
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
        final Node root = new Application(0, leftTerm, rightTerm);
        final Node expectedRoot = new Application(
            4,
            new Variable(4, 0, 1),
            new Abstraction(
                8,
                "z",
                new Variable(11, 0, 2)
            )
        );
        assertEquals(expectedRoot, root.evaluate());
    }
    
    @Test
    public void testVariableUncapture() {
        // "(\\x.\\x.x) y" -> "(\\x.\\x.x)"
        final Variable rightTerm = new Variable(10, 0, 1);
        final Abstraction leftTermChild = new Abstraction(4, "x", new Variable(7, 0, 3));
        final Abstraction leftTerm = new Abstraction(1, "x", leftTermChild);
        final Node expectedRoot = new Abstraction(4, "x", new Variable(7, 0, 2));
        assertEquals(expectedRoot, leftTerm.apply(rightTerm));
    }
    
    @Test
    public void testVariableCapture() {
        // "(\\x.\\y.x) y" -> "\\y'.y"
        final Variable rightTerm = new Variable(10, 0, 1);
        final Abstraction leftTermChild = new Abstraction(4, "y", new Variable(7, 1, 3));
        final Abstraction leftTerm = new Abstraction(1, "x", leftTermChild);
        final Node expectedRoot = new Abstraction(4, "y", new Variable(10, 1, 2));
        assertEquals(expectedRoot, leftTerm.apply(rightTerm));
    }
    
}
