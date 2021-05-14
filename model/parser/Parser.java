package model.parser;

import model.ast.*;
import model.context.Context;
import model.lexer.LexicalAnalyzer;
import model.lexer.TokenType;


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
     * Parse a program in the Arith language.
     * @param sourceCode The source code of the program in the Arith language
     * @return an AST of the program
     */
    public Node parse(final String sourceCode) {
        return parse(sourceCode, new Context());
    }
    
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
        final String arg = lexer.getCurrentToken().getText();
        context.add(0, arg);
        lexer.fetchNextToken();
        if (lexer.getCurrentToken().getType() != TokenType.DOT) {
            System.out.print("Expected " + TokenType.DOT.getName());
            System.out.print(", got \"" + lexer.getCurrentToken().getText() + "\"");
            System.out.println();
            return null;
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
