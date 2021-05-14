package ast;

import context.Context;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * An abstract syntax tree (AST) node.
 */
public abstract class Node {
    
    private final int position;
    
    public Node(final int position) {
        this.position = position;
    }
    
    protected int getPosition() {
        return position;
    }
    
    /**
     * Evaluate this node.
     * @return an evaluated node
     */
    public Node evaluate() {
        return this;
    }
    
    public Node apply(Node right) {
        return new Application(position, this, right);
    }
    
    @FunctionalInterface
    public interface TriFunction<T, U, V, W> {
        public W apply(T t, U u, V v);
    }
    
    public final Node termShift(final int d) {
        return map(
            (c, index, contextLength) -> new Variable(
                getPosition(),
                index < c ? index : index + d,
                contextLength + d),
            0);
    }
    
    public final Node termSubst(final int j, final Node s) {
        return map(
            (c, index, contextLength) -> index == j + c 
                ? s.termShift(c)
                : this,
            0);
    }
    
    public final Node termSubstTop(final Node s) {
        return termSubst(0, s.termShift(1)).termShift(-1);
    }
    
    abstract public Node map(final TriFunction<Integer, Integer, Integer, Node> onvar, final int c);
    
    /**
     * Decompile this node into a string.
     * Note that the resulting string may have
     * extra parentheses.
     * @return a String representation of this AST
     */
    abstract public String toString(final Context context);
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Node)) {
            return false;
        }
        Node other = (Node) obj;
        return position == other.position;
    }
    
}
