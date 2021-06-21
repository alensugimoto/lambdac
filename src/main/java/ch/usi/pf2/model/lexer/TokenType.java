package ch.usi.pf2.model.lexer;


/**
 * A program in a programming language is made up 
 * of different kinds of tokens.
 * This enumeration represents these different kinds
 * in the untyped lambda calculus.
 * 
 * @author Alen Sugimoto
 * @version 03.06.2021
 */
public enum TokenType {

    IDENTIFIER("identifier"),
    LAMBDA("lambda"),
    DOT("dot"),
    OPEN_PAREN("open parenthesis"),
    CLOSED_PAREN("closed parenthesis"),
    END_OF_FILE("end of file");

    private final String name;
    
    /**
     * Constructs a new TokenType with the specified name.
     * @param name the human-readable name of this TokenType
     */
    private TokenType(final String name) {
        this.name = name;
    }

    /**
     * Returns the human-readable name of this TokenType.
     * @return the name of this TokenType
     */
    public final String getName() {
        return name;
    }

}
