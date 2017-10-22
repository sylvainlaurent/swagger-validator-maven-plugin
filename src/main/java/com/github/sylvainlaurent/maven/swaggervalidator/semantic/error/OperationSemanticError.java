package com.github.sylvainlaurent.maven.swaggervalidator.semantic.error;

public class OperationSemanticError extends SemanticError {

    private String operationId;

    public OperationSemanticError(ErrorType errorType, String operationId, String message) {
        super(errorType, message);
        this.operationId = operationId;
    }

    public String getOperationId() {
        return operationId;
    }

    @Override
    public String toString() {
        return "OperationSemanticError{" + super.toString() + "," + "operationId='" + operationId + '\'' + '}';
    }
}
