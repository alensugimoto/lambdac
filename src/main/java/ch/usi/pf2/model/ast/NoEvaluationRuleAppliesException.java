package ch.usi.pf2.model.ast;


/**
 * NoEvaluationRuleAppliesException is an Exception that
 * is thrown when an AST node is invoked on by an evaluate method
 * even though it cannot be evaluated further.
 * 
 * @author Alen Sugimoto
 * @version 03.06.2021
 */
public class NoEvaluationRuleAppliesException extends Exception {
}
