package com.github.sylvainlaurent.maven.swaggervalidator.util.validators.path;

import java.util.List;
import java.util.Map;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.ValidationContext;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.SemanticError;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path.SwaggerPathValidator;

public class SwaggerPathValidatorImpl implements SwaggerPathValidator {

    @Override
    public void visit(Map<String, List<String>> security) {

    }

    @Override
    public List<SemanticError> getErrors() {
        return null;
    }

    @Override
    public void setValidationContext(ValidationContext context) {

    }
}
