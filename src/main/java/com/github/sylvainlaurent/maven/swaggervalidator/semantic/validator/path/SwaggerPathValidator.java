package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path;

import java.util.List;
import java.util.Map;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableParameter;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.OperationWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.PathWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.ResponseWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.SwaggerValidator;

import io.swagger.models.parameters.Parameter;

public interface SwaggerPathValidator extends SwaggerValidator {

    void visit(Map<String, List<String>> security);

    default void validate(List<PathWrapper> paths) {
    }

    default void validate(PathWrapper path) {
    }

    default void validate(OperationWrapper operation) {
    }

    default void validate(ResponseWrapper wrapper) {
    }

    default <T extends Parameter> void validate(VisitableParameter<T> parameter) {
    }

    default void validate(String securityDefinition, List<String> scopes) {
    }
}
