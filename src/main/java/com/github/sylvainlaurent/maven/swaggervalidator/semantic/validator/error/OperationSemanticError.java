package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error;

import java.util.Objects;

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
        return "OperationSemanticError at operationId = " + operationId + ": " + getMessage();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OperationSemanticError that = (OperationSemanticError) o;
        return operationId.equals(that.operationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), operationId);
    }
}
