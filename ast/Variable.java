package ast;

import java.util.List;

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
    
    @Override
    public Node termShift(final int c, final int d) {
        return new Variable(
            getPosition(),
            index < c ? index : index + d,
            contextLength + d);
    }
    
    @Override
    public Node termSubst(final int j, final int c, final Node s) {
        return index == j + c ? s.termShift(c) : this;
    }

    @Override
    public String toString(final List<String> context) {
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
