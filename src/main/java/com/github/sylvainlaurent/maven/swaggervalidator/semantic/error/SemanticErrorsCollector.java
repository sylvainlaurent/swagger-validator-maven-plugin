package com.github.sylvainlaurent.maven.swaggervalidator.semantic.error;

import java.util.ArrayList;
import java.util.List;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.SemanticValidationResult;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class SemanticErrorsCollector {

    private List<SemanticError> errors = new ArrayList<>();

    public void addError(SemanticError.ErrorType errorType, String message) {
        errors.add(new SemanticError(errorType, message));
    }

    public void addOperationError(SemanticError.ErrorType errorType, String operationId, String message) {
        String completeMessage = "Error with operation \"" + operationId + "\": " + message;
        if (!containsAlreadyErrorWithMessage(completeMessage)) {
            errors.add(new OperationSemanticError(errorType, operationId, completeMessage));
        }
    }

    public void addDefinitionError(SemanticError.ErrorType errorType, String path, String message) {
        String completeMessage = "Definitions error at path \"" + path + "\": " + message;
        if (!containsAlreadyErrorWithMessage(completeMessage)) {
            errors.add(new DefinitionsSemanticError(errorType, path, completeMessage));

        }
    }

    private boolean containsAlreadyErrorWithMessage(final String message) {
        return Iterables.tryFind(errors, new Predicate<SemanticError>() {
            @Override
            public boolean apply(SemanticError semanticError) {
                return message.equals(semanticError.getMessage());
            }
        }).isPresent();
    }

    public SemanticValidationResult semanticValidationResult() {
        return new SemanticValidationResult(errors);
    }

}
