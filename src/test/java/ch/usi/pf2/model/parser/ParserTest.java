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


/**
 * This class tests the Parser class.
 * 
 * @author Alen Sugimoto
 * @version 03.06.2021
 */
public class ParserTest {
    
    private static Parser parser;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    
    @BeforeClass
    public static void setUp() {
        parser = new Parser();
    }
    
    @Test
    public void testParseDefinedVariable() throws ParseException {
        final String sourceCode = "x";
        final Context context = new Context();
        context.add("x");
        final Node actualRoot = parser.parse(sourceCode, context);
        final Node expectedRoot = new Variable(0, 0, 1);
        assertEquals(expectedRoot, actualRoot);
    }
    
    @Test
    public void testParseUndefinedVariable() throws ParseException {
        expectedEx.expect(ParseException.class);
        expectedEx.expectMessage("Variable 'x' is not defined");
        parser.parse("x");
    }

    @Test
    public void testParseAbstractionSimple() throws ParseException {
        final String sourceCode = "\\x.x";
        final Node actualRoot = parser.parse(sourceCode);
        final Node expectedRoot = new Abstraction(0, "x", new Variable(3, 0, 1));
        assertEquals(expectedRoot, actualRoot);
    }
    
    @Test
    public void testParseAbstractionRightExtension() throws ParseException {
        final String sourceCode = "\\x.x y";
        final Context context = new Context();
        context.add("y");
        final Node actualRoot = parser.parse(sourceCode, context);
        final Node expectedChild = new Application(3, new Variable(3, 0, 2), new Variable(5, 1, 2));
        final Node expectedRoot = new Abstraction(0, "x", expectedChild);
        assertEquals(expectedRoot, actualRoot);
    }

    @Test
    public void testParseApplicationSimple() throws ParseException {
        final String sourceCode = "x y";
        final Context context = new Context();
        context.add("x");
        context.add("y");
        final Node actualRoot = parser.parse(sourceCode, context);
        final Node expectedRoot = new Application(0, new Variable(0, 0, 2), new Variable(2, 1, 2));
        assertEquals(expectedRoot, actualRoot);
    }
    
    @Test
    public void testParseApplicationLeftAssociativity() throws ParseException {
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
    public void testParseParenthesesHighPrecedence() throws ParseException {
        final String sourceCode = "x (x x)";
        final Context context = new Context();
        context.add("x");
        final Node actualRoot = parser.parse(sourceCode, context);
        final Node expectedChild = new Application(3, new Variable(3, 0, 1), new Variable(5, 0, 1));
        final Node expectedRoot = new Application(0, new Variable(0, 0, 1), expectedChild);
        assertEquals(expectedRoot, actualRoot);
    }

    @Test
    public void testParseRedundantParentheses() throws ParseException {
        final Context context = new Context();
        context.add("x");
        final Node actualRoot = parser.parse("(x x)", context);
        final Node expectedRoot = new Application(1, new Variable(1, 0, 1), new Variable(3, 0, 1));
        assertEquals(expectedRoot, actualRoot);
    }
    
    @Test
    public void testParseMissingClosedParenthesis() throws ParseException {
        expectedEx.expect(ParseException.class);
        expectedEx.expectMessage("Expected closed parenthesis, but got ''");
        parser.parse("(\\x.x x");
    }
    
    @Test
    public void testParseInvalidEndOfTerm() throws ParseException {
        expectedEx.expect(ParseException.class);
        expectedEx.expectMessage("Expected end of file, but got ')'");
        parser.parse("(\\x.x x))");
    }
    
    @Test
    public void testParseInvalidStartOfTerm() throws ParseException {
        expectedEx.expect(ParseException.class);
        expectedEx.expectMessage("Expected lambda, identifier or open parenthesis, but got ')'");
        parser.parse(")x.x x");
    }
    
    @Test
    public void testParseMissingIdentifierInAbstraction() throws ParseException {
        expectedEx.expect(ParseException.class);
        expectedEx.expectMessage("Expected identifier, but got '.'");
        parser.parse("\\.x x");
    }
    
    @Test
    public void testParseMissingDotInAbstraction() throws ParseException {
        expectedEx.expect(ParseException.class);
        expectedEx.expectMessage("Expected dot, but got '('");
        parser.parse("\\x(x x)");
    }
    
}
