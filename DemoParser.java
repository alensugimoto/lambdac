/**
 * A DemoParser for the very simple Demo language.
 * 
 * <code>
 * DEMO_EXPRESSION ::= Literal ("+" | "-") Literal
 * </code>
 */
public final class DemoParser implements Parser {
    
    private LexicalAnalyzer lexer;


    /**
     * Parse a program in the Demo language.
     * @param sourceCode The source code of the program in the Demo language
     * @return an AST of the program
     */
    public Node parse(final String sourceCode) {
        this.lexer = new LexicalAnalyzer(sourceCode);
        // fetch first token
        lexer.fetchNextToken();
        // now parse the DEMO_EXPRESSION
        return parseDemoExpression();
    }
    
    /**
     * Parse a DEMO_EXPRESSION (e.g., "12-2").
     * 
     * <p>EBNF:
     * <code>
     * DEMO_EXPRESSION ::= Literal ("+" | "-") Literal
     * </code>
     * 
     * @return a Node representing the demo expression
     */
    private Node parseDemoExpression() {
        // parse Literal
        if (lexer.getCurrentToken().getType() != TokenType.LITERAL) {
            System.out.println("Expected a LITERAL, got " + lexer.getCurrentToken());
            return null;
        }
        final Node left = new Literal(Integer.parseInt(lexer.getCurrentToken().getText()));
        lexer.fetchNextToken();
        
        // parse "+" or "-"
        boolean shouldAdd = false;
        if (lexer.getCurrentToken().getType() == TokenType.PLUS) {
            shouldAdd = true;
        } else if (lexer.getCurrentToken().getType() == TokenType.MINUS) {
            shouldAdd = false;
        } else {
            System.out.println("Expected a + or a -, got " + lexer.getCurrentToken());
            return null;
        }
        lexer.fetchNextToken();
        
        // parse Literal
        if (lexer.getCurrentToken().getType() != TokenType.LITERAL) {
            System.out.println("Expected a LITERAL, got " + lexer.getCurrentToken());
            return null;
        }
        final Node right = new Literal(Integer.parseInt(lexer.getCurrentToken().getText()));
        lexer.fetchNextToken();
        
        // produce Node
        if (shouldAdd) {
            return new Addition(left, right);
        } else {
            return new Subtraction(left, right);
        }
    }

}