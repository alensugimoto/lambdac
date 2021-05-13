import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


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
        // code under test
        final Node actualRoot = parser.parse(sourceCode);
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
        // code under test
        final Node actualRoot = parser.parse(sourceCode);
        // expected tree
        final Node expectedRoot = new Application(0, new Variable(0, 0, 1), new Variable(2, 1, 2));
        // assertion
        assertEquals(expectedRoot, actualRoot);
    }

    @Test
    public void testParentheses() {
        // setup
        final Parser parser = new Parser();
        // test input
        final String sourceCode = "(x)";
        // code under test
        final Node actualRoot = parser.parse(sourceCode);
        // expected tree
        final Node expectedRoot = new Variable(1, 0, 1);
        // assertion
        assertEquals(expectedRoot, actualRoot);
    }
    
}
