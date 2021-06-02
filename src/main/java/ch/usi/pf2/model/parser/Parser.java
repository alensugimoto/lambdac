package ch.usi.pf2.model.parser;

import ch.usi.pf2.model.ast.Abstraction;
import ch.usi.pf2.model.ast.Application;
import ch.usi.pf2.model.ast.Node;
import ch.usi.pf2.model.ast.Variable;
import ch.usi.pf2.model.interpreter.Context;
import ch.usi.pf2.model.lexer.LexicalAnalyzer;
import ch.usi.pf2.model.lexer.TokenType;


/**
 * A Parser for the untyped lambda calculus.
 * It takes a string and produces an AST node corresponding
 * to it in the lambda calculus.
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
 * 
 * @author Alen Sugimoto
 * @version 03.06.2021
 */
public final class Parser {
    
    private final LexicalAnalyzer lexer;
    
    /**
     * Constructs a new Parser.
     */
    public Parser() {
        lexer = new LexicalAnalyzer();
    }
    
    /**
     * Parse a program in lambda calculus.
     * 
     * @param sourceCode The source code of the program in lambda calculus
     * @return an AST of the program
     * @throws ParseException if the source code contains a syntax error
     *     or an undefined variable
     */
    public final Node parse(final String sourceCode) throws ParseException {
        return parse(sourceCode, new Context());
    }
    
    /**
     * Parse a program in lambda calculus with the specified context.
     * 
     * @param sourceCode The source code of the program in lambda calculus
     * @param context The context of the program
     * @return an AST of the program
     * @throws ParseException if the source code contains a syntax error
     *     or an undefined variable.
     */
    public final Node parse(final String sourceCode, final Context context) throws ParseException {
        lexer.setText(sourceCode);
        // fetch first token
        lexer.fetchNextToken();
        // now parse the TERM
        final Node root = parseTerm(context);
        // check if lexer is at the end of file
        expect(TokenType.END_OF_FILE);
        return root;
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
    private final Node parseTerm(final Context context) throws ParseException {
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
    private final Node parseApplication(final Context context) throws ParseException {
        final int position = lexer.getCurrentToken().getStartPosition();
        Node root = parseAtom(new Context(context));
        while (lexer.getCurrentToken().getType() == TokenType.IDENTIFIER
            || lexer.getCurrentToken().getType() == TokenType.OPEN_PAREN) {
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
    private final Node parseAbstraction(final Context context) throws ParseException {
        final int position = lexer.getCurrentToken().getStartPosition();
        
        expect(TokenType.LAMBDA);
        lexer.fetchNextToken();
        
        expect(TokenType.IDENTIFIER);
        final String arg = lexer.getCurrentToken().getText();
        lexer.fetchNextToken();
        
        expect(TokenType.DOT);
        lexer.fetchNextToken();
        
        final Context newContext = new Context(context);
        newContext.addFirst(arg);
        return new Abstraction(position, arg, parseTerm(newContext));
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
     * @throws ParseException if the atom is an undefined variable
     */
    private final Node parseAtom(final Context context) throws ParseException {
        final Node root;
        
        expect(TokenType.IDENTIFIER, TokenType.OPEN_PAREN);
        if (lexer.getCurrentToken().getType() == TokenType.IDENTIFIER) {
            if (context.contains(lexer.getCurrentToken().getText())) {
                root = new Variable(
                    lexer.getCurrentToken().getStartPosition(),
                    context.indexOf(lexer.getCurrentToken().getText()),
                    context.size());
            } else {
                throw new ParseException("Variable '" + lexer.getCurrentToken().getText()
                                         + "' is not defined");
            }
        } else {
            lexer.fetchNextToken();
            root = parseTerm(context);
            expect(TokenType.CLOSED_PAREN);
        }
        lexer.fetchNextToken();
        
        return root;
    }
    
    private final void expect(final TokenType expectedType, final TokenType... expectedTypes)
                              throws ParseException {
        if (lexer.getCurrentToken().getType() != expectedType) {
            for (final TokenType type : expectedTypes) {
                if (lexer.getCurrentToken().getType() == type) {
                    return;
                }
            }
            throw new ParseException(lexer.getCurrentToken(), expectedType, expectedTypes);
        }
    }

}
