package ch.usi.pf2.model.ast;

import ch.usi.pf2.model.context.Context;


/**
 * An application of the untyped lambda calculus.
 * It holds two subterms being applied.
 */
public final class Application extends Node {
    
    private final Node leftTerm;
    private final Node rightTerm;
    
    /**
     * Constructs a new application.
     * 
     * @param position the position where this application was originally found
     * @param leftTerm the left term being applied
     * @param rightTerm the right term being applied
     */
    public Application(final int position, final Node leftTerm, final Node rightTerm) {
        super(position);
        this.leftTerm = leftTerm;
        this.rightTerm = rightTerm;
    }
    
    @Override
    public boolean isValue() {
        return false;
    }
    
    @Override
    public Node evaluateOne() {
        if (leftTerm.isValue() && rightTerm.isValue()) {
            return ((Abstraction) leftTerm).apply(rightTerm);
        } else if (leftTerm.isValue()) {
            return new Application(getPosition(), leftTerm, rightTerm.evaluateOne());
        } else {
            return new Application(getPosition(), leftTerm.evaluateOne(), rightTerm);
        }
    }
    
    @Override
    protected Node shift(final int c, final int d) {
        return new Application(getPosition(), leftTerm.shift(c, d), rightTerm.shift(c, d));
    }
    
    @Override
    protected Node substitute(final int c, final Node s) {
        return new Application(getPosition(), leftTerm.substitute(c, s), rightTerm.substitute(c, s));
    }

    @Override
    public String toString(final Context context) {
        return "(" + leftTerm.toString(new Context(context))
            + " " + rightTerm.toString(new Context(context)) + ")";
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (super.equals(obj) && obj instanceof Application) {
            final Application other = (Application) obj;
            return leftTerm.equals(other.leftTerm) && rightTerm.equals(other.rightTerm);
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + super.hashCode();
        result = 37 * result + leftTerm.hashCode();
        result = 37 * result + rightTerm.hashCode();
        return result;
    }
    
}
