package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.PathVisitor;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.VisitedItemsHolder;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableParameter;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.OperationWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.PathWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.ResponseWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.ValidationContext;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.SemanticError;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        holder.push(path.getObjectPath());
        validate(path);
        path.getParameters().forEach(this::validate);
        path.getOperations().values().forEach(this::visit);
        holder.pop();
    }

    @Override
    public void visit(OperationWrapper operation) {
        holder.push(operation.getObjectPath());
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
        holder.push(response.getObjectPath());
        validate(response);
        holder.pop();
    }

    @Override
    public void visit(VisitableParameter parameter) {
        holder.push(parameter.getObjectPath());
        validate(parameter);
        holder.pop();
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
}
