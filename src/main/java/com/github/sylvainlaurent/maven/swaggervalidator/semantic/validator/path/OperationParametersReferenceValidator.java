package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableParameter;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.parameter.RefParameterWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.SemanticError;

import java.util.ArrayList;
import java.util.List;

public class OperationParametersReferenceValidator extends PathValidatorTemplate {

    private List<SemanticError> validationErrors = new ArrayList<>();

    @Override
    public List<SemanticError> getErrors() {
        return validationErrors;
    }

    @Override
    public void validate(VisitableParameter parameter) {
        if (parameter instanceof RefParameterWrapper) {
            String refPath = ((RefParameterWrapper) parameter).get$ref();
            boolean validRefPath = context.getParameters().keySet().stream().anyMatch(p -> p.equals(refPath));
            if (!validRefPath) {
                validationErrors.add(new SemanticError(holder.getCurrentPath(), "Could not resolve reference: " + refPath));
            }
        }
    }
}
