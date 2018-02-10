package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableParameter;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.OperationWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.PathWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.ResponseWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.SwaggerValidator;

import java.util.List;
import java.util.Map;

public interface SwaggerPathValidator extends SwaggerValidator {

    void visit(Map<String, List<String>> security);

    void validate(List<PathWrapper> paths);
    void validate(PathWrapper path);
    void validate(OperationWrapper operation);
    void validate(ResponseWrapper wrapper);
    void validate(VisitableParameter parameter);

    void validate(String securityDefinition, List<String> scopes);
}
