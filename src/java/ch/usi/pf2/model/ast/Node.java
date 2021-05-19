package ch.usi.pf2.model.ast;

import ch.usi.pf2.model.context.Context;


/**
 * An abstract syntax tree (AST) node for lambda terms.
 */
public abstract class Node {
    
    private final int position;
    
    /**
     * Sole constructor.
     * 
     * @param position the position where this node was originally found
     */
    protected Node(final int position) {
        this.position = position;
    }
    
    /**
     * Returns the position where this node was originally found.
     * 
     * @return the position where this node was originally found
     */
    protected int getPosition() {
        return position;
    }
    
    /**
     * Evaluates this node.
     * 
     * @return the node obtained by evaluating this node
     */
    public Node evaluate() {
        try {
            return evaluateOne().evaluate();
        } catch (UnsupportedOperationException exception) {
            return this;
        }
    }
    
    /**
     * Increments the index of every free variable in this node by {@code d}
     * assuming that their indices are greater than or equal to zero.
     * 
     * @param d the number to be added to the index of every free variable in this node
     */
    protected final Node shift(final int d) {
        return shift(0, d);
    }
    
    /**
     * Substitutes node {@code s} for a variable with index {@code j} in this node
     * assuming that the index of every free variable is greater than or equal to zero.
     * 
     * @param j the index of the variable in this node to be substituted
     * @param s the node to substitute
     */
    protected final Node substitute(final int j, final Node s) {
        return substitute(0, j, s);
    }
    
    /**
     * Substitutes the specified node for the bound variable in this node.
     * 
     * @param s the node to substitute
     */
    protected final Node substituteTop(final Node s) {
        return substitute(0, s.shift(1)).shift(-1);
    }
    
    /**
     * Determines whether this node is a value.
     * 
     * @return true if this node is a value and false otherwise
     */
    abstract public boolean isValue();
    
    /**
     * Evaluates this node by one step.
     * 
     * @return the evaluated node
     * @throws UnsupportedOperationException if the <tt>evaluateOne</tt> operation
     *         is not supported by this node
     */
    abstract public Node evaluateOne();
    
    /**
     * Applies this node to the specified node.
     * 
     * @return the node to be applied
     * @throws UnsupportedOperationException if the <tt>apply</tt> operation
     *         is not supported by this node
     */
    abstract public Node apply(final Node right);
    
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
     * @return a string representation of this node
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
