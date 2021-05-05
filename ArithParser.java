/**
 * A Parser for our Arith language
 * (a simple language of arithmetic expressions).
 * 
 * <code>
 * EXPRESSION   ::= [ "+" | "-" ] TERM { ( "+" | "-" ) TERM }
 * TERM         ::= FACTOR { ( "*" | "/" ) FACTOR }
 * FACTOR       ::= Literal | 
 *                  Identifier| 
 *                  "(" EXPRESSION ")"
 * </code>
 */
public final class ArithParser implements Parser {
    
    private LexicalAnalyzer lexer;

    
    /**
     * Parse a program in the Arith language.
     * @param sourceCode The source code of the program in the Arith language
     * @return an AST of the program
     */
    public Node parse(final String sourceCode) {
        this.lexer = new LexicalAnalyzer(sourceCode);
        // fetch first token
        lexer.fetchNextToken();
        // now parse the EXPRESSION
        final Node root = parseExpression();
        // check if lexer is at the end of file
        if (lexer.getCurrentToken().getType() == TokenType.END_OF_FILE) {
            return root;
        } else {
            System.out.print("Expected " + TokenType.END_OF_FILE.getName());
            System.out.print(", got \"" + lexer.getCurrentToken().getText() + "\"");
            System.out.println();
            return null;
        }
    }
    
    /**
     * Parse an expression.
     * This assumes the lexer already points to the first token of this expression.
     * 
     * <p>EBNF:
     * <code>
     * EXPRESSION ::= [ "+" | "-" ] TERM { ( "+" | "-" ) TERM }
     * </code>
     * 
     * @return a Node representing the expression
     */
    private Node parseExpression() {
        // parse [ "+" | "-" ]
        final boolean shouldNegate;
        switch (lexer.getCurrentToken().getType()) {
            case PLUS:
            case MINUS:
                shouldNegate = lexer.getCurrentToken().getType() == TokenType.MINUS;
                lexer.fetchNextToken();
                break;
            default:
                shouldNegate = false;
                break;
        }
        
        // parse TERM
        Node root = parseTerm();
        
        // update Node
        if (shouldNegate) {
            root = new Negation(root);
        }
        
        while (lexer.getCurrentToken().getType() == TokenType.PLUS
            || lexer.getCurrentToken().getType() == TokenType.MINUS) {
            // parse ( "+" or "-" )
            final boolean shouldAdd = lexer.getCurrentToken().getType() == TokenType.PLUS;
            lexer.fetchNextToken();
            // parse TERM
            final Node right = parseTerm();
            // update Node
            if (shouldAdd) {
                root = new Addition(root, right);
            } else {
                root = new Subtraction(root, right);
            }
        }
        
        return root;
    }
    
    /**
     * Parse a term.
     * This assumes the lexer already points to the first token of this term.
     * 
     * <p>EBNF:
     * <code>
     * TERM ::= FACTOR { ( "*" | "/" ) FACTOR }
     * </code>
     * 
     * @return a Node representing the term
     */
    private Node parseTerm() {
        // parse FACTOR
        Node root = parseFactor();
        
        while (lexer.getCurrentToken().getType() == TokenType.STAR
            || lexer.getCurrentToken().getType() == TokenType.SLASH) {
            // parse ( "*" or "/" )
            final boolean shouldMult = lexer.getCurrentToken().getType() == TokenType.STAR;
            lexer.fetchNextToken();
            // parse FACTOR
            final Node right = parseFactor();
            // update Node
            if (shouldMult) {
                root = new Multiplication(root, right);
            } else {
                root = new Division(root, right);
            }
        }
        
        return root;
    }
    
    /**
     * Parse a factor.
     * This assumes the lexer already points to the first token of this factor.
     * 
     * <p>EBNF:
     * <code>
     * FACTOR ::=  
     *          Literal | 
     *          Identifier | 
     *          "(" EXPRESSION ")"
     * </code>
     * 
     * @return a Node representing the factor
     */
    private Node parseFactor() {
        final Node root;
        
        // parse Literal |
        //       Identifier |
        //       "(" EXPRESSION ")"
        switch (lexer.getCurrentToken().getType()) {
            case LITERAL:
                root = new Literal(Integer.parseInt(lexer.getCurrentToken().getText()));
                break;
            case IDENTIFIER:
                root = new Variable(lexer.getCurrentToken().getText());
                break;
            case OPEN_PAREN:
                lexer.fetchNextToken();
                root = parseExpression();
                if (lexer.getCurrentToken().getType() != TokenType.CLOSED_PAREN) {
                    System.out.print("Expected " + TokenType.CLOSED_PAREN.getName());
                    System.out.print(", got \"" + lexer.getCurrentToken().getText() + "\"");
                    System.out.println();
                    return null;
                }
                break;
            default:
                System.out.print("Expected " + TokenType.LITERAL.getName());
                System.out.print(", " + TokenType.IDENTIFIER.getName());
                System.out.print(", or " + TokenType.OPEN_PAREN.getName());
                System.out.print(", got \"" + lexer.getCurrentToken().getText() + "\"");
                System.out.println();
                return null;
        }
        lexer.fetchNextToken();
        
        return root;
    }

}
