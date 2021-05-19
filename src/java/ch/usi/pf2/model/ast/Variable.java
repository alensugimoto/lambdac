package ch.usi.pf2.model.ast;

import ch.usi.pf2.model.context.Context;


/**
 * A variable of the untyped lambda calculus.
 * It holds its de Bruijn index and the length of the context that it occurs in
 */
public class Variable extends Node {
    
    private final int index;
    private final int contextLength;

    /**
     * Constructs a new Variable node.
     * 
     * @param position the starting position of this variable
     * @param index the de Bruijn index of this variable
     * @param contextLength the length of the context in which this variable occurs
     */
    public Variable(final int position, final int index, final int contextLength) {
        super(position);
        this.index = index;
        this.contextLength = contextLength;
    }
    
    @Override
    public Node evaluateCallByValue() {
        return this;
    }
    
    @Override
    public Node evaluateCallByName() {
        return this;
    }
    
    @Override
    public Node evaluateApplicativeOrder() {
        return this;
    }
    
    @Override
    public Node evaluateNormalOrder() {
        return this;
    }
    
    @Override
    public Node termShift(final int c, final int d) {
        return new Variable(getPosition(), index < c ? index : index + d, contextLength + d);
    }
    
    @Override
    public Node termSubst(final int c, final int j, final Node s) {
        return index == j + c ? s.termShift(c) : this;
    }

    @Override
    public String toString(final Context context) {
        return context.size() == contextLength ? context.get(index) : "[bad index]";
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
        return -1;
    }
    
}
