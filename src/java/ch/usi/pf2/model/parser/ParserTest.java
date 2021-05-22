package ch.usi.pf2.model.parser;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.usi.pf2.model.ast.Abstraction;
import ch.usi.pf2.model.ast.Application;
import ch.usi.pf2.model.ast.Node;
import ch.usi.pf2.model.ast.Variable;
import ch.usi.pf2.model.context.Context;


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
    public void testVariable() throws ParseException {
        final Parser parser = new Parser();
        final String sourceCode = "x";
        final Context context = new Context();
        context.add("x");
        final Node actualRoot = parser.parse(sourceCode, context);
        final Node expectedRoot = new Variable(0, 0, 1);
        assertEquals(expectedRoot, actualRoot);
    }

    @Test
    public void testAbstraction() throws ParseException {
        final Parser parser = new Parser();
        final String sourceCode = "\\x.x";
        final Node actualRoot = parser.parse(sourceCode);
        final Node expectedRoot = new Abstraction(0, "x", new Variable(3, 0, 1));
        assertEquals(expectedRoot, actualRoot);
    }

    @Test
    public void testApplication() throws ParseException {
        final Parser parser = new Parser();
        final String sourceCode = "x y";
        final Context context = new Context();
        context.add("x");
        context.add("y");
        final Node actualRoot = parser.parse(sourceCode, context);
        final Node expectedRoot = new Application(0, new Variable(0, 0, 2), new Variable(2, 1, 2));
        assertEquals(expectedRoot, actualRoot);
    }

    @Test
    public void testParentheses() throws ParseException {
        final Parser parser = new Parser();
        final String sourceCode = "(x)";
        final Context context = new Context();
        context.add("x");
        final Node actualRoot = parser.parse(sourceCode, context);
        final Node expectedRoot = new Variable(1, 0, 1);
        assertEquals(expectedRoot, actualRoot);
    }
    
    @Test(expected = ParseException.class)
    public void testUndefinedVariable() throws ParseException {
        final Parser parser = new Parser();
        final String sourceCode = "x";
        final Node actualRoot = parser.parse(sourceCode);
    }
    
    @Test(expected = ParseException.class)
    public void testMissingClosedParentheses() throws ParseException {
        final Parser parser = new Parser();
        final String sourceCode = "(\\x.x x";
        final Node actualRoot = parser.parse(sourceCode);
    }
    
    @Test(expected = ParseException.class)
    public void testExtraClosedParentheses() throws ParseException {
        final Parser parser = new Parser();
        final String sourceCode = "(\\x.x x))";
        final Node actualRoot = parser.parse(sourceCode);
    }
    
    @Test(expected = ParseException.class)
    public void testStartOfTerm() throws ParseException {
        final Parser parser = new Parser();
        final String sourceCode = ")x.x x";
        final Node actualRoot = parser.parse(sourceCode);
    }
    
}
