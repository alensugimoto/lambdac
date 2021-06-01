package ch.usi.pf2.model.interpreter;

import ch.usi.pf2.model.parser.ParseException;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;


public class InterpreterTest {
    
    private static String identity;
    private static String churchTrue;
    private static String churchFalse;
    private static String ifThenElse;
    private static String and;
    private static String isZero;
    private static String succ;
    private static String pred;
    private static String minus;
    private static String leq;
    private static String eq;
    private static String zero;
    private static String one;
    
    @BeforeClass
    public static void setUp() {
        identity = "(\\x.x)";
        churchTrue = "(\\t.\\f.t)";
        churchFalse = "(\\t.\\f.f)";
        ifThenElse = "(\\l.\\m.\\n.l m n)";
        and = "(\\b.\\c.b c" + churchFalse + ")";
        isZero = "(\\n.n (\\x." + churchFalse + ")" + churchTrue + ")";
        succ = "(\\n.\\s.\\z.s (n s z))";
        pred = "(\\n.\\f.\\x.n (\\g.\\h.h (g f)) (\\u.x) (\\u.u))";
        minus = "(\\m.\\n.n" + pred + "m)";
        leq = "(\\m.\\n." + isZero + "(" + minus + "m n))";
        eq = "(\\m.\\n." + and + "(" + leq + "m n) (" + leq + "n m))";
        zero = "(\\s.\\z.z)";
        one = "(\\s.\\z.s z)";
    }

    @Test(expected = ParseException.class)
    public void testInterpretUndefinedVariable() throws ParseException {
        final Interpreter interpreter = new Interpreter();
        interpreter.interpret("x");
    }
    
    @Test(expected = ParseException.class)
    public void testInterpretBadSyntax() throws ParseException {
        final Interpreter interpreter = new Interpreter();
        interpreter.interpret("\\_.x");
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
        String actualString = interpreter.interpret(ifThenElse + churchTrue + "(\\x.x) (\\y.y)");
        assertEquals("(\\x.x)", actualString);
        actualString = interpreter.interpret(ifThenElse + churchFalse + "(\\x.x) (\\y.y)");
        assertEquals("(\\y.y)", actualString);
    }
    
    @Test
    public void testInterpretAnd() throws ParseException {
        final Interpreter interpreter = new Interpreter();
        String actualString = interpreter.interpret(and + churchTrue + churchTrue);
        assertEquals("(\\t.(\\f.t))", actualString);
        actualString = interpreter.interpret(and + churchTrue + churchFalse);
        assertEquals("(\\t.(\\f.f))", actualString);
    }

    @Test
    public void testInterpretEquals() throws ParseException {
        final Interpreter interpreter = new Interpreter();
        String actualString = interpreter.interpret(eq + one + "(" + succ + zero + ")");
        assertEquals("(\\t.(\\f.t))", actualString);
        actualString = interpreter.interpret(eq + zero + "(" + succ + one + ")");
        assertEquals("(\\t.(\\f.f))", actualString);
    }
    
}
