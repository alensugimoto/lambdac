package ch.usi.pf2.model.lexer;

import ch.usi.pf2.model.parser.ParseException;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class LexicalAnalyzerTest {
    
    @Test
    public void testInitial() throws ParseException {
        final LexicalAnalyzer l = new LexicalAnalyzer("");
        assertNull(l.getCurrentToken());
    }
    
    @Test
    public void testEof() throws ParseException {
        final LexicalAnalyzer l = new LexicalAnalyzer("");
        l.fetchNextToken();
        Token t = l.getCurrentToken();
        assertEquals(TokenType.END_OF_FILE, t.getType());
        assertEquals(0, t.getStartPosition());
    }
    
    @Test
    public void testEofNextEof() throws ParseException {
        final LexicalAnalyzer l = new LexicalAnalyzer("");
        l.fetchNextToken();
        l.fetchNextToken();
        Token t = l.getCurrentToken();
        assertEquals(TokenType.END_OF_FILE, t.getType());
        assertEquals(0, t.getStartPosition());
    }
    
    @Test
    public void testOne() throws ParseException {
        final LexicalAnalyzer l = new LexicalAnalyzer("\\");
        l.fetchNextToken();
        Token t0 = l.getCurrentToken();
        assertEquals(TokenType.LAMBDA, t0.getType());
        assertEquals(0, t0.getStartPosition());
        l.fetchNextToken();
        Token t1 = l.getCurrentToken();
        assertEquals(TokenType.END_OF_FILE, t1.getType());
        assertEquals(1, t1.getStartPosition());
    }
    
    @Test
    public void testTwo() throws ParseException {
        final LexicalAnalyzer l = new LexicalAnalyzer("\\.");
        l.fetchNextToken();
        Token t = l.getCurrentToken();
        assertEquals(TokenType.LAMBDA, t.getType());
        assertEquals(0, t.getStartPosition());
        l.fetchNextToken();
        t = l.getCurrentToken();
        assertEquals(TokenType.DOT, t.getType());
        assertEquals(1, t.getStartPosition());
        l.fetchNextToken();
        t = l.getCurrentToken();
        assertEquals(TokenType.END_OF_FILE, t.getType());
        assertEquals(2, t.getStartPosition());
    }
    
    @Test
    public void testThree() throws ParseException {
        final LexicalAnalyzer l = new LexicalAnalyzer("(xyz)");
        l.fetchNextToken();
        Token t = l.getCurrentToken();
        assertEquals(TokenType.OPEN_PAREN, t.getType());
        assertEquals(0, t.getStartPosition());
        l.fetchNextToken();
        t = l.getCurrentToken();
        assertEquals(TokenType.IDENTIFIER, t.getType());
        assertEquals(1, t.getStartPosition());
        l.fetchNextToken();
        t = l.getCurrentToken();
        assertEquals(TokenType.CLOSED_PAREN, t.getType());
        assertEquals(4, t.getStartPosition());
        l.fetchNextToken();
        t = l.getCurrentToken();
        assertEquals(TokenType.END_OF_FILE, t.getType());
        assertEquals(5, t.getStartPosition());
    }
    
    @Test(expected = ParseException.class)
    public void testIllegalToken() throws ParseException {
        final LexicalAnalyzer l = new LexicalAnalyzer("^");
        l.fetchNextToken();
        Token t = l.getCurrentToken();
    }
    
    @Test
    public void testLongestFoundToken() throws ParseException {
        final LexicalAnalyzer l = new LexicalAnalyzer("..", new TokenFactory[] {
            new OperatorTokenFactory("..", TokenType.LAMBDA),
            new OperatorTokenFactory(".", TokenType.DOT)
        });
        l.fetchNextToken();
        Token t = l.getCurrentToken();
        assertEquals(TokenType.LAMBDA, t.getType());
        assertEquals("..", t.getText());
    }
    
}
