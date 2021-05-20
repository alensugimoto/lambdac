package ch.usi.pf2.model;

import ch.usi.pf2.model.context.Context;

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
    public void testInterpretVariable() {
        final Interpreter interpreter = new Interpreter();
        final Context context = new Context();
        context.add("x");
        final String actualString = interpreter.interpret("x", context);
        assertEquals("x", actualString);
    }
    
    @Test
    public void testInterpretApplyVarToVar() {
        final Interpreter interpreter = new Interpreter();
        final Context context = new Context();
        context.add("x");
        context.add("y");
        final String actualString = interpreter.interpret("x y", context);
        assertEquals("(x y)", actualString);
    }
    
    @Test
    public void testInterpretApplyVarToAbs() {
        final Interpreter interpreter = new Interpreter();
        final Context context = new Context();
        context.add("x");
        final String actualString = interpreter.interpret("x (\\y.y)", context);
        assertEquals("(x (\\y.y))", actualString);
    }
    
    @Test
    public void testInterpretApplyAbsToVar() {
        final Interpreter interpreter = new Interpreter();
        final Context context = new Context();
        context.add("x");
        final String actualString = interpreter.interpret("(\\y.y) x", context);
        assertEquals("((\\y.y) x)", actualString);
    }
    
    @Test
    public void testInterpretApplyAbsToAbs() {
        final Interpreter interpreter = new Interpreter();
        final String actualString = interpreter.interpret("(\\y.y) (\\z.z z)");
        assertEquals("(\\z.(z z))", actualString);
    }
    
    @Test
    public void testInterpretParentheses() {
        final Interpreter interpreter = new Interpreter();
        final String actualString = interpreter.interpret("(\\y.y) ((\\x.x) (\\z.z z))");
        assertEquals("(\\z.(z z))", actualString);
    }
    
    @Test
    public void testInterpretIdentity() {
        final Interpreter interpreter = new Interpreter();
        final String actualString = interpreter.interpret(identity);
        assertEquals("(\\x.x)", actualString);
    }
    
    @Test
    public void testInterpretTrue() {
        final Interpreter interpreter = new Interpreter();
        final String actualString = interpreter.interpret(churchTrue);
        assertEquals("(\\t.(\\f.t))", actualString);
    }
    
    @Test
    public void testInterpretFalse() {
        final Interpreter interpreter = new Interpreter();
        final String actualString = interpreter.interpret(churchFalse);
        assertEquals("(\\t.(\\f.f))", actualString);
    }
    
    @Test
    public void testInterpretIf() {
        final Interpreter interpreter = new Interpreter();
        String actualString = interpreter.interpret(
            wrap(ifThenElse) + " " + wrap(churchTrue) + " (\\x.x) (\\y.y)");
        assertEquals("(\\x.x)", actualString);
        actualString = interpreter.interpret(
            wrap(ifThenElse) + " " + wrap(churchFalse) + " (\\x.x) (\\y.y)");
        assertEquals("(\\y.y)", actualString);
    }
    
    @Test
    public void testInterpretAnd() {
        final Interpreter interpreter = new Interpreter();
        String actualString = interpreter.interpret(
            wrap(and) + " " + wrap(churchTrue) + " " + wrap(churchTrue));
        assertEquals("(\\t.(\\f.t))", actualString);
        actualString = interpreter.interpret(
            wrap(and) + " " + wrap(churchTrue) + " " + wrap(churchFalse));
        assertEquals("(\\t.(\\f.f))", actualString);
    }
    
}
