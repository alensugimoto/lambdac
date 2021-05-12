import java.util.LinkedList;

/**
 * An Interpreter interprets source code.
 */
public class Interpreter {

    /**
     * Interpret the specified source code.
     * @param sourceCode the source code
     */
    public String interpret(final String sourceCode) {
        final Parser parser = new Parser();
        final Node root = parser.parse(sourceCode);
        return root.evaluate().toString(new LinkedList<>());
    }
    
}
