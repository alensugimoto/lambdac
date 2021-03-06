package ch.usi.pf2.model.ast;

import ch.usi.pf2.model.interpreter.Context;


/**
 * An application of the untyped lambda calculus.
 * It holds two subterms with the former being applied to the latter.
 * 
 * @author Alen Sugimoto
 * @version 03.06.2021
 */
public final class Application extends Node {
    
    private final Node leftTerm;
    private final Node rightTerm;
    
    /**
     * Constructs a new application.
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
    protected final boolean isValue() {
        return false;
    }
    
    /**
     * Evaluates this application using call-by-value.
     * @return the evaluated node using call-by-value
     */
    @Override
    public final Node evaluate() {
        final Node newLeftTerm = leftTerm.evaluate();
        if (!newLeftTerm.isValue()) {
            return new Application(getPosition(), newLeftTerm, rightTerm);
        } else {
            final Node newRightTerm = rightTerm.evaluate();
            if (!newRightTerm.isValue()) {
                return new Application(getPosition(), newLeftTerm, newRightTerm);
            } else {
                return ((Abstraction)newLeftTerm).apply(newRightTerm).evaluate();
            }
        }
    }
    
    @Override
    protected final Node shift(final int cutoff, final int increment) {
        return new Application(getPosition(),
                               leftTerm.shift(cutoff, increment),
                               rightTerm.shift(cutoff, increment));
    }
    
    @Override
    protected final Node substitute(final int index, final Node node) {
        return new Application(getPosition(),
                               leftTerm.substitute(index, node),
                               rightTerm.substitute(index, node));
    }

    @Override
    public final String toString(final Context context) {
        return "(" + leftTerm.toString(context) + " " + rightTerm.toString(context) + ")";
    }
    
    @Override
    public final boolean equals(final Object obj) {
        if (super.equals(obj) && obj instanceof Application) {
            final Application other = (Application) obj;
            return leftTerm.equals(other.leftTerm) && rightTerm.equals(other.rightTerm);
        } else {
            return false;
        }
    }
    
    @Override
    public final int hashCode() {
        int result = 17;
        result = 37 * result + super.hashCode();
        result = 37 * result + leftTerm.hashCode();
        result = 37 * result + rightTerm.hashCode();
        return result;
    }
    
}
