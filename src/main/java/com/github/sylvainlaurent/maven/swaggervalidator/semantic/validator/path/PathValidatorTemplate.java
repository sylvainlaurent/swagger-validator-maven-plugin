package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.PathVisitor;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.VisitedItemsHolder;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableParameter;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.OperationWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.PathWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.ResponseWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.ValidationContext;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.SemanticError;

import io.swagger.models.parameters.Parameter;

public abstract class PathValidatorTemplate implements PathVisitor, SwaggerPathValidator {

    protected List<SemanticError> validationErrors = new ArrayList<>();
    protected VisitedItemsHolder holder = new VisitedItemsHolder();
    protected ValidationContext context;

    @Override
    public void setValidationContext(ValidationContext context) {
        this.context = context;
    }

    @Override
    public List<SemanticError> getErrors() {
        return validationErrors;
    }

    @Override
    public void validate() {
        visit(context.getPaths());
    }

    @Override
    public void visit(List<PathWrapper> paths) {
        holder.push("paths");
        validate(paths);
        for (PathWrapper path : context.getPaths()) {
            visit(path);
        }
        holder.pop();
    }

    @Override
    public void visit(PathWrapper path) {
        holder.push(path.getName());
        validate(path);
        path.getParameters().forEach(this::validate);
        path.getOperations().values().forEach(this::visit);
        holder.pop();
    }

    @Override
    public void visit(OperationWrapper operation) {
        holder.push(operation.getName());
        validate(operation);
        operation.getParameters().forEach(this::visit);
        operation.getResponses().forEach(this::visit);
        operation.getSecurity().forEach(this::visit);
        holder.pop();
    }

    @Override
    public void visit(Map<String, List<String>> security) {
        holder.push("security");
        security.forEach(this::validate);
        holder.pop();
    }

    @Override
    public void visit(ResponseWrapper response) {
        holder.push("responses");
        holder.push(response.getName());
        validate(response);
        holder.pop();
        holder.pop();
    }

    @Override
    public <T extends Parameter> void visit(VisitableParameter<T> parameter) {
        holder.push(parameter.getName());
        validate(parameter);
        holder.pop();
    }

}
