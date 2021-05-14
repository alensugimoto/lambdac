package ast;

import context.Context;

/**
 * A variable.
 */
public class Variable extends Node {
    
    private final int index;
    private final int contextLength;

    /**
     * Create a new Variable node.
     * @param name the name of this node
     */
    public Variable(final int position, final int index, final int contextLength) {
        super(position);
        this.index = index;
        this.contextLength = contextLength;
    }
    
    public Node map(final TriFunction<Integer, Integer, Integer, Node> onvar, final int c) {
        return onvar.apply(c, index, contextLength);
    }

    @Override
    public String toString(final Context context) {
        return context.size() == contextLength ? context.get(index) : "[bad index]";
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (super.equals(obj) && obj instanceof Variable) {
            Variable other = (Variable) obj;
            return index == other.index && contextLength == other.contextLength;
        } else {
            return false;
        }
    }
    
}
