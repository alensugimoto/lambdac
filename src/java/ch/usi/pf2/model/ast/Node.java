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
     * Determines whether this node is a value.
     * 
     * @return true if this node is a value and false otherwise
     */
    public abstract boolean isValue();
    
    /**
     * Evaluates this node.
     * 
     * @return the node obtained by evaluating this node
     */
    public Node evaluate() {
        Node evaluatedNode = this;
        try {
            while (true) {
                evaluatedNode = evaluatedNode.evaluateOne();
            }
        } catch (UnsupportedOperationException exception) {
            return evaluatedNode;
        }
    }
    
    /**
     * Evaluates this node by one step.
     * 
     * @return the evaluated node
     * @throws UnsupportedOperationException if the <tt>evaluateOne</tt> operation
     *         is not supported by this node
     */
    public abstract Node evaluateOne();
    
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
     * Increments the index of every free variable in this node by {@code d}
     * assuming that their indices are greater than or equal to {@code c}.
     * 
     * @param c the lower bound for the index of every free variable in this node
     * @param d the number to be added to the index of every free variable in this node
     * @return the node after incrementing the index of every free variable in this node
     *         by {@code d} assuming that their indices are greater than or equal to {@code c}.
     */
    public abstract Node shift(final int c, final int d);
    
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
     * Substitutes node {@code s} for a variable with index {@code j} in this node
     * assuming that the index of every free variable is greater than or equal to {@code c}.
     * 
     * @param c the lower bound for the index of every free variable in this node
     * @param j the index of the variable in this node to be substituted
     * @param s the node to substitute
     * @return the node after substituting node {@code s} for a variable
     *         with index {@code j} in this node assuming that the index of
     *         every free variable is greater than or equal to {@code c}.
     */
    public abstract Node substitute(final int c, final int j, final Node s);
    
    /**
     * Substitutes the specified node for the bound variable in this node.
     * 
     * @param s the node to substitute
     */
    protected final Node substituteTop(final Node s) {
        return substitute(0, s.shift(1)).shift(-1);
    }
    
    /**
     * Decompiles this node into a string based on the specified context.
     * Note that the resulting string may have extra parentheses.
     * 
     * @param context the current context
     * @return a string representation of this node
     */
    public abstract String toString(final Context context);
    
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
        int result = 17;
        result = 37 * result + position;
        return result;
    }
    
}
