package ch.usi.pf2.model.parser;

import ch.usi.pf2.model.ast.Abstraction;
import ch.usi.pf2.model.ast.Application;
import ch.usi.pf2.model.ast.Node;
import ch.usi.pf2.model.ast.Variable;
import ch.usi.pf2.model.interpreter.Context;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class ParserTest {
    
    private static Parser parser;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    
    @BeforeClass
    public static void setUp() {
        parser = new Parser();
    }
    
    @Test
    public void testDefinedVariable() throws ParseException {
        final String sourceCode = "x";
        final Context context = new Context();
        context.add("x");
        final Node actualRoot = parser.parse(sourceCode, context);
        final Node expectedRoot = new Variable(0, 0, 1);
        assertEquals(expectedRoot, actualRoot);
    }
    
    @Test
    public void testUndefinedVariable() throws ParseException {
        expectedEx.expect(ParseException.class);
        expectedEx.expectMessage("Variable 'x' is not defined");
        parser.parse("x");
    }

    @Test
    public void testAbstractionSimple() throws ParseException {
        final String sourceCode = "\\x.x";
        final Node actualRoot = parser.parse(sourceCode);
        final Node expectedRoot = new Abstraction(0, "x", new Variable(3, 0, 1));
        assertEquals(expectedRoot, actualRoot);
    }
    
    @Test
    public void testAbstractionRightExtension() throws ParseException {
        final String sourceCode = "\\x.x y";
        final Context context = new Context();
        context.add("y");
        final Node actualRoot = parser.parse(sourceCode, context);
        final Node expectedChild = new Application(3, new Variable(3, 0, 2), new Variable(5, 1, 2));
        final Node expectedRoot = new Abstraction(0, "x", expectedChild);
        assertEquals(expectedRoot, actualRoot);
    }

    @Test
    public void testApplicationSimple() throws ParseException {
        final String sourceCode = "x y";
        final Context context = new Context();
        context.add("x");
        context.add("y");
        final Node actualRoot = parser.parse(sourceCode, context);
        final Node expectedRoot = new Application(0, new Variable(0, 0, 2), new Variable(2, 1, 2));
        assertEquals(expectedRoot, actualRoot);
    }
    
    @Test
    public void testApplicationLeftAssociativity() throws ParseException {
        final String sourceCode = "x y z";
        final Context context = new Context();
        context.add("x");
        context.add("y");
        context.add("z");
        final Node actualRoot = parser.parse(sourceCode, context);
        final Node expectedChild = new Application(0, new Variable(0, 0, 3), new Variable(2, 1, 3));
        final Node expectedRoot = new Application(0, expectedChild, new Variable(4, 2, 3));
        assertEquals(expectedRoot, actualRoot);
    }

    @Test
    public void testParentheses() throws ParseException {
        final String sourceCode = "(x)";
        final Context context = new Context();
        context.add("x");
        final Node actualRoot = parser.parse(sourceCode, context);
        final Node expectedRoot = new Variable(1, 0, 1);
        assertEquals(expectedRoot, actualRoot);
    }
    
    @Test
    public void testMissingClosedParentheses() throws ParseException {
        expectedEx.expect(ParseException.class);
        expectedEx.expectMessage("Expected closed parenthesis, but got ''");
        parser.parse("(\\x.x x");
    }
    
    @Test
    public void testExtraClosedParentheses() throws ParseException {
        expectedEx.expect(ParseException.class);
        expectedEx.expectMessage("Expected end of file, but got ')'");
        parser.parse("(\\x.x x))");
    }
    
    @Test
    public void testInvalidStartOfTerm() throws ParseException {
        expectedEx.expect(ParseException.class);
        expectedEx.expectMessage("Expected identifier or open parenthesis, but got ')'");
        parser.parse(")x.x x");
    }
    
    @Test
    public void testMissingIdentifierInAbstraction() throws ParseException {
        expectedEx.expect(ParseException.class);
        expectedEx.expectMessage("Expected identifier, but got '.'");
        parser.parse("\\.x x");
    }
    
    @Test
    public void testMissingDotInAbstraction() throws ParseException {
        expectedEx.expect(ParseException.class);
        expectedEx.expectMessage("Expected dot, but got '('");
        parser.parse("\\x(x x)");
    }
    
}
