package ch.usi.pf2.model.ast;

import ch.usi.pf2.model.interpreter.Context;


/**
 * A variable of the untyped lambda calculus.
 */
public final class Variable extends Node {
    
    private final int index;
    private final int contextLength;

    /**
     * Constructs a new variable.
     * 
     * @param position the position where this variable was originally found
     * @param index the de Bruijn index of this variable
     * @param contextLength the length of the context in which this variable occurs
     */
    public Variable(final int position, final int index, final int contextLength) {
        super(position);
        this.index = index;
        this.contextLength = contextLength;
    }
    
    @Override
    protected boolean isValue() {
        return false;
    }
    
    @Override
    protected Node evaluateOne() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected Node shift(final int c, final int d) {
        return new Variable(getPosition(), index < c ? index : index + d, contextLength + d);
    }
    
    @Override
    protected Node substitute(final int c, final Node s) {
        return index == c ? s.shift(c) : this;
    }

    @Override
    public String toString(final Context context) {
        // assert context.size() == contextLength : "bad index";
        return context.get(index);
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (super.equals(obj) && obj instanceof Variable) {
            final Variable other = (Variable) obj;
            return index == other.index && contextLength == other.contextLength;
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + super.hashCode();
        result = 37 * result + index;
        result = 37 * result + contextLength;
        return result;
    }
    
}