package ch.usi.pf2.model.ast;

import ch.usi.pf2.model.context.Context;


/**
 * An abstract syntax tree (AST) node for lambda terms.
 * Every node holds the position where they were originally found.
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
    abstract public Node evaluate();
    
    public Node apply(final Node right) {
        return new Application(position, this, right);
    }
    
    /**
     * Increments the index of every free variable in this node by {@code d}
     * assuming that their indices are greater than or equal to zero.
     * 
     * @param d the number to be added to the index of every free variable in this node
     */
    public final Node shift(final int d) {
        return shift(0, d);
    }
    
    /**
     * Substitutes node {@code s} for a variable with index {@code j} in this node
     * assuming that the index of every free variable is greater than or equal to zero.
     * 
     * @param j the index of the variable in this node to be substituted
     * @param s the node to substitute
     */
    public final Node substitute(final int j, final Node s) {
        return substitute(0, j, s);
    }
    
    /**
     * Increments the index of every free variable in this node by {@code d}
     * assuming that their indices are greater than or equal to {@code c}.
     * 
     * @param c the lower bound for the index of every free variable in this node
     * @param d the number to be added to the index of every free variable in this node
     */
    abstract public Node shift(final int c, final int d);
    
    /**
     * Substitutes node {@code s} for a variable with index {@code j} in this node
     * assuming that the index of every free variable is greater than or equal to {@code c}.
     * 
     * @param c the lower bound for the index of every free variable in this node
     * @param j the index of the variable in this node to be substituted
     * @param s the node to substitute
     */
    abstract public Node substitute(final int c, final int j, final Node s);
    
    /**
     * Decompiles this node into a string.
     * Note that the resulting string may have extra parentheses.
     * 
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
        final Node other = (Node) obj;
        return position == other.position;
    }
    
    @Override
    public int hashCode() {
        return -1;
    }    
}
