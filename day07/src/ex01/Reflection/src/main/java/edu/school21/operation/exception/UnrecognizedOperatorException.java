package edu.school21.operation.exception;

public class UnrecognizedOperatorException extends RuntimeException {

    public UnrecognizedOperatorException(String operator) {
        super(String.format("Operator '%s' does not found.\n", operator));
    }
}
