package model.ast;

import model.context.Context;

/**
 * An abstraction.
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
    public Node apply(Node right) {
        return body.termSubstTop(right);
    }
    
    @Override
    public Node termShift(final int c, final int d) {
        return new Abstraction(getPosition(), arg, body.termShift(c + 1, d));
    }
    
    @Override
    public Node termSubst(final int j, final int c, final Node s) {
        return new Abstraction(getPosition(), arg, body.termSubst(j, c + 1, s));
    }

    @Override
    public String toString(final Context context) {
        return "(\\" + pickFreshName(context) + "." + body.toString(context) + ")";
    }
    
    private String pickFreshName(final Context context) {
        String freshName = arg;
        while (context.contains(freshName)) {
            freshName += "\u2032";
        }
        context.add(0, freshName);
        return freshName;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (super.equals(obj) && obj instanceof Abstraction) {
            Abstraction other = (Abstraction) obj;
            return arg.equals(other.arg) && body.equals(other.body);
        } else {
            return false;
        }
    }
    
}
