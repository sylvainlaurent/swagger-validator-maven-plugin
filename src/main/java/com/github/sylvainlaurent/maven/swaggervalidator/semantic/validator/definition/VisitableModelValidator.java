package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.definition;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableModel;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.SwaggerValidator;

public interface VisitableModelValidator extends SwaggerValidator {
    void validate(VisitableModel visitableModel);
}
