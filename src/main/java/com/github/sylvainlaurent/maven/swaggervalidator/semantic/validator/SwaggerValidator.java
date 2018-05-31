package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator;

import java.util.List;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.SemanticError;

public interface SwaggerValidator {
    default void validate() {
    }

    List<SemanticError> getErrors();

    void setValidationContext(ValidationContext context);
}
