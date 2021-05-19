package ch.usi.pf2.model.ast;

import ch.usi.pf2.model.context.Context;


/**
 * An abstraction of the untyped lambda calculus.
 * It holds a hint for the name of its bound variable 
 * and another node as its body.
 */
public class Abstraction extends Node {
    
    private final String arg;
    private final Node body;
    
    /**
     * Create a new Abstraction node.
     * @param leftChild the left operand
     * @param rightChild the right operand
     */
    public Abstraction(final int position, final String arg, final Node body) {
        super(position);
        this.arg = arg;
        this.body = body;
    }
    
    @Override
    public Node evaluateCallByValue() {
        return this;
    }
    
    @Override
    public Node evaluateCallByName() {
        return evaluateCallByValue();
    }
    
    @Override
    public Node evaluateApplicativeOrder() {
        return new Abstraction(arg, body.evaluateApplicativeOrder());
    }
    
    @Override
    public Node evaluateNormalOrder() {
        return evaluateApplicativeOrder();
    }
    
    @Override
    public Node apply(final Node right) {
        return body.substituteTop(right);
    }
    
    /**
     * Substitutes the specified node for the bound variable in this abstraction's body.
     * 
     * @param s the node to substitute
     */
    private Node substituteTop(final Node s) {
        return substitute(0, s.shift(1)).shift(-1);
    }
    
    @Override
    public Node termShift(final int c, final int d) {
        return new Abstraction(getPosition(), arg, body.termShift(c + 1, d));
    }
    
    @Override
    public Node termSubst(final int c, final int j, final Node s) {
        return new Abstraction(getPosition(), arg, body.termSubst(j, c + 1, s));
    }

    @Override
    public String toString(final Context context) {
        return "(\\" + pickFreshName(context) + "." + body.toString(context) + ")";
    }
    
    /**
     * Generates a fresh name for its bound variable
     * based on its hint and the specified context, and
     * updates the specified context with the generated name.
     * 
     * @param context the current context
     */
    private String pickFreshName(final Context context) {
        String freshName = arg;
        while (context.contains(freshName)) {
            freshName += "\u2032";
        }
        context.addFirst(freshName);
        return freshName;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (super.equals(obj) && obj instanceof Abstraction) {
            final Abstraction other = (Abstraction) obj;
            return arg.equals(other.arg) && body.equals(other.body);
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        return -1;
    }    
}
