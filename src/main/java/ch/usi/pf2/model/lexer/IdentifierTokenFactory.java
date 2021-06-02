package ch.usi.pf2.model.lexer;


/**
 * A factory for tokens of type identifier.
 * 
 * @author Alen Sugimoto
 * @version 03.06.2021
 */
public final class IdentifierTokenFactory extends RegExTokenFactory {

    /**
     * Constructs an IdentifierTokenFactory.
     */
    public IdentifierTokenFactory() {
        super("[a-zA-Z_][a-zA-Z_0-9]*");
    }
    
    @Override
    public final Token getToken() {
        return new Token(TokenType.IDENTIFIER, getTokenText(), getTokenStartPosition());
    }
    
}
