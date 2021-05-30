package ch.usi.pf2.model.ast;

import ch.usi.pf2.model.interpreter.Context;


/**
 * An abstraction of the untyped lambda calculus.
 */
public final class Abstraction extends Node {
    
    private final String arg;
    private final Node body;
    
    /**
     * Constructs a new abstraction.
     * 
     * @param position the position where this abstraction was originally found
     * @param arg the string hint for the name of this abstraction's bound variable
     * @param body the body of this abstraction
     */
    public Abstraction(final int position, final String arg, final Node body) {
        super(position);
        this.arg = arg;
        this.body = body;
    }
    
    @Override
    protected boolean isValue() {
        return true;
    }
    
    /**
     * Applies this abstraction to the specified node.
     * 
     * @param right the node to be applied
     * @return the result of applying this abstraction to the specified node
     */
    protected Node apply(final Node right) {
        return body.substitute(right.shift(1)).shift(-1);
    }
    
    @Override
    protected Node evaluateOne() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected Node shift(final int c, final int d) {
        return new Abstraction(getPosition(), arg, body.shift(c + 1, d));
    }
    
    @Override
    protected Node substitute(final int c, final Node s) {
        return new Abstraction(getPosition(), arg, body.substitute(c + 1, s));
    }

    @Override
    public String toString(final Context context) {
        final Context newContext = new Context(context);
        return "(\\" + pickFreshName(newContext) + "." + body.toString(newContext) + ")";
    }
    
    /**
     * Generates a fresh name for its bound variable
     * based on its string hint and the specified context, and
     * updates the specified context with the generated name.
     * 
     * @param context the current context
     */
    private String pickFreshName(final Context context) {
        String freshName = arg;
        while (context.contains(freshName)) {
            freshName += "'";
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
        int result = 17;
        result = 37 * result + super.hashCode();
        result = 37 * result + arg.hashCode();
        result = 37 * result + body.hashCode();
        return result;
    }
    
}