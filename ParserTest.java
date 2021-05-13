import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.LinkedList;


/**
 * This test class will test some aspects of the rules
 * of the untyped lambda calculus.
 * 
 * <pre>
 * TERM        ::= ABSTRACTION | APPLICATION
 * ATOM        ::= Identifier | "(" TERM ")"
 * ABSTRACTION ::= "\\" Identifier "." TERM
 * APPLICATION ::= ATOM { ATOM }
 * </pre>
 */
public class ParserTest {

    @Test
    public void testVariable() {
        // setup
        final Parser parser = new Parser();
        // test input
        final String sourceCode = "x";
        // context
        final List<String> context = new LinkedList<>();
        context.add("x");
        // code under test
        final Node actualRoot = parser.parse(sourceCode, context);
        // expected tree
        final Node expectedRoot = new Variable(0, 0, 1);
        // assertion
        assertEquals(expectedRoot, actualRoot);
    }

    @Test
    public void testAbstraction() {
        // setup
        final Parser parser = new Parser();
        // test input
        final String sourceCode = "\\x.x";
        // code under test
        final Node actualRoot = parser.parse(sourceCode);
        // expected tree
        final Node expectedRoot = new Abstraction(0, "x", new Variable(3, 0, 1));
        // assertion
        assertEquals(expectedRoot, actualRoot);
    }

    @Test
    public void testApplication() {
        // setup
        final Parser parser = new Parser();
        // test input
        final String sourceCode = "x y";
        // context
        final List<String> context = new LinkedList<>();
        context.add("x");
        context.add("y");
        // code under test
        final Node actualRoot = parser.parse(sourceCode, context);
        // expected tree
        final Node expectedRoot = new Application(0, new Variable(0, 0, 2), new Variable(2, 1, 2));
        // assertion
        assertEquals(expectedRoot, actualRoot);
    }

    @Test
    public void testParentheses() {
        // setup
        final Parser parser = new Parser();
        // test input
        final String sourceCode = "(x)";
        // context
        final List<String> context = new LinkedList<>();
        context.add("x");
        // code under test
        final Node actualRoot = parser.parse(sourceCode, context);
        // expected tree
        final Node expectedRoot = new Variable(1, 0, 1);
        // assertion
        assertEquals(expectedRoot, actualRoot);
    }
    
}
