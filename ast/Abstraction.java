package ast;

import context.Context;

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
    
    public Node map(final TriFunction<Integer, Integer, Integer, Node> onvar, final int c) {
        return new Abstraction(getPosition(), arg, body.map(onvar, c + 1));
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
