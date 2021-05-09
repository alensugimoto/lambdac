package LexicalAnalyser;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class OperatorTokenFactoryTest {

    @Test
    public void testLambdaFound() {
        OperatorTokenFactory f = new OperatorTokenFactory("\\", TokenType.LAMBDA);
        f.setText("ab\\(x)");
        boolean found = f.find(2);
        assertTrue(found);
        assertEquals(2, f.getTokenStartPosition());
        assertEquals("\\", f.getTokenText());
        assertEquals(1, f.getTokenLength());
        assertEquals(2, f.getToken().getStartPosition());
        assertEquals(TokenType.LAMBDA, f.getToken().getType());
        assertEquals("\\", f.getToken().getText());
    }

    @Test
    public void testLambdaNotFound() {
        OperatorTokenFactory f = new OperatorTokenFactory("\\", TokenType.LAMBDA);
        f.setText("ab.(x)");
        boolean found = f.find(2);
        assertFalse(found);
    }

}
