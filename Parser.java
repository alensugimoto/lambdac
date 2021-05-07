/**
 * A Parser for the untyped lambda calculus.
 * 
 * <p>The following EBNF was inspired by
 * <a href="https://tadeuzagallo.com/blog/writing-a-lambda-calculus-interpreter-in-javascript/">
 * this blog post</a>.
 * 
 * <pre>
 * TERM        ::= ABSTRACTION | APPLICATION
 * ATOM        ::= Identifier | "(" TERM ")"
 * ABSTRACTION ::= "\\" Identifier "." TERM
 * APPLICATION ::= ATOM { " " ATOM }
 * </pre>
 */
public final class Parser {
    
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
        // now parse the TERM
        final Node root = parseTerm();
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
     * Parse a term.
     * This assumes the lexer already points to the first token of this term.
     * 
     * <p>EBNF:
     * <code>
     * TERM ::= ABSTRACTION | APPLICATION
     * </code>
     * 
     * @return a Node representing the term
     */
    private Node parseTerm() {
        return lexer.getCurrentToken().getType() == TokenType.LAMBDA
            ? parseAbstraction()
            : parseApplication();
    }
    
    /**
     * Parse an application.
     * This assumes the lexer already points to the first token of this application.
     * 
     * <p>EBNF:
     * <code>
     * APPLICATION ::= ATOM { " " ATOM }
     * </code>
     * 
     * @return a Node representing the application
     */
    private Node parseApplication() {
        Node root = parseAtom();
        while (lexer.getCurrentToken().getType() == TokenType.SPACE) {
            lexer.fetchNextToken();
            root = new Application(root, parseAtom());
        }
        return root;
    }
    
    /**
     * Parse an abstraction.
     * This assumes the lexer already points to the first token of this abstraction.
     * 
     * <p>EBNF:
     * <code>
     * ABSTRACTION ::= "\\" Identifier "." TERM
     * </code>
     * 
     * @return a Node representing the abstraction
     */
    private Node parseAbstraction() {
        if (lexer.getCurrentToken().getType() != TokenType.LAMBDA) {
            System.out.print("Expected " + TokenType.LAMBDA.getName());
            System.out.print(", got \"" + lexer.getCurrentToken().getText() + "\"");
            System.out.println();
            return null;
        }
        lexer.fetchNextToken();
        if (lexer.getCurrentToken().getType() != TokenType.IDENTIFIER) {
            System.out.print("Expected " + TokenType.IDENTIFIER.getName());
            System.out.print(", got \"" + lexer.getCurrentToken().getText() + "\"");
            System.out.println();
            return null;
        }
        Variable var = new Variable(lexer.getCurrentToken().getText());;
        lexer.fetchNextToken();
        if (lexer.getCurrentToken().getType() != TokenType.DOT) {
            System.out.print("Expected " + TokenType.DOT.getName());
            System.out.print(", got \"" + lexer.getCurrentToken().getText() + "\"");
            System.out.println();
            return null;
        }
        lexer.fetchNextToken();
        return new Abstraction(var, parseTerm());
    }
    
    /**
     * Parse an atom.
     * This assumes the lexer already points to the first token of this atom.
     * 
     * <p>EBNF:
     * <code>
     * ATOM ::= Identifier | "(" TERM ")"
     * </code>
     * 
     * @return a Node representing the atom
     */
    private Node parseAtom() {
        final Node root;
        
        switch (lexer.getCurrentToken().getType()) {
            case IDENTIFIER:
                root = new Variable(lexer.getCurrentToken().getText());
                break;
            case OPEN_PAREN:
                lexer.fetchNextToken();
                root = parseTerm();
                if (lexer.getCurrentToken().getType() != TokenType.CLOSED_PAREN) {
                    System.out.print("Expected " + TokenType.CLOSED_PAREN.getName());
                    System.out.print(", got \"" + lexer.getCurrentToken().getText() + "\"");
                    System.out.println();
                    return null;
                }
                break;
            default:
                System.out.print("Expected " + TokenType.IDENTIFIER.getName());
                System.out.print(" or " + TokenType.OPEN_PAREN.getName());
                System.out.print(", got \"" + lexer.getCurrentToken().getText() + "\"");
                System.out.println();
                return null;
        }
        lexer.fetchNextToken();
        
        return root;
    }

}
