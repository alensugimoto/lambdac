package ch.usi.pf2.model.lexer;


/**
 * A special kind of StringTokenFactory,
 * which produces tokens that represent operators.
 * 
 * @author Alen Sugimoto
 * @version 03.06.2021
 */
public final class OperatorTokenFactory extends StringTokenFactory {

    private final TokenType tokenType;
    
    /**
     * Constructs an OperatorTokenFactory for tokens representing the given operator.
     * @param operator the operator
     * @param tokenType the TokenType corresponding to this operator
     */
    public OperatorTokenFactory(final String operator, final TokenType tokenType) {
        super(operator);
        this.tokenType = tokenType;
    }

    @Override
    public final Token getToken() {
        return new Token(tokenType, getTokenText(), getTokenStartPosition());
    }

}
