package ch.usi.pf2.model;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * This test class will test the Interpreter.
 */
public class InterpreterTest {
    
    @Test
    public void testInterpret1() {
        // setup
        final Interpreter interpreter = new Interpreter();
        // test input
        final String sourceCode = "\\x.x";
        // code under test
        final String actualString = interpreter.interpret(sourceCode);
        // expected string
        final String expectedString = "(\\x.x)";
        // assertion
        assertEquals(expectedString, actualString);
    }
    
    @Test
    public void testInterpret2() {
        // setup
        final Interpreter interpreter = new Interpreter();
        // test input
        final String sourceCode = "(\\x.x) (\\y.y y)";
        // code under test
        final String actualString = interpreter.interpret(sourceCode);
        // expected string
        final String expectedString = "(\\y.(y y))";
        // assertion
        assertEquals(expectedString, actualString);
    }
    
    @Test
    public void testInterpret3() {
        // setup
        final Interpreter interpreter = new Interpreter();
        // test input
        final String sourceCode = "(\\x.x) ((\\x.x) (\\z.(\\x.x) z))";
        // code under test
        final String actualString = interpreter.interpret(sourceCode);
        // expected string
        final String expectedString = "(\\z.((\\x.x) z))";
        // assertion
        assertEquals(expectedString, actualString);
    }
    
    /*
    @Test
    public void testInterpret4() {
        // setup
        final Interpreter interpreter = new Interpreter();
        // test input
        final String sourceCode = "\\x.(\\x.x)";
        // code under test
        final String actualString = interpreter.interpret(sourceCode);
        // expected string
        final String expectedString = "(\\x.(\\x'.x'))";
        // assertion
        assertEquals(expectedString, actualString);
    }
    
    @Test
    public void testInterpret5() {
        // setup
        final Interpreter interpreter = new Interpreter();
        // test input
        final String sourceCode = "(\\x.(\\x.x) (\\y.y)) (\\z.z)";
        // code under test
        final String actualString = interpreter.interpret(sourceCode);
        // expected string
        final String expectedString = "(\\x.(\\x'.x'))";
        // assertion
        assertEquals(expectedString, actualString);
    }
    */
    
}
