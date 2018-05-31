package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableParameter;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.parameter.RefParameterWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.SemanticError;

import io.swagger.models.parameters.Parameter;

public class OperationParametersReferenceValidator extends PathValidatorTemplate {

    @Override
    public <T extends Parameter> void validate(VisitableParameter<T> parameter) {
        if (parameter instanceof RefParameterWrapper) {
            String refPath = ((RefParameterWrapper) parameter).get$ref();
            boolean validRefPath = context.getParameters().keySet().stream().anyMatch(p -> p.equals(refPath));
            if (!validRefPath) {
                validationErrors
                        .add(new SemanticError(holder.getCurrentPath(), "Could not resolve reference: " + refPath));
            }
        }
    }
}
