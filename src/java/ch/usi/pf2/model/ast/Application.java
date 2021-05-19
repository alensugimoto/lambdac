package ch.usi.pf2.model.ast;

import ch.usi.pf2.model.context.Context;


/**
 * An application of the untyped lambda calculus.
 */
public class Application extends Node {
    
    private final Node leftTerm;
    private final Node rightTerm;
    
    /**
     * Create a new Application node.
     * @param leftChild the left operand
     * @param rightChild the right operand
     */
    public Application(final int position, final Node leftTerm, final Node rightTerm) {
        super(position);
        this.leftTerm = leftTerm;
        this.rightTerm = rightTerm;
    }
    
    
    @Override
    public Node evaluateCallByValue() {
        // TODO: use big-step evaluation
        if (leftTerm.isValue() && rightTerm.isValue())
        return leftTerm.evaluateCallByValue().apply(rightTerm.evaluateCallByValue());
    }
    
    @Override
    public Node evaluateCallByName() {
        return leftTerm.evaluateCallByName().apply(rightTerm).evaluateCallByName();
    }
    
    @Override
    public Node evaluateApplicativeOrder() {
        return evaluateCallByValue();
    }
    
    @Override
    public Node evaluateNormalOrder() {
        return evaluateCallByName();
    }
    
    @Override
    public Node shift(final int c, final int d) {
        return new Application(
            getPosition(),
            leftTerm.shift(c, d),
            rightTerm.shift(c, d));
    }
    
    @Override
    public Node substitute(final int c, final int j, final Node s) {
        return new Application(
            getPosition(),
            leftTerm.substitute(j, c, s),
            rightTerm.substitute(j, c, s));
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
        return -1;
    }    
}
