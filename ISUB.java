/**
 * ISUB subtracts the top value from the second-to-top value
 * of the OperandStack,
 * and pushes the result back to the OperandStack.
 */
public class ISUB extends Instruction {
    
    @Override
    public void execute(final Storage storage) {
        final OperandStack stack = storage.getOperandStack();
        final int secondOperand = stack.pop();
        final int firstOperand = stack.pop();
        stack.push(firstOperand - secondOperand);
    }
    
    @Override
    public String toString() {
        return "ISUB";
    }

}
