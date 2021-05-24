package ch.usi.pf2.model.lexer;


/**
 * A factory for tokens of type identifier.
 */
public final class IdentifierTokenFactory extends RegExTokenFactory {

    /**
     * Create an IdentifierTokenFactory.
     */
    public IdentifierTokenFactory() {
        super("[a-zA-Z_][a-zA-Z_0-9]*");
    }
    
    @Override
    public Token getToken() {
        return new Token(TokenType.IDENTIFIER, getTokenText(), getTokenStartPosition());
    }
    
}
