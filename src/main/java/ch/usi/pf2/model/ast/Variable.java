package ch.usi.pf2.model.ast;

import ch.usi.pf2.model.interpreter.Context;
import ch.usi.pf2.model.interpreter.InvalidContextException;


/**
 * A variable of the untyped lambda calculus.
 * 
 * @author Alen Sugimoto
 * @version 03.06.2021
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
    protected final boolean isValue() {
        return false;
    }
    
    @Override
    protected final Node shift(final int c, final int d) {
        return new Variable(getPosition(), index < c ? index : index + d, contextLength + d);
    }
    
    @Override
    protected final Node substitute(final int c, final Node s) {
        return index == c ? s.shift(c) : this;
    }

    @Override
    public final String toString(final Context context) {
        if (context.size() != contextLength) {
            throw new InvalidContextException();
        }
        return context.get(index);
    }
    
    @Override
    public final boolean equals(final Object obj) {
        if (super.equals(obj) && obj instanceof Variable) {
            final Variable other = (Variable) obj;
            return index == other.index && contextLength == other.contextLength;
        } else {
            return false;
        }
    }
    
    @Override
    public final int hashCode() {
        int result = 17;
        result = 37 * result + super.hashCode();
        result = 37 * result + index;
        result = 37 * result + contextLength;
        return result;
    }
    
}
