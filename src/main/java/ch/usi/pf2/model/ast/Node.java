package ch.usi.pf2.model.ast;

import ch.usi.pf2.model.interpreter.Context;


/**
 * An abstract syntax tree (AST) node for lambda terms.
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
    public final Node evaluate() {
        Node evaluatedNode = this;
        try {
            while (true) {
                evaluatedNode = evaluatedNode.evaluateOne();
            }
        } catch (NoEvaluationRuleAppliesException ex) {
            return evaluatedNode;
        }
    }
    
    /**
     * Evaluates this node by one step.
     * 
     * @return the evaluated node
     * @throws NoEvaluationRuleAppliesException if the <tt>evaluateOne</tt> operation
     *         is not supported by this node
     */
    protected abstract Node evaluateOne() throws NoEvaluationRuleAppliesException;
    
    /**
     * Increments by {@code d} the index of every variable in this node
     * that has an index greater than or equal to
     * the number of binders it is inside with respect to this node.
     * 
     * @param d the number by which to increment
     * @return the node after incrementing by {@code d} the index of every variable in this node
     *     that has an index greater than or equal to
     *     the number of binders it is inside with respect to this node.
     */
    protected final Node shift(final int d) {
        return shift(0, d);
    }
    
    /**
     * Increments by {@code d} the index of every variable in this node
     * that has an index greater than or equal to {@code c}
     * plus the number of binders it is inside with respect to this node.
     * 
     * @param c the lower bound for the index of every free variable in this node to be incremented
     * @param d the number by which to increment
     * @return the node after incrementing by {@code d} the index of every variable in this node
     *     that has an index greater than or equal to {@code c}
     *     plus the number of binders it is inside with respect to this node.
     */
    protected abstract Node shift(final int c, final int d);
    
    /**
     * Substitutes node {@code s} for every variable in this node
     * that has an index equal to the number of binders it is inside
     * with respect to this node.
     * 
     * @param s the node to substitute
     * @return the node after substituting node {@code s} for every variable in this node
     *     that has an index equal to the number of binders it is inside
     *     with respect to this node.
     */
    protected final Node substitute(final Node s) {
        return substitute(0, s);
    }
    
    /**
     * Substitutes node {@code s} for every variable in this node
     * that has an index equal to {@code c} plus the number of binders it is inside
     * with respect to this node.
     * 
     * @param c the index of every free variable in this node to be substituted
     * @param s the node to substitute
     * @return the node after substituting node {@code s} for every variable in this node
     *     that has an index equal to {@code c} plus the number of binders it is inside
     *     with respect to this node.
     */
    protected abstract Node substitute(final int c, final Node s);
    
    /**
     * Decompiles this node into a string based on the specified context.
     * Note that the resulting string may have extra parentheses.
     * 
     * @param context the current context
     * @return a string representation of this node
     * @throws InvalidContextException if the specified context is invalid
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
