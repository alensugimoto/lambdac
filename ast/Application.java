package ast;

import java.util.List;
import java.util.LinkedList;

/**
 * An application.
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
    public Node evaluate() {
        return leftTerm.evaluate().apply(rightTerm.evaluate());
    }
    
    @Override
    public Node termShift(final int c, final int d) {
        return new Application(
            getPosition(),
            leftTerm.termShift(c, d),
            rightTerm.termShift(c, d));
    }
    
    @Override
    public Node termSubst(final int j, final int c, final Node s) {
        return new Application(
            getPosition(),
            leftTerm.termSubst(j, c, s),
            rightTerm.termSubst(j, c, s));
    }

    @Override
    public String toString(final List<String> context) {
        return "(" + leftTerm.toString(new LinkedList(context))
            + " " + rightTerm.toString(new LinkedList(context)) + ")";
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (super.equals(obj) && obj instanceof Application) {
            Application other = (Application) obj;
            return leftTerm.equals(other.leftTerm) && rightTerm.equals(other.rightTerm);
        } else {
            return false;
        }
    }
    
}
