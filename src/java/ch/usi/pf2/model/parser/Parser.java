package ch.usi.pf2.model.parser;

import ch.usi.pf2.model.ast.Abstraction;
import ch.usi.pf2.model.ast.Application;
import ch.usi.pf2.model.ast.Node;
import ch.usi.pf2.model.ast.Variable;
import ch.usi.pf2.model.context.Context;
import ch.usi.pf2.model.lexer.LexicalAnalyzer;
import ch.usi.pf2.model.lexer.TokenType;


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
 * APPLICATION ::= ATOM { ATOM }
 * </pre>
 */
public final class Parser {
    
    private LexicalAnalyzer lexer;

    
    /**
     * Parse a program in lambda calculus.
     * @param sourceCode The source code of the program in lambda calculus
     * @return an AST of the program
     */
    public Node parse(final String sourceCode) {
        return parse(sourceCode, new Context());
    }
    
    /**
     * Parse a program in lambda calculus with the specified context.
     * @param sourceCode The source code of the program in lambda calculus
     * @param context The context of the program
     * @return an AST of the program
     */
    public Node parse(final String sourceCode, final Context context) {
        lexer = new LexicalAnalyzer(sourceCode);
        // fetch first token
        lexer.fetchNextToken();
        // now parse the TERM
        final Node root = parseTerm(context);
        // check if lexer is at the end of file
        if (lexer.getCurrentToken().getType() == TokenType.END_OF_FILE) {
            return root;
        } else {
            throw new IllegalArgumentException(errorMessage(TokenType.END_OF_FILE));
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
    private Node parseTerm(final Context context) {
        return lexer.getCurrentToken().getType() == TokenType.LAMBDA
            ? parseAbstraction(context)
            : parseApplication(context);
    }
    
    /**
     * Parse an application.
     * This assumes the lexer already points to the first token of this application.
     * 
     * <p>EBNF:
     * <code>
     * APPLICATION ::= ATOM { ATOM }
     * </code>
     * 
     * @return a Node representing the application
     */
    private Node parseApplication(final Context context) {
        int nextPosition = lexer.getCurrentToken().getStartPosition();
        Node root = parseAtom(new Context(context));
        while (lexer.getCurrentToken().getType() == TokenType.IDENTIFIER
            || lexer.getCurrentToken().getType() == TokenType.OPEN_PAREN) {
            final int position = nextPosition;
            nextPosition = lexer.getCurrentToken().getStartPosition();
            root = new Application(position, root, parseAtom(new Context(context)));
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
    private Node parseAbstraction(final Context context) {
        final int position = lexer.getCurrentToken().getStartPosition();
        if (lexer.getCurrentToken().getType() != TokenType.LAMBDA) {
            throw new IllegalArgumentException(errorMessage(TokenType.LAMBDA));
        }
        lexer.fetchNextToken();
        if (lexer.getCurrentToken().getType() != TokenType.IDENTIFIER) {
            throw new IllegalArgumentException(errorMessage(TokenType.IDENTIFIER));
        }
        final String arg = lexer.getCurrentToken().getText();
        context.addFirst(arg);
        lexer.fetchNextToken();
        if (lexer.getCurrentToken().getType() != TokenType.DOT) {
            throw new IllegalArgumentException(errorMessage(TokenType.DOT));
        }
        lexer.fetchNextToken();
        return new Abstraction(position, arg, parseTerm(context));
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
    private Node parseAtom(final Context context) {
        final Node root;
        
        switch (lexer.getCurrentToken().getType()) {
            case IDENTIFIER:
                root = new Variable(
                    lexer.getCurrentToken().getStartPosition(),
                    context.indexOf(lexer.getCurrentToken().getText()),
                    context.size());
                break;
            case OPEN_PAREN:
                lexer.fetchNextToken();
                root = parseTerm(context);
                if (lexer.getCurrentToken().getType() != TokenType.CLOSED_PAREN) {
                    throw new IllegalArgumentException(errorMessage(TokenType.CLOSED_PAREN));
                }
                break;
            default:
                throw new IllegalArgumentException(
                    errorMessage(TokenType.IDENTIFIER) + " or "
                    + errorMessage(TokenType.OPEN_PAREN));
        }
        lexer.fetchNextToken();
        
        return root;
    }
    
    private String errorMessage(final TokenType expected) {
        return "Expected " + expected.getName()
            + ", got \"" + lexer.getCurrentToken().getText() + "\"";
    }

}
