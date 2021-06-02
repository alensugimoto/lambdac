package ch.usi.pf2.model.ast;

import ch.usi.pf2.model.interpreter.Context;
import ch.usi.pf2.model.interpreter.InvalidContextLengthException;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


/**
 * This class tests all methods and constructors of the Node classes.
 * 
 * @author Alen Sugimoto
 * @version 03.06.2021
 */
public class NodeTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    
    @Test
    public void testToStringVariableWithValidContext() {
        // "x"
        final Node root = new Variable(0, 0, 1);
        final Context context = new Context();
        context.add("x");
        assertEquals("x", root.toString(context));
    }

    @Test
    public void testToStringVariableWithInvalidContext() {
        // "x"
        expectedEx.expect(InvalidContextLengthException.class);
        expectedEx.expectMessage("Expected a context length of 1, but got 0");
        final Node root = new Variable(0, 0, 1);
        root.toString(new Context());
    }
    
    @Test
    public void testToStringAbstraction() {
        // "\\x.x"
        final Node root = new Abstraction(0, "x", new Variable(3, 0, 1));
        assertEquals("(\\x.x)", root.toString(new Context()));
    }
    
    @Test
    public void testToStringApplication() {
        // "x y"
        final Node root = new Application(0, new Variable(0, 0, 2), new Variable(2, 1, 2));
        final Context context = new Context();
        context.add("x");
        context.add("y");
        assertEquals("(x y)", root.toString(context));
    }
    
    @Test
    public void testToStringPickFreshName() {
        // "\\x.\\x.x"
        final Node root = new Abstraction(0, "x", new Abstraction(3, "x", new Variable(6, 0, 2)));
        assertEquals("(\\x.(\\x'.x'))", root.toString(new Context()));
    }
    
    @Test
    public void testEvaluateNonValueToNonValueApplication() {
        // "x y"
        final Node root = new Application(0, new Variable(0, 0, 2), new Variable(2, 1, 2));
        assertEquals(root, root.evaluate());
    }
    
    @Test
    public void testEvaluateNonValueToValueApplication() {
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
    public void testEvaluateValueToNonValueApplication() {
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
    public void testEvaluateValueToValueApplication() {
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
    public void testEvaluateNonValueToDeepValueApplication() {
        // "x ((\\y.y) (\\z.z))"
        final Node root = new Application(
            0,
            new Variable(0, 0, 1),
            new Application(
                3,
                new Abstraction(
                    4,
                    "y",
                    new Variable(7, 0, 2)
                ),
                new Abstraction(
                    11,
                    "z",
                    new Variable(14, 0, 2)
                )
            )
        );
        assertEquals(root, root.evaluate());
    }
    
    @Test
    public void testEvaluateValueToDeepNonValueApplication() {
        // "(\\y.x y) ((\\y.x y) (\\z.z))"
        final Node leftTerm = new Abstraction(
            11,
            "y",
            new Application(
                14,
                new Variable(14, 1, 2),
                new Variable(16, 0, 2)
            )
        );
        final Node rightRightTerm = new Abstraction(
            18,
            "z",
            new Variable(21, 0, 2)
        );
        final Node rightTerm = new Application(0, leftTerm, rightRightTerm);
        final Node root = new Application(0, leftTerm, rightTerm);
        final Node expectedRoot = new Application(
            0,
            leftTerm,
            new Application(
                14,
                new Variable(14, 0, 1),
                new Abstraction(
                    18,
                    "z",
                    new Variable(21, 0, 2)
                )
            )
        );
        assertEquals(expectedRoot, root.evaluate());
    }
    
    @Test
    public void testEvaluateWithoutVariableUncapture() {
        // "(\\x.\\x.x) y" -> "\\x.x"
        final Node rightTerm = new Variable(10, 0, 1);
        final Node leftTermChild = new Abstraction(4, "x", new Variable(7, 0, 3));
        final Abstraction leftTerm = new Abstraction(1, "x", leftTermChild);
        final Node expectedRoot = new Abstraction(4, "x", new Variable(7, 0, 2));
        assertEquals(expectedRoot, leftTerm.apply(rightTerm));
    }
    
    @Test
    public void testEvaluateWithoutVariableCapture() {
        // "(\\x.\\y.x) y" -> "\\y'.y"
        final Node rightTerm = new Variable(10, 0, 1);
        final Node leftTermChild = new Abstraction(4, "y", new Variable(7, 1, 3));
        final Abstraction leftTerm = new Abstraction(1, "x", leftTermChild);
        final Node expectedRoot = new Abstraction(4, "y", new Variable(10, 1, 2));
        assertEquals(expectedRoot, leftTerm.apply(rightTerm));
    }
    
}
