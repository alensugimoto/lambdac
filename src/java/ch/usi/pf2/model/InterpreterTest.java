package ch.usi.pf2.model;

import ch.usi.pf2.model.context.Context;
import ch.usi.pf2.model.parser.ParseException;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * This test class will test the Interpreter.
 */
public class InterpreterTest {
    
    private String identity;
    private String churchTrue;
    private String churchFalse;
    private String ifThenElse;
    private String and;
    
    private String wrap(final String termString) {
        return "(" + termString + ")";
    }
    
    @Before
    public void init() {
        identity = "\\x.x";
        churchTrue = "\\t.\\f.t";
        churchFalse = "\\t.\\f.f";
        ifThenElse = "\\l.\\m.\\n.l m n";
        and = "\\b.\\c.b c " + wrap(churchFalse);
    }
    
    @Test
    public void testInterpretVariable() throws ParseException {
        final Context context = new Context();
        context.add("x");
        final Interpreter interpreter = new Interpreter(context);
        final String actualString = interpreter.interpret("x");
        assertEquals("x", actualString);
    }
    
    @Test
    public void testInterpretApplyVarToVar() throws ParseException {
        final Context context = new Context();
        context.add("x");
        context.add("y");
        final Interpreter interpreter = new Interpreter(context);
        final String actualString = interpreter.interpret("x y");
        assertEquals("(x y)", actualString);
    }
    
    @Test
    public void testInterpretApplyVarToAbs() throws ParseException {
        final Context context = new Context();
        context.add("x");
        final Interpreter interpreter = new Interpreter(context);
        final String actualString = interpreter.interpret("x (\\y.y)");
        assertEquals("(x (\\y.y))", actualString);
    }
    
    @Test
    public void testInterpretApplyAbsToVar() throws ParseException {
        final Context context = new Context();
        context.add("x");
        final Interpreter interpreter = new Interpreter(context);
        final String actualString = interpreter.interpret("(\\y.y) x");
        assertEquals("((\\y.y) x)", actualString);
    }
    
    @Test
    public void testInterpretApplyAbsToAbs() throws ParseException {
        final Interpreter interpreter = new Interpreter();
        final String actualString = interpreter.interpret("(\\y.y) (\\z.z z)");
        assertEquals("(\\z.(z z))", actualString);
    }
    
    @Test
    public void testInterpretParentheses() throws ParseException {
        final Interpreter interpreter = new Interpreter();
        final String actualString = interpreter.interpret("(\\y.y) ((\\x.x) (\\z.z z))");
        assertEquals("(\\z.(z z))", actualString);
    }
    
    @Test
    public void testInterpretIdentity() throws ParseException {
        final Interpreter interpreter = new Interpreter();
        final String actualString = interpreter.interpret(identity);
        assertEquals("(\\x.x)", actualString);
    }
    
    @Test
    public void testInterpretTrue() throws ParseException {
        final Interpreter interpreter = new Interpreter();
        final String actualString = interpreter.interpret(churchTrue);
        assertEquals("(\\t.(\\f.t))", actualString);
    }
    
    @Test
    public void testInterpretFalse() throws ParseException {
        final Interpreter interpreter = new Interpreter();
        final String actualString = interpreter.interpret(churchFalse);
        assertEquals("(\\t.(\\f.f))", actualString);
    }
    
    @Test
    public void testInterpretIf() throws ParseException {
        final Interpreter interpreter = new Interpreter();
        String actualString = interpreter.interpret(
            wrap(ifThenElse) + " " + wrap(churchTrue) + " (\\x.x) (\\y.y)");
        assertEquals("(\\x.x)", actualString);
        actualString = interpreter.interpret(
            wrap(ifThenElse) + " " + wrap(churchFalse) + " (\\x.x) (\\y.y)");
        assertEquals("(\\y.y)", actualString);
    }
    
    @Test
    public void testInterpretAnd() throws ParseException {
        final Interpreter interpreter = new Interpreter();
        String actualString = interpreter.interpret(
            wrap(and) + " " + wrap(churchTrue) + " " + wrap(churchTrue));
        assertEquals("(\\t.(\\f.t))", actualString);
        actualString = interpreter.interpret(
            wrap(and) + " " + wrap(churchTrue) + " " + wrap(churchFalse));
        assertEquals("(\\t.(\\f.f))", actualString);
    }
    
}
