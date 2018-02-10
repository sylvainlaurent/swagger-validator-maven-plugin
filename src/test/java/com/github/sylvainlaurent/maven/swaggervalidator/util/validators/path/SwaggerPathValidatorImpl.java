package com.github.sylvainlaurent.maven.swaggervalidator.util.validators.path;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableParameter;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.OperationWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.PathWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.ResponseWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.ValidationContext;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.SemanticError;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path.SwaggerPathValidator;

import java.util.List;
import java.util.Map;

public class SwaggerPathValidatorImpl implements SwaggerPathValidator {
    @Override
    public void validate() {

    }

    @Override
    public void visit(Map<String, List<String>> security) {

    }

    @Override
    public void validate(List<PathWrapper> paths) {

    }

    @Override
    public void validate(PathWrapper path) {

    }

    @Override
    public void validate(OperationWrapper operation) {

    }

    @Override
    public void validate(ResponseWrapper wrapper) {

    }

    @Override
    public void validate(VisitableParameter parameter) {

    }

    @Override
    public void validate(String securityDefinition, List<String> scopes) {

    }

    @Override
    public List<SemanticError> getErrors() {
        return null;
    }

    @Override
    public void setValidationContext(ValidationContext context) {

    }
}
