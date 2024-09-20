package edu.school21.operation;

import edu.school21.operation.Operationable;
import edu.school21.operation.exception.UnrecognizedOperatorException;

public class Operation implements Operationable {

    private static final String DEFAULT_OPERATOR = Operationable.SUM;

    private int firstValue;
    private int secondValue;
    private String operator;

    public Operation() { this.operator = DEFAULT_OPERATOR; }

    public Operation(String operator) { this.operator = operator; }

    @Override
    public int operation() {
        if (Operationable.SUM.equals(operator)) {
            return (this.firstValue + this.secondValue);
        } else if (Operationable.MUL.equals(operator)) {
            return (this.firstValue * this.secondValue);
        } else {
            throw new UnrecognizedOperatorException(operator);
        }
    }

    public int operation(int x, int y) {
        this.firstValue = x;
        this.secondValue = y;
        return operation();
    }

    @Override
    public String toString() {
        return String.format("%d %c %d = %d", this.firstValue,
                             (Operationable.SUM.equals(operator) ? '+' : '*'),
                             this.secondValue, operation());
    }
}
