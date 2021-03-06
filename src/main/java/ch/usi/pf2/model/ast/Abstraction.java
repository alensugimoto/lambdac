package ch.usi.pf2.model.ast;

import ch.usi.pf2.model.interpreter.Context;


/**
 * An abstraction of the untyped lambda calculus.
 * It holds another term that represents the body
 * of this abstraction as well as a string hint 
 * for the name of its bound variable.
 * 
 * @author Alen Sugimoto
 * @version 03.06.2021
 */
public final class Abstraction extends Node {
    
    private final String arg;
    private final Node body;
    
    /**
     * Constructs a new abstraction.
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
    protected final boolean isValue() {
        return true;
    }
    
    /**
     * Applies this abstraction to the specified node.
     * @param right the node to be applied
     * @return the result of applying this abstraction to the specified node
     */
    protected final Node apply(final Node right) {
        return body.substitute(right.shift(1)).shift(-1);
    }
    
    @Override
    protected final Node shift(final int cutoff, final int increment) {
        return new Abstraction(getPosition(), arg, body.shift(cutoff + 1, increment));
    }
    
    @Override
    protected final Node substitute(final int index, final Node node) {
        return new Abstraction(getPosition(), arg, body.substitute(index + 1, node));
    }

    @Override
    public final String toString(final Context context) {
        final Context newContext = new Context(context);
        newContext.addFirstUnique(arg);
        return "(\\" + newContext.getFirst() + "." + body.toString(newContext) + ")";
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
