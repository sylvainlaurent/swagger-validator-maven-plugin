package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error;

public class OperationSemanticError extends SemanticError {

    private String operationId;

    public OperationSemanticError(String operationId, String message) {
        super(message);
        this.operationId = operationId;
    }

    public String getOperationId() {
        return operationId;
    }

    @Override
    public String toString() {
        return "OperationSemanticError{" + super.toString() + "," + "operationId='" + operationId + "'}";
    }
}
