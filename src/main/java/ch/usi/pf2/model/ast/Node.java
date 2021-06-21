package ch.usi.pf2.model.ast;

import ch.usi.pf2.model.interpreter.Context;


/**
 * An abstract syntax tree (AST) node for lambda terms.
 * Every term holds an integer representing
 * the position where this node was originally found.
 * 
 * @author Alen Sugimoto
 * @version 03.06.2021
 */
public abstract class Node {
    
    private final int position;
    
    /**
     * Constructs a node with the specified position.
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
    protected final int getPosition() {
        return position;
    }
    
    /**
     * Determines whether this node is a value.
     * 
     * @return true if this node is a value and false otherwise
     */
    protected abstract boolean isValue();
    
    /**
     * Evaluates this node.
     * 
     * @return the node obtained by evaluating this node
     */
    public Node evaluate() {
        return this;
    }
    
    /**
     * Increments by {@code increment} the index of every variable in this node
     * that has an index greater than or equal to
     * the number of binders it is inside with respect to this node.
     * 
     * @param increment the number by which to increment
     * @return the node after incrementing by {@code increment} the index of every variable
     *     in this node that has an index greater than or equal to
     *     the number of binders it is inside with respect to this node.
     */
    protected final Node shift(final int increment) {
        return shift(0, increment);
    }
    
    /**
     * Increments by {@code increment} the index of every variable in this node
     * that has an index greater than or equal to {@code cutoff}
     * plus the number of binders it is inside with respect to this node.
     * 
     * @param cutoff the lower bound for the index of every free variable
     *     in this node to be incremented
     * @param increment the number by which to increment
     * @return the node after incrementing by {@code increment} the index of every variable
     *     in this node that has an index greater than or equal to {@code cutoff}
     *     plus the number of binders it is inside with respect to this node.
     */
    protected abstract Node shift(final int cutoff, final int increment);
    
    /**
     * Substitutes node {@code node} for every variable in this node
     * that has an index equal to the number of binders it is inside
     * with respect to this node.
     * 
     * @param node the node to substitute
     * @return the node after substituting node {@code node} for every variable in this node
     *     that has an index equal to the number of binders it is inside
     *     with respect to this node.
     */
    protected final Node substitute(final Node node) {
        return substitute(0, node);
    }
    
    /**
     * Substitutes node {@code node} for every variable in this node
     * that has an index equal to {@code index} plus the number of binders it is inside
     * with respect to this node.
     * 
     * @param index the index of every free variable in this node to be substituted
     * @param node the node to substitute
     * @return the node after substituting node {@code node} for every variable in this node
     *     that has an index equal to {@code index} plus the number of binders it is inside
     *     with respect to this node.
     */
    protected abstract Node substitute(final int index, final Node node);
    
    /**
     * Decompiles this node into a string based on the specified context.
     * Note that the resulting string may have extra parentheses.
     * 
     * @param context the current context
     * @return a string representation of this node
     * @throws InvalidContextLengthException if the specified context is invalid
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
