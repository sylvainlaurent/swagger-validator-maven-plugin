package com.github.sylvainlaurent.maven.swaggervalidator.util.validators.model;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableModel;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.ValidationContext;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.definition.VisitableModelValidator;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.SemanticError;

import java.util.List;

public class ModelValidatorImpl implements VisitableModelValidator {

    @Override
    public List<SemanticError> getErrors() {
        return null;
    }

    @Override
    public void setValidationContext(ValidationContext context) {

    }

    @Override
    public void validate(VisitableModel visitableModel) {

    }
}
